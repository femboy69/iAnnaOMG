package anna.iAnnaOMG.commands.slash;

import anna.iAnnaOMG.commands.types.SlashCommand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.components.Button;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import static net.dv8tion.jda.api.utils.MarkdownUtil.codeblock;

public class SlashUrbanDictionary implements SlashCommand {

    String word = "";
    String def = "";
    String meaning = "";
    String author = "";
    String date = "";
    String example = "";
    long likes = 0;
    long dislikes = 0;

    @Override
    public void performCommand(SlashCommandEvent event, Member m, MessageChannel channel) throws IOException, ParseException {
        word = event.getOption("word").getAsString();
        // Get the urban dictionary definition of the given word and reply accordingly
        def = getWord(word);

        // If we get a result, print and format it
        if (def != "No Results Found") {
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
                    .addActionRow(
                            Button.success("Prev", "Prev"),
                            Button.success("Next", "Next")
                    )
                    .queue();
        } else {
            event.reply(codeblock(clear(word + ": " + def))).queue();
        }
    }

    // Remove square brackets from a String
    public String clear(String input){
        return input.replaceAll("\\[", "").replaceAll("]", "");
    }

    // Use JSON to retrieve the meaning, etc. of a word
    public String getWord(String input) throws IOException, ParseException {

        URL url = new URL("https://api.urbandictionary.com/v0/define?term=" + input.replaceAll(" ", "%20"));

        // Real the url and create corresponding JSON object
        URLConnection conn = url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
        reader.close();

        JSONArray listObject = (JSONArray) jsonObject.get("list");
        JSONObject result; //initializes variable

        // If the word isn't in the dictionary, return "No Results Found"
        if (listObject.isEmpty()) {
            return "No Results Found";
        }

        else {
            result = (JSONObject) listObject.get(0);
            word = (String) result.get("word");
            meaning = (String) result.get("definition");
            author = (String) result.get("author");
            example = (String) result.get("example");
            date = result.get("written_on").toString().substring(0, 10);
            likes = (long) result.get("thumbs_up");
            dislikes = (long) result.get("thumbs_down");
            return " " + result.get("word");
        }
    }
}
