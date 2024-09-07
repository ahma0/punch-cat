package toyproject.discord.catbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import toyproject.discord.catbot.domain.Meme;

import java.util.Optional;

@Repository
public interface MemeRepository extends JpaRepository<Meme, String> {

    @Query(value = "SELECT * FROM meme m ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Optional<Meme> findRandomMeme();

    Optional<Meme> findOneByOrderByIdDesc();

}
