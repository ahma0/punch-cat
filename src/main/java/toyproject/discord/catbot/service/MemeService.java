package toyproject.discord.catbot.service;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.user.UserActivityStartEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import toyproject.discord.catbot.domain.Meme;
import toyproject.discord.catbot.exception.CBNotFoundException;
import toyproject.discord.catbot.repository.MemeRepository;

import java.io.File;
import java.nio.file.Files;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MemeService {

    private final MemeRepository memeRepository;

    public String getMessageForStartingActivities(@NotNull UserActivityStartEvent event) {
        User user = Objects.requireNonNull(event.getUser());
        Activity activity = event.getNewActivity();

        return user.getName() + "님이 " + activity.getName() + "을 시작했습니다!!!!";

    }

    public File getRandomMemeFile() {
        Meme meme = memeRepository.findRandomMeme().orElse(getMemeFileIdDesc());
        return new File(meme.getImageUrls());
    }

    public Meme getMemeFileIdDesc() {
        return memeRepository.findOneByOrderByIdDesc()
                .orElseThrow(() -> new CBNotFoundException("cannot find meme file"));
    }

}
