package toyproject.discord.catbot.service;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.Guild;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import toyproject.discord.catbot.domain.DiscordGuild;
import toyproject.discord.catbot.exception.CBNotSavedException;
import toyproject.discord.catbot.repository.DiscordGuildRepository;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class DiscordGuildService {

    private final DiscordGuildRepository discordGuildRepository;

    public Set<String> getChannelIds(@NotNull Guild guild) {
        return discordGuildRepository.findById(guild.getId())
                .orElse(registerGuild(guild))
                .getChannelsId();
    }

    public DiscordGuild registerGuild(Guild guild) {
        DiscordGuild discordGuild = DiscordGuild.of(guild);
        discordGuildRepository.save(discordGuild);
        return discordGuildRepository.findById(discordGuild.getId())
                .orElseThrow(() -> new CBNotSavedException("Could not save guild " + discordGuild.getId()));
    }

}
