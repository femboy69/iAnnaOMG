package anna.iAnnaOMG.commands.slash.music;

import anna.iAnnaOMG.commands.types.SlashCommand;
import anna.iAnnaOMG.commands.types.SlashMusic;
import anna.iAnnaOMG.listeners.lavaplayer.GuildMusicManager;
import anna.iAnnaOMG.listeners.lavaplayer.PlayerManager;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class SlashSong implements SlashCommand, SlashMusic {

    @Override
    public void performCommand(SlashCommandEvent event, Member m, MessageChannel channel) throws IOException, ParseException, InterruptedException {

        final Member bot = m.getGuild().getSelfMember();

        if (!inputHelper(event, bot, m)){
            return;
        }

        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(m.getGuild());
        final AudioPlayer audioPlayer = musicManager.audioPlayer;
        final AudioTrack track = audioPlayer.getPlayingTrack();

        if (track == null) {
            event.reply("`No track playing`").queue();
            return;
        }

        event.reply("`Playing: " + track.getInfo().title + " by " + track.getInfo().author + "\n(Link: " + track.getInfo().uri + ")`").queue();

    }
}
