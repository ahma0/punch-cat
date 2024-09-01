package toyproject.discord.catbot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import toyproject.discord.catbot.domain.DiscordBotToken;
import toyproject.discord.catbot.listener.CatBotListener;

import javax.security.auth.login.LoginException;

@SpringBootApplication
public class CatBotApplication {

    public static void main(String[] args) throws LoginException {
        ApplicationContext context = SpringApplication.run(CatBotApplication.class, args);

        DiscordBotToken discordBotTokenEntity = context.getBean(DiscordBotToken.class);
        String discordBotToken = discordBotTokenEntity.getDiscordBotToken();

        JDA jda = JDABuilder.createDefault(discordBotToken)
                .setActivity(Activity.playing("메시지 대기 중"))
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(new CatBotListener())
                .build();
    }

}
