package toyproject.discord.catbot.domain;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

@Getter
public class DiscordBotToken {

    @Value("${discord.bot.token}")
    private String discordBotToken;

}
