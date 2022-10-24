package cc.invictusgames.invictusresapi.util.configuration.defaults;

import cc.invictusgames.invictusresapi.util.configuration.StaticConfiguration;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Emilxyz (langgezockt@gmail.com)
 * 12.02.2020 / 21:33
 * iLib / cc.invictusgames.ilib.configuration.defaults
 */

@Data
@NoArgsConstructor
public class RedisConfig implements StaticConfiguration {

    private String host = "localhost";
    private int port = 6379;
    private boolean authEnabled = false;
    private String authPassword = "password";
    private int dbId = 0;

}
