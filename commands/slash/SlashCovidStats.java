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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SlashCovidStats implements SlashCommand {
    @Override
    public void performCommand(SlashCommandEvent event, Member m, MessageChannel channel) throws IOException, ParseException, InterruptedException {
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String date = formatter.format(new Date());

        URL url = new URL("https://api.opencovid.ca/timeseries?stat=cases&loc=ON&date=" + date);
        URLConnection conn = url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
        reader.close();

        JSONArray listObject = (JSONArray) jsonObject.get("cases");
        JSONObject result = (JSONObject) listObject.get(0);

        String cases = (String) result.get("cases");
        String cumulativeCases = (String) result.get("cumulative_cases");

        event.reply("```YAML"
                        + "\nCOVID: " + "-" + "Ontario" + "-" + "\n"
                        + "\nCases: " + cases
                        + "\nCumulative Cases: " + cumulativeCases
                        + "\n\uD83D\uDCC5" + date + "\n"
                        + "\n```")
                .queue();
    }
}
