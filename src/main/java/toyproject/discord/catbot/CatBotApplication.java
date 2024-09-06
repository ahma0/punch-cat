package toyproject.discord.catbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.security.auth.login.LoginException;

@SpringBootApplication
public class CatBotApplication {

    public static void main(String[] args) throws LoginException {
        SpringApplication.run(CatBotApplication.class, args);
    }

}
