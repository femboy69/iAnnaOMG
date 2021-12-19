package anna.iAnnaOMG.commands.slash.music;

import anna.iAnnaOMG.Main;
import anna.iAnnaOMG.commands.types.SlashCommand;
import anna.iAnnaOMG.commands.types.SlashMusic;
import anna.iAnnaOMG.listeners.lavaplayer.GuildMusicManager;
import anna.iAnnaOMG.listeners.lavaplayer.PlayerManager;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import org.json.simple.parser.ParseException;

import java.io.IOException;

import static java.lang.Integer.parseInt;

public class SlashSkip implements SlashCommand, SlashMusic {

    @Override
    public void performCommand(SlashCommandEvent event, Member m, MessageChannel channel) throws IOException, ParseException, InterruptedException {

        final Member bot = m.getGuild().getSelfMember();

        if (!inputHelper(event, bot, m)){
            return;
        }

        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(m.getGuild());
        final AudioPlayer audioPlayer = musicManager.audioPlayer;
        final AudioTrack track = audioPlayer.getPlayingTrack();

        int index = parseInt(event.getOption("index").getAsString());

        if ((PlayerManager.songs.size() >= index) && (index >= 0)){
            while(PlayerManager.songs.get(index) != audioPlayer.getPlayingTrack()){
                musicManager.scheduler.nextTrack();
            }
        }

        else{
            event.reply("`Index doesn't exist`").queue();
        }

        event.reply("`Successfully Skipped To: `").queue();
        channel.sendMessage("` ")
                .append(audioPlayer.getPlayingTrack().getInfo().title)
                .append(" by ")
                .append(audioPlayer.getPlayingTrack().getInfo().author)
                .append("`")
                .queue();

    }
}
