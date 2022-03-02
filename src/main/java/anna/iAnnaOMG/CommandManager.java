package anna.iAnnaOMG;

import anna.iAnnaOMG.commands.Danganrompa;
import anna.iAnnaOMG.commands.TikTok;
import anna.iAnnaOMG.commands.types.Command;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {

    // HashMap which keeps track of every command and what class they'll execute
    private final Map<String, Command> commands;

    public CommandManager() {
        commands = new HashMap<>();
        commands.put("anna.danganronpa", new Danganrompa());
    }

    public void performCommand(Member m, MessageChannel channel, Message message) {
        String cmd = message.getContentDisplay();
        Command command;

        // If the command exists, perform it
        if ((command = commands.get(cmd)) != null)
            Command.performCommand(m, channel, message);
    }
}