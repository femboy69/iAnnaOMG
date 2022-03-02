package anna.iAnnaOMG.commands.slash;

import anna.iAnnaOMG.commands.types.SlashCommand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class SlashUrbanDictionary implements SlashCommand {

    @Override
    public void performCommand(SlashCommandEvent event, Member m, MessageChannel channel) throws IOException, ParseException {
        String meaning = null;
        String author = null;
        String example = null;
        String date = null;
        Long likes = null;
        Long dislikes = null;

        String word = event.getOption("word").getAsString();

        // Get the urban dictionary definition of the given word and reply accordingly
        URL url = new URL("https://api.urbandictionary.com/v0/define?term=" + word.replaceAll(" ", "%20"));

        // Real the url and create corresponding JSON object
        URLConnection conn = url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
        reader.close();

        JSONArray listObject = (JSONArray) jsonObject.get("list");
        JSONObject result;

        // If the word isn't in the dictionary, return "No Results Found"
        if (listObject.isEmpty()) {
            event.reply("No Results Found").queue();
        } else {
            result = (JSONObject) listObject.get(0);
            word = (String) result.get("word");
            meaning = (String) result.get("definition");
            author = (String) result.get("author");
            example = (String) result.get("example");
            date = result.get("written_on").toString().substring(0, 10);
            likes = (long) result.get("thumbs_up");
            dislikes = (long) result.get("thumbs_down");
        }

        // If we get a result, print and format it
        event.reply("```YAML"
                        + "\nURBAN DICTIONARY: " + "-" + word + "-" + "\n"
                        + "\nMeaning: " + clear(meaning)
                        + "\nAuthor: " + clear(author)
                        + "\n\uD83D\uDCC5" + clear(date) + "\n"
                        + "\nExamples: " + "\n"
                        + "\n" + clear(example) + "\n"
                        + "\n-\uD83D\uDC4D" + likes
                        + "\n-\uD83D\uDC4E" + dislikes
                        + "\n```")
                .queue();
    }

    // Remove square brackets from a String
    public String clear(String input) {
        return input.replaceAll("\\[", "").replaceAll("]", "");
    }

}
