package anna.iAnnaOMG.listeners;

import anna.iAnnaOMG.Main;
import anna.iAnnaOMG.commands.TikTok;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        // Upon receiving a message, keep track of the content, author and channel
        Message message = event.getMessage();
        Member member = event.getMember();
        MessageChannel channel = event.getChannel();

        // If a message starts with our prefix, attempt to perform its
        // corresponding performCommand() method (if it exists)
        if (message.getContentDisplay().startsWith(Main.prefix)) {
            Main.getCmdMan().performCommand(member, channel, message);
        }
        // If a message starts with a TikTok url, run the TikTokDL command
        if (message.getContentDisplay().startsWith("https://tiktok.com/") || message.getContentDisplay().startsWith("https://vm.tiktok.com/")) {
            System.out.println("starting...");
            TikTok.performCommand(member, channel, message);
        }
    }
}
