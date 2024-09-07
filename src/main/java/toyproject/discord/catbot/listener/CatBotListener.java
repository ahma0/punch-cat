package toyproject.discord.catbot.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.user.UserActivityStartEvent;
import net.dv8tion.jda.api.events.user.update.GenericUserPresenceEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.FileUpload;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import toyproject.discord.catbot.service.MemeService;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;

@Log4j2
@Component
@RequiredArgsConstructor
public class CatBotListener extends ListenerAdapter {

    private final MemeService memeService;

    @Override
    public void onUserActivityStart(@NotNull UserActivityStartEvent event) {
        String message = memeService.getMessageForStartingActivities(event);
        File memeFile = memeService.getRandomMemeFile();
        Guild guild = event.getGuild();

        for (TextChannel channel : guild.getTextChannels()) {
            channel
                    .sendMessage(message)
                    .addFiles(FileUpload.fromData(memeFile))
                    .queue();
        }
    }
//
//    @Override
//    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
//        switch (event.getName()) {
//            case "say":
//                String content = event.getOption("content", OptionMapping::getAsString);
//                event.reply(content).queue();
//                break;
//            case "leave":
//                event.reply("I'm leaving the server now!")
//                        .setEphemeral(true) // this message is only visible to the command user
//                        .flatMap(m -> Objects.requireNonNull(event.getGuild()).leave()) // append a follow-up action using flatMap
//                        .queue(); // enqueue both actions to run in sequence (send message -> leave guild)
//                break;
//            default:
//                return;
//        }
//
//        TextChannel textChannel = event.getChannel().asTextChannel();
//
//        textChannel.sendMessage("Hello Friend!")
//                .addFiles(FileUpload.fromData(greetImage)) // Chain builder methods to configure the request
//                .queue();
//    }

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

    private String sendMessage(@NotNull MessageReceivedEvent event, @NotNull String message) {
        User user = event.getAuthor();
        String returnMessage = "";

        switch (message) {
            case "안녕하세요":
                returnMessage = user.getName() + "님 안녕하세요! 좋은 하루 되세요";
                break;
            case "hi":
                returnMessage = "Hello " + user.getAsTag();
                break;
            case "정보 확인":
                returnMessage = user.getAsMention() + "님 저는 Discord Bot 입니다.";
                break;
            case "1":
                returnMessage = user.getName() + " / 1번 옵션";
                break;
            case "2":
                returnMessage = user.getName() + " / 2번 옵션";
                break;
            default:
                returnMessage = "못 알아 듣겠어요 죄송합니다.";
                break;
        }
        return returnMessage;
    }
}
