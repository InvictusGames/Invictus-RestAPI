package cc.invictusgames.invictusresapi.forum.trophy;

import cc.invictusgames.invictusresapi.InvictusRestAPI;
import com.google.gson.JsonObject;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.Document;

@Data
@NoArgsConstructor
public class TrophyModel {

    private String id;
    private String name;

    public TrophyModel(Document document) {
        this.id = document.getString("id");
        this.name = document.getString("name");
    }

    public Document toBson() {
        Document document = new Document();

        document.append("id", id);
        document.append("name", name);

        return document;
    }

    public JsonObject toJson() {
        return InvictusRestAPI.GSON.toJsonTree(this).getAsJsonObject();
    }

}
