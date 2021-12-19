package anna.iAnnaOMG.commands.types;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

// Interface for all slash commands, relating to the music features
// Checks if a music command is possible (bot not in channel etc.)

public interface SlashMusic {

    default boolean inputHelper(SlashCommandEvent event, Member bot, Member m) {
        if (!bot.getVoiceState().inVoiceChannel()) {
            event.reply("`I need to be connected to a voice channel`").queue();
            return false;
        }

        if (!m.getVoiceState().inVoiceChannel()) {
            event.reply("`You need to be connected to a voice channel").queue();
            return false;
        }

        if (!m.getVoiceState().getChannel().equals(bot.getVoiceState().getChannel())) {
            event.reply("`We need to be connected to the same voice channel`").queue();
            return false;
        }
        return true;
    }
}
