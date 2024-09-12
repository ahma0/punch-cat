package toyproject.discord.catbot.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import toyproject.discord.catbot.domain.value.OriginType;
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
    private OriginType originType;

    @Column(nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guild_id", referencedColumnName = "id")
    private DiscordGuild guild;

    protected Meme() {
        super(Domain.MEME);
    }

    @Builder
    public Meme(String command, String imageUrls, OriginType originType, String description, DiscordGuild guild) {
        this();
        this.command = command;
        this.imageUrls = imageUrls;
        this.originType = originType;
        this.description = description;
        this.guild = guild;
    }

}
