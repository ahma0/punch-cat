package toyproject.discord.catbot.domain;

import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class CBUrls {

    public static String discordMemeRegisterUrl;

    @Value("${discord.url.meme.register-path}")
    private String injectedMemeRegisterUrl;

    @PostConstruct
    public void init() {
        discordMemeRegisterUrl = this.injectedMemeRegisterUrl;
    }

    @NotNull
    @Contract("_ -> new")
    public static String createMemeRegisterUrl(String guildId) {
        return String.join("/", discordMemeRegisterUrl, guildId);
    }

}
