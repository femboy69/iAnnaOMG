package anna.iAnnaOMG.commands.types;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

// Interface for all (non slash) Commands
// Contains a method performCommand method
public interface Command {
    void performCommand(Member m, MessageChannel channel, Message message);
}
