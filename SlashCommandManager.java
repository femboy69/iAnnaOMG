package anna.iAnnaOMG;

import anna.iAnnaOMG.commands.slash.SlashCovidStats;
import anna.iAnnaOMG.commands.slash.SlashSay;
import anna.iAnnaOMG.commands.slash.SlashUrbanDictionary;
import anna.iAnnaOMG.commands.slash.music.*;
import anna.iAnnaOMG.commands.types.SlashCommand;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static anna.iAnnaOMG.Main.IDOne;

public class SlashCommandManager extends ListenerAdapter {

    private final Map<String, SlashCommand> commandsMap;

    public SlashCommandManager() {
        // Creates a HashMap which contains they key for each command
        // And the corresponding class which will execute it
        commandsMap = new ConcurrentHashMap<>();
        commandsMap.put("say", new SlashSay()); //adds new keys to the hashmap
        commandsMap.put("urban_dictionary", new SlashUrbanDictionary());
        commandsMap.put("play", new SlashPlay());
        commandsMap.put("disconnect", new SlashDisconnect());
        commandsMap.put("jump", new SlashSkip());
        commandsMap.put("queue", new SlashQueue());
        commandsMap.put("clear", new SlashClear());
        commandsMap.put("song", new SlashSong());
        commandsMap.put("loops", new SlashLoop());
        commandsMap.put("pasue_play", new SlashPausePlay());
        commandsMap.put("remove", new SlashRemove());
        commandsMap.put("covid_stats", new SlashCovidStats());

        // --Removes invalid commands and updates the command list
        // CommandListUpdateAction commands = Main.jda.updateCommands();
        // CommandListUpdateAction commands = Main.jda.getGuildById(IDTwo).updateCommands();
        CommandListUpdateAction commands = Main.jda.getGuildById(IDOne).updateCommands();

        // Add the commands to our bot instance (JDA)
        commands.addCommands(new CommandData("say", "Resends your message")
                .addOptions(new OptionData(OptionType.STRING, "msg", "The message to resend.").setRequired(true)));
        commands.addCommands(new CommandData("urban_dictionary", "Defines A Term Using [Urban Dictionary]")
                .addOptions(new OptionData(OptionType.STRING, "word", "The Word To Define.").setRequired(true)));
        commands.addCommands(new CommandData("define_word", "Defines A Term Using [Merriam Webster]")
                .addOptions(new OptionData(OptionType.STRING, "word", "The Word To Define.").setRequired(true)));
        commands.addCommands(new CommandData("play", "Plays a Youtube Video")
                .addOptions(new OptionData(OptionType.STRING, "title", "The name or URL to play.").setRequired(true)));
        commands.addCommands(new CommandData("remove", "Removes a song from the queue.")
                .addOptions(new OptionData(OptionType.STRING, "index", "The index of the song to remove.").setRequired(true)));
        commands.addCommands(new CommandData("disconnect", "Disconnects from the connected voice channel"));
        commands.addCommands(new CommandData("jump", "Jumps to another song.")
                .addOptions(new OptionData(OptionType.STRING, "index", "The index of the song to play.").setRequired(true)));
        commands.addCommands(new CommandData("queue", "Displays the queue."));
        commands.addCommands(new CommandData("clear", "Clears the queue."));
        commands.addCommands(new CommandData("song", "Displays info about the currently playing song."));
        commands.addCommands(new CommandData("loop", "Loops or Un-loops the current song."));
        commands.addCommands(new CommandData("pause_play", "Pauses or Plays the current song"));
        commands.addCommands(new CommandData("covid_stats", "Returns COVID stats for Ontario"));
        commands.queue();
    }

    // Upon a slash command, look through our HashMap to perform the corresponding command
    // In the corresponding class
    // For example, /play will execute the performCommand() method in the SlashPlay class
    public void onSlashCommand(SlashCommandEvent event) {
        String commandName = event.getName();
        SlashCommand command;

        if ((command = commandsMap.get(commandName)) != null) {
            try {
                command.performCommand(event, event.getMember(), event.getMessageChannel());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

