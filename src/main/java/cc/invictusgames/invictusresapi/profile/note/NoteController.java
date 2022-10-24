package cc.invictusgames.invictusresapi.profile.note;

import cc.invictusgames.invictusresapi.InvictusRestAPI;
import cc.invictusgames.invictusresapi.util.json.JsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/profile/{uuid}/notes")
public class NoteController {

    private final InvictusRestAPI api;
    private final NoteService noteService;

    public NoteController(InvictusRestAPI api) {
        this.api = api;
        this.noteService = api.getNoteService();
    }

    @PostMapping
    public ResponseEntity<JsonElement> addNote(@RequestBody JsonObject body, @PathVariable(name = "uuid") UUID uuid) {
        JsonBuilder response = new JsonBuilder();
        UUID id = body.has("id")
                ? UUID.fromString(body.get("id").getAsString())
                : UUID.randomUUID();

        if (noteService.getNote(id).isPresent()) {
            response.add("message", "Note already exists");
            return new ResponseEntity<>(response.build(), HttpStatus.CONFLICT);
        }

        Note note = new Note();
        note.setId(id);
        note.setUuid(uuid);
        note.setAddedBy(body.get("addedBy").getAsString());
        note.setNote(body.get("note").getAsString());
        note.setAddedOn(body.has("addedOn")
                ? body.get("addedOn").getAsString()
                : "Website");
        note.setAddedAt(body.has("addedAt")
                ? body.get("addedAt").getAsLong()
                : System.currentTimeMillis());

        noteService.saveNote(note);
        return new ResponseEntity<>(note.toJson(), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<JsonElement> getNotesOf(@PathVariable(name = "uuid") UUID uuid) {
        JsonArray notes = new JsonArray();
        noteService.getNotesOf(uuid).forEach(note -> notes.add(note.toJson()));
        return new ResponseEntity<>(notes, HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<JsonElement> updateNote(@RequestBody JsonObject body,
                               @PathVariable(name = "uuid") UUID uuid,
                               @PathVariable(name = "id") UUID id) {
        JsonBuilder response = new JsonBuilder();
        Optional<Note> noteOpt = noteService.getNote(id);

        if (!noteOpt.isPresent()) {
            response.add("message", "Note not found");
            return new ResponseEntity<>(response.build(), HttpStatus.NOT_FOUND);
        }

        Note note = noteOpt.get();

        if (body.has("note"))
            note.setNote(body.get("note").getAsString());

        noteService.saveNote(note);
        return new ResponseEntity<>(note.toJson(), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<JsonElement> removeNote(@PathVariable(name = "uuid") UUID uuid,
                               @PathVariable(name = "id") UUID id) {
        JsonBuilder response = new JsonBuilder();
        Optional<Note> noteOpt = noteService.getNote(id);

        if (!noteOpt.isPresent()) {
            response.add("message", "Note not found");
            return new ResponseEntity<>(response.build(), HttpStatus.NOT_FOUND);
        }

        Note note = noteOpt.get();
        noteService.deleteNote(note);
        return new ResponseEntity<>(note.toJson(), HttpStatus.OK);
    }
}
