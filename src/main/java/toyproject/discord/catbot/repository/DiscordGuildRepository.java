package toyproject.discord.catbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import toyproject.discord.catbot.domain.DiscordGuild;

import java.util.Optional;

@Repository
public interface DiscordGuildRepository extends JpaRepository<DiscordGuild, String> {

    Optional<DiscordGuild> findByGuildId(String id);

    boolean existsByGuildId(String id);

}
