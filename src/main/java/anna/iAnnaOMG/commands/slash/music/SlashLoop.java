package anna.iAnnaOMG.commands.slash.music;

import anna.iAnnaOMG.commands.types.SlashCommand;
import anna.iAnnaOMG.listeners.lavaplayer.GuildMusicManager;
import anna.iAnnaOMG.listeners.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class SlashLoop implements SlashCommand {
    @Override
    public void performCommand(SlashCommandEvent event, Member m, MessageChannel channel) throws IOException, ParseException, InterruptedException {

        //final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(m.getGuild());
        event.reply("WIP").setEphemeral(true).queue();

        //musicManager.scheduler.rep
        //channel.sendMessageFormat("The player has been set to **%s**", newRepeating ? "repeating" : "not repeating").queue();

    }
}
