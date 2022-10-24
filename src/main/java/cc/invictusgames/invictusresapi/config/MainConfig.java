package cc.invictusgames.invictusresapi.config;

import cc.invictusgames.invictusresapi.util.configuration.StaticConfiguration;
import cc.invictusgames.invictusresapi.util.configuration.defaults.MongoConfig;
import cc.invictusgames.invictusresapi.util.configuration.defaults.RedisConfig;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class MainConfig implements StaticConfiguration {

    private final MongoConfig mongoConfig = new MongoConfig();
    private final RedisConfig redisConfig = new RedisConfig();
    private final List<UUID> oplist = new ArrayList<>();

}
