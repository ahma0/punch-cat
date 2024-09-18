package toyproject.discord.catbot.domain;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import toyproject.discord.catbot.exception.CBException;

import java.io.File;
import java.io.IOException;

@Log4j2
@Component
public class FilePath {

    public static String discordMemeFileLocation;

    @Value("${discord.file-location.meme}")
    private String injectedMemeFileLocation;

    @PostConstruct
    public void init() {
        discordMemeFileLocation = this.injectedMemeFileLocation;
    }

    @NotNull
    @Contract("_ -> new")
    public static String createFilePath(String fileName) {
        return String.join("/", discordMemeFileLocation, fileName);
    }

    public static String createFilePath(@NotNull MultipartFile multipartFile) {
        String filePath = null;
        try {
            filePath = createFilePath(multipartFile.getOriginalFilename());
            multipartFile.transferTo(new File(filePath));
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new CBException("file upload failed");
        }
        return filePath;
    }

}
