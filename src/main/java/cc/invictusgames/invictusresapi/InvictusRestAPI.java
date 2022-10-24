package cc.invictusgames.invictusresapi;

import cc.invictusgames.invictusresapi.banphrase.BanphraseService;
import cc.invictusgames.invictusresapi.config.MainConfig;
import cc.invictusgames.invictusresapi.discord.DiscordService;
import cc.invictusgames.invictusresapi.disguise.DisguiseService;
import cc.invictusgames.invictusresapi.forum.ForumService;
import cc.invictusgames.invictusresapi.mongo.MongoService;
import cc.invictusgames.invictusresapi.profile.ProfileService;
import cc.invictusgames.invictusresapi.profile.grant.GrantService;
import cc.invictusgames.invictusresapi.profile.note.NoteService;
import cc.invictusgames.invictusresapi.punishment.PunishmentService;
import cc.invictusgames.invictusresapi.rank.RankService;
import cc.invictusgames.invictusresapi.redis.RedisService;
import cc.invictusgames.invictusresapi.tag.TagService;
import cc.invictusgames.invictusresapi.totp.TotpService;
import cc.invictusgames.invictusresapi.util.configuration.ConfigurationService;
import cc.invictusgames.invictusresapi.util.configuration.JsonConfigurationService;
import cc.invictusgames.invictusresapi.util.json.adapter.UUIDAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import lombok.Getter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author langgezockt (langgezockt@gmail.com)
 * 01.03.2021 / 02:47
 * InvictusRestAPI / cc.invictusgames.invictusresapi
 */

@SpringBootApplication
@Getter
@RestController
public class InvictusRestAPI {

    public static final Gson PRETTY_GSON = new GsonBuilder()
            .disableHtmlEscaping()
            .setPrettyPrinting()
            .registerTypeHierarchyAdapter(UUID.class, new UUIDAdapter())
            .create();

    public static final Gson GSON = new GsonBuilder()
            .disableHtmlEscaping()
            .registerTypeHierarchyAdapter(UUID.class, new UUIDAdapter())
            .create();

    public static final JsonParser JSON_PARSER = new JsonParser();

    @Getter
    private static InvictusRestAPI instance;

    public static void main(String[] args) {
        SpringApplication.run(InvictusRestAPI.class, args);
    }

    private final ConfigurationService configurationService = new JsonConfigurationService();
    private final MainConfig mainConfig = configurationService.loadConfiguration(MainConfig.class, new File("./config.json"));
    private final MongoService mongoService = new MongoService(this);
    private final RedisService redisService = new RedisService(mainConfig.getRedisConfig(), "invictus");

    private final RankService rankService = new RankService(this);
    private final GrantService grantService = new GrantService(this);
    private final NoteService noteService = new NoteService(this);
    private final PunishmentService punishmentService = new PunishmentService(this);
    private final DiscordService discordService = new DiscordService(this);
    private final ProfileService profileService = new ProfileService(this);
    private final BanphraseService banphraseService = new BanphraseService(this);
    private final DisguiseService disguiseService = new DisguiseService(this);
    private final TagService tagService = new TagService(this);
    private final TotpService totpService = new TotpService(this);
    private final ForumService forumService;

    private final long startedAt;

    public InvictusRestAPI() {
        instance = this;
        if (!mongoService.connect()) {
            System.out.println("Could not connect to mongodb, shutting down");
            System.exit(-1);
            throw new RuntimeException("Failed to connect to mongo");
        }

        rankService.loadRanks();
        tagService.loadTags();
        banphraseService.loadBanphrases();
        disguiseService.loadPresets();

        this.forumService = new ForumService(this);

        this.startedAt = System.currentTimeMillis();
    }

    public void saveMainConfig() {
        try {
            configurationService.saveConfiguration(mainConfig, new File("./config.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

