package anna.iAnnaOMG.commands.slash.music;

import anna.iAnnaOMG.commands.types.SlashCommand;
import anna.iAnnaOMG.listeners.lavaplayer.PlayerManager;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioTrack;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.managers.AudioManager;

import java.net.URI;
import java.net.URISyntaxException;

public class SlashPlay implements SlashCommand {

    protected static VoiceChannel voiceChannel;
    protected static VoiceChannel connectedChannel;

    @Override
    public void performCommand(SlashCommandEvent event, Member m, MessageChannel channel) {

        voiceChannel = event.getMember().getVoiceState().getChannel();
        connectedChannel = event.getGuild().getAudioManager().getConnectedChannel();
        String trackUrl = event.getOption("title").getAsString();

        event.reply(":flushed:").setEphemeral(true).queue(); //replies are mandatory (set ephemeral)

        AudioManager manager = event.getGuild().getAudioManager();

        if (voiceChannel == null) {
            channel.sendMessage("Please connect to a voice channel.").queue();
            return;
        }

        else if (connectedChannel == null || voiceChannel != connectedChannel){
            manager.openAudioConnection(voiceChannel);
        }

        if (!isUrl(trackUrl)) {
            trackUrl = "ytsearch:" + trackUrl;
        }

        PlayerManager.getInstance()
                .loadAndPlay((TextChannel) channel, trackUrl);
    }

    public boolean isUrl(String url) {
        try {
            new URI(url);
            return true;
        } catch (URISyntaxException e) {
            return false;
        }
    }

}

