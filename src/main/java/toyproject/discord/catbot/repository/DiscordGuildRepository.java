package toyproject.discord.catbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import toyproject.discord.catbot.domain.DiscordGuild;

@Repository
public interface DiscordGuildRepository extends JpaRepository<DiscordGuild, String> {
}
