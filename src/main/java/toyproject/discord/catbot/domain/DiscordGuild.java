package toyproject.discord.catbot.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import net.dv8tion.jda.api.entities.Guild;
import org.jetbrains.annotations.NotNull;
import toyproject.discord.catbot.domain.value.Domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Entity
@Table(name = "discord_guild")
public class DiscordGuild extends BaseEntity {

    @Column(nullable = false)
    private String guildId;

    @Column(nullable = false)
    private String guildName;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "guild_channels",
            joinColumns = @JoinColumn(name = "guild_id", referencedColumnName = "id")
    )
    private Set<String> channelsId = new HashSet<>();

    @OneToMany(mappedBy = "guild")
    private List<Meme> memes = new ArrayList<>();

    protected DiscordGuild() {
        super(Domain.GUILD);
    }

    @Builder
    public DiscordGuild(String guildId, String name, Set<String> channelsId) {
        this();
        this.guildId = guildId;
        this.guildName = name;
        this.channelsId = channelsId;
    }

    public static DiscordGuild of(@NotNull Guild guild) {
        return DiscordGuild.builder()
                .guildId(guild.getId())
                .name(guild.getName())
                .build();
    }

    public void addChannelIds(String channelsId) {
        this.channelsId.add(channelsId);
    }
}
