package toyproject.discord.catbot.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.user.UserActivityStartEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.FileUpload;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import toyproject.discord.catbot.service.DiscordGuildService;
import toyproject.discord.catbot.service.MemeService;

import java.awt.*;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static toyproject.discord.catbot.service.ListenerService.*;

@Log4j2
@Component
@RequiredArgsConstructor
public class CatBotListener extends ListenerAdapter {

    private final MemeService memeService;
    private final DiscordGuildService guildService;

    @Override
    public void onUserActivityStart(@NotNull UserActivityStartEvent event) {
        String message = memeService.getMessageForStartingActivities(event);
        File memeFile = memeService.getRandomMemeFile();

        Guild guild = event.getGuild();
        Set<String> channelIds = guildService.getChannelIds(guild);
        List<TextChannel> channels = channelIds.stream().map(guild::getTextChannelById).toList();

        if (channelIds.isEmpty()) {
            channels = guild.getTextChannels();
        }

        for (TextChannel channel : channels) {
            channel
                    .sendMessage(message)
                    .addFiles(FileUpload.fromData(memeFile))
                    .queue();
        }
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        switch (event.getName()) {
            case "add":
                sendButtonMessageForRegister(event);
                break;
            default:
                return;
        }
    }

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        String buttonId = event.getComponentId();

        switch (buttonId) {
            case "registerMeme":
                createEmbedForRegisterMemes(event);
                break;
        }
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        User user = event.getAuthor();
        TextChannel textChannel = event.getChannel().asTextChannel();
        Message message = event.getMessage();

        log.info("get TextMessages : {}", message.getContentDisplay());

        if (user.isBot()) {
            return;
        } else if (message.getContentDisplay().isBlank()) {
            log.info("디스코드 Message 문자열 값 공백");
        }

        String[] messageArray = message.getContentDisplay().split("/");


        if (messageArray[0].equalsIgnoreCase("!bot")) {
            String[] messageArgs = Arrays.copyOfRange(messageArray, 1, messageArray.length);

            for (String msg : messageArgs) {
                String returnMessage = sendMessage(event, msg);
                textChannel.sendMessage(returnMessage).queue();
            }

        }

    }

}
