package anna.iAnnaOMG.commands.types;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import org.json.simple.parser.ParseException;

import java.io.IOException;

// Interface for all SlashCommands
// Contains a method performCommand method
public interface SlashCommand {
    void performCommand(SlashCommandEvent event, Member m, MessageChannel channel) throws IOException, ParseException, InterruptedException;
}
