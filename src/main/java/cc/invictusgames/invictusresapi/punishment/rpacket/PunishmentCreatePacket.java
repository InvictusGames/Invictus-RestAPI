package cc.invictusgames.invictusresapi.punishment.rpacket;

import cc.invictusgames.invictusresapi.redis.packet.RPacket;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
public class PunishmentCreatePacket implements RPacket {

    private UUID uuid;
    private String type;
    private String executor;
    private String reason;
    private String serverType;
    private String server;
    private long duration;
    private Set<String> flags;

    @Override
    public void receive() {

    }

    @Override
    public String getId() {
        return "cc.invictusgames.invictus.punishment.packets.PunishmentCreatePacket";
    }
}
