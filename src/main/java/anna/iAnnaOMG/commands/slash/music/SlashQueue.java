package anna.iAnnaOMG.commands.slash.music;

import anna.iAnnaOMG.commands.types.SlashCommand;
import anna.iAnnaOMG.commands.types.SlashMusic;
import anna.iAnnaOMG.listeners.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


//import static anna.iAnnaOMG.listeners.lavaplayer.PlayerManager.songs;

public class SlashQueue implements SlashCommand, SlashMusic {
    @Override
    public void performCommand(SlashCommandEvent event, Member m, MessageChannel channel) throws IOException, ParseException, InterruptedException {

        final Member bot = m.getGuild().getSelfMember();

        if (!inputHelper(event, bot, m)) {
            return;
        }

        StringBuilder sb = new StringBuilder();

        if (PlayerManager.songs.size() == 0) {
            event.reply("`No songs in queue`").queue();
            return;
        }

        event.reply("`Queue:`").setEphemeral(false).queue(); //replies are mandatory (set ephemeral)

        for (int i = 0; i < PlayerManager.songs.size(); i++) {
            channel.sendMessage("`" + i + ": ")
                    .append(PlayerManager.songs.get(i).getInfo().title)
                    .append(" by ")
                    .append(PlayerManager.songs.get(i).getInfo().author)
                    .append("` [`")
                    .append(formatTime(PlayerManager.songs.get(i).getDuration()))
                    .append("`]\n")
                    .queue();
        }
    }

    private String formatTime(long timeInMillis) {
        final long hours = timeInMillis / TimeUnit.HOURS.toMillis(1);
        final long minutes = timeInMillis / TimeUnit.MINUTES.toMillis(1);
        final long seconds = timeInMillis % TimeUnit.MINUTES.toMillis(1) / TimeUnit.SECONDS.toMillis(1);

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

}
