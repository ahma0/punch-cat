package toyproject.discord.catbot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/meme")
public class MemeController {

    @GetMapping("/register")
    public String registerMeme(Model model) {

        return "meme/register";
    }

}
