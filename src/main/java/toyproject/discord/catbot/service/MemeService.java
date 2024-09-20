package toyproject.discord.catbot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.user.UserActivityStartEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.discord.catbot.controller.dto.MemeSaveRequest;
import toyproject.discord.catbot.domain.DiscordGuild;
import toyproject.discord.catbot.domain.FilePath;
import toyproject.discord.catbot.domain.Meme;
import toyproject.discord.catbot.exception.CBNotFoundException;
import toyproject.discord.catbot.exception.CBNotSavedException;
import toyproject.discord.catbot.repository.MemeRepository;

import java.io.File;
import java.util.Objects;

@Log4j2
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemeService {

    private final MemeRepository memeRepository;

    public void saveMeme(@NotNull MemeSaveRequest request, DiscordGuild guild) {
        String filePath = FilePath.createFilePath(request.imageFile());

        Meme meme = Meme.from(request, guild, filePath);
        if (memeRepository.existsByCommandAndGuild(request.command(), guild)) {
            log.info("이미 저장되어있는 커맨드 입니다.");
            return;
        }
        memeRepository.save(meme);

        if (!memeRepository.existsById(meme.getId())) {
            throw new CBNotSavedException("Meme not saved");
        }
    }

    public String getMessageForStartingActivities(@NotNull UserActivityStartEvent event) {
        User user = Objects.requireNonNull(event.getUser());
        Activity activity = event.getNewActivity();

        return user.getName() + "님이 " + activity.getName() + "을 시작했습니다!!!!";
    }

    @Transactional(readOnly = true)
    public File getRandomMemeFile() {
        Meme meme = memeRepository.findRandomMeme().orElse(getMemeFileIdDesc());
        return new File(meme.getImageUrls());
    }

    @Transactional(readOnly = true)
    public Meme getMemeFileIdDesc() {
        return memeRepository.findOneByOrderByIdDesc()
                .orElseThrow(() -> new CBNotFoundException("cannot find meme file"));
    }

}
