package cc.invictusgames.invictusresapi.tag;

import cc.invictusgames.invictusresapi.InvictusRestAPI;
import cc.invictusgames.invictusresapi.util.json.JsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/tag")
public class TagController {

    private final InvictusRestAPI api;
    private final TagService tagService;

    public TagController(InvictusRestAPI api) {
        this.api = api;
        this.tagService = api.getTagService();
    }

    @GetMapping
    public ResponseEntity<JsonElement> getTags() {
        JsonArray tags = new JsonArray();
        tagService.getTags().forEach(tag -> tags.add(tag.toJson()));
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<JsonElement> createTag(@RequestBody JsonObject body) {
        JsonBuilder response = new JsonBuilder();
        Tag tag = InvictusRestAPI.GSON.fromJson(body, Tag.class);
        if (tagService.getTag(tag.getName()).isPresent()) {
            response.add("message", "Tag already exists");
            return new ResponseEntity<>(response.build(), HttpStatus.CONFLICT);
        }

        tagService.saveTag(tag);
        return new ResponseEntity<>(tag.toJson(), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{name}")
    public ResponseEntity<JsonElement> deleteTag(@PathVariable(name = "name") String name) {
        JsonBuilder response = new JsonBuilder();
        if (!tagService.getTag(name).isPresent()) {
            response.add("message", "Tag not found");
            return new ResponseEntity<>(response.build(), HttpStatus.NOT_FOUND);
        }

        Tag tag = tagService.deleteTag(name);
        return new ResponseEntity<>(tag.toJson(), HttpStatus.OK);
    }

}
