package toyproject.discord.catbot.config;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import toyproject.discord.catbot.domain.DiscordBotToken;
import toyproject.discord.catbot.listener.CatBotListener;

@Configuration
public class JDAConfig {

    @Bean
    public JDA jda(ApplicationContext context) throws Exception {
        DiscordBotToken discordBotTokenEntity = context.getBean(DiscordBotToken.class);
        String discordBotToken = discordBotTokenEntity.getDiscordBotToken();

        CatBotListener catBotListener = context.getBean(CatBotListener.class);

        JDA jda = JDABuilder.createDefault(discordBotToken)
                .setActivity(Activity.playing("punch"))
                .enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(catBotListener)
                .build();

        jda.updateCommands().addCommands(
                Commands.slash("add-form", "meme form 등록"),
                Commands.slash("register", "서버 등록")
        ).queue();

        return jda;
    }

}
