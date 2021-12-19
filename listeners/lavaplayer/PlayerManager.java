package anna.iAnnaOMG.listeners.lavaplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Lava player class (DO NOT CHANGE)

public class PlayerManager {
    public static ArrayList<AudioTrack> songs = new ArrayList<AudioTrack>(); // Create an ArrayList object
    private static PlayerManager INSTANCE;
    private final Map<Long, GuildMusicManager> musicManagers;
    private final AudioPlayerManager audioPlayerManager;

    public PlayerManager() {
        this.musicManagers = new HashMap<>();
        this.audioPlayerManager = new DefaultAudioPlayerManager();

        AudioSourceManagers.registerRemoteSources(this.audioPlayerManager);
        AudioSourceManagers.registerLocalSource(this.audioPlayerManager);
    }

    public static PlayerManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PlayerManager();
        }
        return INSTANCE;
    }

    public GuildMusicManager getMusicManager(Guild guild) {
        return this.musicManagers.computeIfAbsent(guild.getIdLong(), (guildId) -> {
            final GuildMusicManager guildMusicManager = new GuildMusicManager(this.audioPlayerManager);

            guild.getAudioManager().setSendingHandler(guildMusicManager.getSendHandler());

            return guildMusicManager;
        });
    }

    public void loadAndPlay(TextChannel channel, String trackUrl) {
        final GuildMusicManager musicManager = this.getMusicManager(channel.getGuild());

        this.audioPlayerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                musicManager.scheduler.queue(track);

                channel.sendMessage("Adding to queue: `")
                        .append(track.getInfo().title)
                        .append(" by ")
                        .append(track.getInfo().author)
                        .append('`')
                        .queue();
                songs.add(track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                final List<AudioTrack> tracks = playlist.getTracks();

                if (playlist.isSearchResult()) {
                    musicManager.scheduler.queue(tracks.get(0));
                    channel.sendMessage("Adding to queue: `")
                            .append(tracks.get(0).getInfo().title)
                            .append(" by ")
                            .append(tracks.get(0).getInfo().author)
                            .append('`')
                            .queue();
                    songs.add(tracks.get(0));
                    return;
                }

                channel.sendMessage("`Adding to queue: ")
                        .append(String.valueOf(tracks.size()))
                        .append(" tracks from playlist ")
                        .append(playlist.getName())
                        .append('`')
                        .queue();

                for (final AudioTrack track : tracks) {
                    musicManager.scheduler.queue(track);
                }
                songs.addAll(tracks);
            }

            @Override
            public void noMatches() {
                channel.sendMessage("No Matches Found");
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                channel.sendMessage("Load Failed");
            }
        });
    }
}