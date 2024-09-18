package toyproject.discord.catbot.service;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.jetbrains.annotations.NotNull;
import toyproject.discord.catbot.domain.CBUrls;

import java.awt.*;
import java.util.Objects;

public class ListenerService {

    public static String sendMessage(@NotNull MessageReceivedEvent event, @NotNull String message) {
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

    public static void sendButtonMessageForRegister(@NotNull SlashCommandInteractionEvent event) {
        event.reply("파일을 추가하려면 버튼을 클릭하긔:")
                .addActionRow(
                        Button.primary("registerMeme", "등록하기")
                ).queue();
    }

    @NotNull
    public static MessageEmbed createEmbedForRegisterMemes(@NotNull ButtonInteractionEvent event) {
        String id = Objects.requireNonNull(event.getGuild()).getId();

        EmbedBuilder embed = new EmbedBuilder();
        embed
                .setTitle("밈 등록하러 가기")
                .setDescription("밈을 등록하기 위해 홈페이지 이동하러 가긔")
                .setColor(Color.green)
                .setUrl(CBUrls.createMemeRegisterUrl(id));
//                .setThumbnail("imageUrl");

        return embed.build();
    }

}
