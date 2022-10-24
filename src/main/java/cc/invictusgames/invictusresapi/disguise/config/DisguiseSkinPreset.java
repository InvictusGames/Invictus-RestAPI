package cc.invictusgames.invictusresapi.disguise.config;

import cc.invictusgames.invictusresapi.InvictusRestAPI;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisguiseSkinPreset {

    private String name = "N/A";
    private boolean hidden = false;
    private String texture;
    private String signature;

    public JsonObject toJson() {
        return InvictusRestAPI.GSON.toJsonTree(this).getAsJsonObject();
    }

}
