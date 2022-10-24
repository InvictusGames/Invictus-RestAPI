package cc.invictusgames.invictusresapi.forum.category;

import cc.invictusgames.invictusresapi.InvictusRestAPI;
import cc.invictusgames.invictusresapi.forum.forum.ForumModel;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.Document;

@Data
@NoArgsConstructor
public class ForumCategory {

    private String id;
    private String name;
    private int weight;

    public ForumCategory(Document document) {
        this.id = document.getString("id");
        this.name = document.getString("name");
        this.weight = document.getInteger("weight");
    }

    public Document toBson() {
        Document document = new Document();
        document.append("id", id);
        document.append("name", name);
        document.append("weight", weight);
        return document;
    }

    public JsonObject toJson() {
        JsonObject object = InvictusRestAPI.GSON.toJsonTree(this).getAsJsonObject();

        JsonArray array = new JsonArray();
        for (ForumModel forum : InvictusRestAPI.getInstance().getForumService().getForumModelService().getByCategory(id))
            array.add(forum.toJson());
        object.add("forums", array);

        return object;
    }

}
