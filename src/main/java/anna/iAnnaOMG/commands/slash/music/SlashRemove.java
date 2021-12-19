package anna.iAnnaOMG.commands.slash.music;

import anna.iAnnaOMG.commands.types.SlashCommand;
import anna.iAnnaOMG.commands.types.SlashMusic;
import anna.iAnnaOMG.listeners.lavaplayer.PlayerManager;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import org.json.simple.parser.ParseException;

import java.io.IOException;

import static java.lang.Integer.parseInt;

public class SlashRemove implements SlashCommand, SlashMusic {
    @Override
    public void performCommand(SlashCommandEvent event, Member m, MessageChannel channel) throws IOException, ParseException, InterruptedException {
        final TextChannel textChannel = m.getDefaultChannel();
        final Member bot = m.getGuild().getSelfMember();

        AudioTrack removedSong;

        if (!inputHelper(event, bot, m)){
            return;
        }

        if (PlayerManager.songs.size() == 0) {
            event.reply("`No songs in queue`").queue();
            return;
        }

        int index = parseInt(event.getOption("index").getAsString());

        if(index > PlayerManager.songs.size() || index < 0){
            event.reply("`Enter a valid index.`").queue();
        }

        removedSong = PlayerManager.songs.get(index);
        PlayerManager.getInstance().getMusicManager(m.getGuild()).scheduler.queue.remove(PlayerManager.songs.get(index));
        PlayerManager.songs.remove(index);

        event.reply("`Successfully Removed:`").queue();
        channel.sendMessage("` ")
                .append(removedSong.getInfo().title)
                .append(" by ")
                .append(removedSong.getInfo().author)
                .append("`")
                .queue();

    }
}
