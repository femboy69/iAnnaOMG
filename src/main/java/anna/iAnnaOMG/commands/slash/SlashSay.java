package anna.iAnnaOMG.commands.slash;

import anna.iAnnaOMG.commands.types.SlashCommand;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

// Repeat a String the user says, making it look like the bot said it

public class SlashSay implements SlashCommand {

    @Override
    public void performCommand(SlashCommandEvent event, Member m, MessageChannel channel) {
        String msg = event.getOption("msg").getAsString(); //gets the message sent as a string
        event.reply(":flushed:").setEphemeral(true).queue(); //replies are mandatory (set ephemeral)
        channel.sendMessage(msg).queue();
    }
}
