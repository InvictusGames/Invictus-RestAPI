package cc.invictusgames.invictusresapi.util;

import cc.invictusgames.invictusresapi.InvictusRestAPI;
import cc.invictusgames.invictusresapi.discord.DiscordData;
import cc.invictusgames.invictusresapi.profile.Profile;
import cc.invictusgames.invictusresapi.profile.grant.Grant;
import com.google.gson.JsonObject;

import java.util.*;

public class PermissionUtil {

    public static JsonObject getEffectivePermissions(Profile profile) {
        JsonObject effectivePermissions = new JsonObject();

        List<Grant> grants = new ArrayList<>(profile.getActiveGrants());
        grants.sort(Grant.COMPARATOR.reversed());
        for (Grant grant : grants) {
            JsonObject rankPerms = convert(grant.asRank().getAllPermissions());
            for (String key : rankPerms.keySet())
                effectivePermissions.addProperty(key, rankPerms.get(key).getAsString());

        }

        grants.clear();

        JsonObject profilePerms = convert(profile.getPermissions());
        for (String key : profilePerms.keySet())
            effectivePermissions.addProperty(key, profilePerms.get(key).getAsString());

        Optional<DiscordData> discordData = InvictusRestAPI.getInstance().getDiscordService().getByUuid(profile.getUuid());
        if (discordData.isPresent() && discordData.get().isBoosted()) {
            effectivePermissions.addProperty("invictus.nitroboost", true);
            effectivePermissions.addProperty("aresenic.gkit.nitro", true);
        }
        return effectivePermissions;
    }

    private static JsonObject convert(List<String> list) {
        JsonObject permissions = new JsonObject();
        list.forEach(permission -> {
            if (permission.startsWith("-"))
                permissions.addProperty(permission.substring(1), false);
            else permissions.addProperty(permission, true);
        });
        return permissions;
    }

}
