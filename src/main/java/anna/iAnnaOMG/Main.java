package anna.iAnnaOMG;

import anna.iAnnaOMG.listeners.MessageListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.security.auth.login.LoginException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

    // Prefix for (non slash) commands
    public static final String prefix = "anna.";
    public static JDA jda;
    // Server IDs that will be used by other parts of the program
    // .gitignore
    public static String IDOne = "";
    public static String IDTwo = "";
    // Manages (non slash) commands
    static CommandManager cmdMan = new CommandManager();

    public static void main(String[] args) throws InterruptedException, IOException, ParseException {
        String activityUrl = "";
        String token = "";

        // Parse our client_secret.json file which contains our token and other private information
        // In .gitignore
        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(new FileReader("C:\\Users\\wagee\\Documents\\Projects\\iAnnaOMG\\src\\main\\java\\anna\\iAnnaOMG\\client_secret.json"));

        IDOne = (String) obj.get("id-1");
        IDTwo = (String) obj.get("id-2");
        activityUrl = (String) obj.get("url");
        token = (String) obj.get("token");

        // Create the instance of our bot
        try {
            jda = JDABuilder.createDefault(token).build();
        } catch (LoginException e) {
            e.printStackTrace();
        }

        jda.awaitReady();

        // Discord presence
        jda.getPresence().setPresence(OnlineStatus.DO_NOT_DISTURB, Activity.streaming("Only Fans", activityUrl));

        // Add our event listeners for commands and slash commands
        jda.addEventListener(new SlashCommandManager());
        jda.addEventListener(new MessageListener());

    }

    // Getter for the command manager
    public static CommandManager getCmdMan() {
        return cmdMan;
    }
}
