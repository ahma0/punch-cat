package toyproject.discord.catbot.controller.dto;

import org.springframework.web.multipart.MultipartFile;

public record MemeSaveRequest(String command, String description, MultipartFile imageFile) {
}
