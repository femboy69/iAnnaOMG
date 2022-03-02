package anna.iAnnaOMG.commands;

import anna.iAnnaOMG.commands.types.Command;
import anna.iAnnaOMG.listeners.TikTokDL;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

import java.io.File;

public class TikTok implements Command {

    // Downloads a TikTok that a user sent, and sends it to the same channel
    public static void performCommand(Member m, MessageChannel channel, Message message) {
        TikTokDL.download(message.getContentDisplay());
        channel.deleteMessageById(message.getId());
        File video = getLastModified("C:\\Users\\wagee\\Documents\\TikToks");
        channel.sendFile(video).queue();
    }

    // Returns the last modified file in a directory
    public static File getLastModified(String dir) {
        File directory = new File(dir);
        File[] files = directory.listFiles(File::isFile);
        long lastModifiedTime = Long.MIN_VALUE;
        File chosenFile = null;

        if (files != null) {
            for (File file : files) {
                if (file.lastModified() > lastModifiedTime) {
                    chosenFile = file;
                    lastModifiedTime = file.lastModified();
                }
            }
        }
        return chosenFile;
    }
}
