package cc.invictusgames.invictusresapi.profile.rpacket;

import cc.invictusgames.invictusresapi.redis.packet.RPacket;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
public class ProfileUpdatePacket implements RPacket {

    private UUID uuid;

    @Override
    public void receive() {

    }

    @Override
    public String getId() {
        return "cc.invictusgames.invictus.profile.packets.ProfileUpdatePacket";
    }
}
