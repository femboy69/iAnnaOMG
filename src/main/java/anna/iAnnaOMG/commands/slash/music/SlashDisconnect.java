package anna.iAnnaOMG.commands.slash.music;

import anna.iAnnaOMG.commands.types.SlashCommand;
import anna.iAnnaOMG.commands.types.SlashMusic;
import anna.iAnnaOMG.listeners.lavaplayer.GuildMusicManager;
import anna.iAnnaOMG.listeners.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;


public class SlashDisconnect implements SlashCommand, SlashMusic {

    @Override
    public void performCommand(SlashCommandEvent event, Member m, MessageChannel channel) {

        final Member bot = m.getGuild().getSelfMember();

        if (!inputHelper(event, bot, m)){
            return;
        }

        if (event.getGuild().getAudioManager().getConnectedChannel() != null){

            final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(m.getGuild());

            musicManager.scheduler.player.stopTrack();
            musicManager.scheduler.queue.clear();
            PlayerManager.songs.clear();

            event.getGuild().getAudioManager().closeAudioConnection();

            event.reply("`Disconnected`").queue();
        }
    }
}
