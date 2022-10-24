package cc.invictusgames.invictusresapi.profile.grant.rpacket;

import cc.invictusgames.invictusresapi.redis.packet.RPacket;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
public class GrantAddPacket implements RPacket {

    private UUID uuid;
    private UUID rankUuid;
    private long duration;

    @Override
    public void receive() {

    }

    @Override
    public String getId() {
        return "cc.invictusgames.invictus.grant.packets.GrantAddPacket";
    }
}
