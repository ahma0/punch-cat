package toyproject.discord.catbot.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import toyproject.discord.catbot.domain.value.Defaults;
import toyproject.discord.catbot.domain.value.Domain;

@Getter
@Entity
@Table(name = "meme")
public class Meme extends BaseEntity {

    @Column(nullable = false)
    private String command;

    @Column(nullable = false)
    private String imageUrls;

    @Enumerated(EnumType.STRING)
    private Defaults defaults;

    @Column(nullable = false)
    private String description;

    @Column
    private String guildId;

    protected Meme() {
        super(Domain.MEME);
    }

    @Builder
    public Meme(String command, String imageUrls, Defaults defaults, String description, String guildId) {
        this();
        this.command = command;
        this.imageUrls = imageUrls;
        this.defaults = defaults;
        this.description = description;
        this.guildId = guildId;
    }

}
