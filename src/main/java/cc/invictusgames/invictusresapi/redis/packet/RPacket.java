package cc.invictusgames.invictusresapi.redis.packet;

/**
 * @author Emilxyz (langgezockt@gmail.com)
 * 31.12.2019 / 03:29
 * iLib / cc.invictusgames.ilib.redis
 */

public interface RPacket {

    void receive();

    String getId();
}
