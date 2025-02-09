package education.technicalcareer.Notes.API.controllers;


import education.technicalcareer.Notes.API.controllers.dtos.NoteDTO;
import education.technicalcareer.Notes.API.controllers.dtos.NoteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import education.technicalcareer.Notes.API.service.NoteService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @GetMapping
    public List<NoteDTO> getAllNotes() {
        return noteService.getAllNotes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteDTO> getNoteById(@PathVariable Long id) {
        Optional<NoteDTO> note = noteService.getNoteById(id);
        return note.map(noteDto -> ResponseEntity.ok(noteDto))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<NoteDTO> createNote(@RequestBody NoteDTO noteDto) {
        NoteDTO createdNote = noteService.createNote(noteDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdNote);
    }

    @PutMapping//("/{id}")  // Use PUT for full updates  @PathVariable Long id,
    public ResponseEntity<NoteDTO> updateNote(@RequestBody NoteDTO noteDto) {
        Optional<NoteDTO> existingNote = noteService.getNoteById(noteDto.getId()); // Check if the note exists

        if (existingNote.isPresent()) {
            NoteDTO updatedNote = noteService.updateNote(noteDto.getId(), noteDto); // Service method to handle the update
            return ResponseEntity.ok(updatedNote);
        } else {
            return ResponseEntity.notFound().build(); // Return 404 if the note doesn't exist
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        noteService.deleteNote(id);
        return ResponseEntity.noContent().build();
    }
}

