package toyproject.discord.catbot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import toyproject.discord.catbot.controller.dto.MemeSaveRequest;
import toyproject.discord.catbot.domain.DiscordGuild;
import toyproject.discord.catbot.service.DiscordGuildService;
import toyproject.discord.catbot.service.MemeService;

@Controller
@RequestMapping("/meme")
@RequiredArgsConstructor
public class MemeController {

    private final MemeService memeService;
    private final DiscordGuildService guildService;

    @GetMapping("/register/{guildId}")
    public String registerMeme(@PathVariable String guildId, Model model) {
        return "meme/register";
    }

    @PostMapping("/register/{guildId}")
    public String registerMeme(@PathVariable String guildId, @ModelAttribute MemeSaveRequest meme) {
        DiscordGuild guild = guildService.findGuildByGuildId(guildId);
        memeService.saveMeme(meme, guild);
        return "redirect:/meme/success";
    }

    @GetMapping("/success")
    public String successForm() {
        return "meme/success";
    }

}
