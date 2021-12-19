package anna.iAnnaOMG.commands;

import anna.iAnnaOMG.commands.types.Command;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

// Tells a user to play Danganrompa
// An inside joke

public class Danganrompa implements Command {

    public void performCommand(Member m, MessageChannel channel, Message message) {
        channel.sendMessage("<@766816685604601856>" + " Play Danganronpa " + suffix()).queue();
    }

    //generates a random suffix
    public String suffix() {

        int Random = (int)(Math.random()*4);
        String s = "";

        switch (Random) {
            case 1: s = "hoe";
            case 2: s =  "bitch";
            case 3: s = "slut";
            default: s = "";
        };
        return s;
    }

}
