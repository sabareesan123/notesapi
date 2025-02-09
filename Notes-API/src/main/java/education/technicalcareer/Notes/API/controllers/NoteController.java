package education.technicalcareer.Notes.API.controllers;


import education.technicalcareer.Notes.API.controllers.dtos.NoteDto;
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
    public List<NoteDto> getAllNotes() {
        return noteService.getAllNotes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteDto> getNoteById(@PathVariable Long id) {
        Optional<NoteDto> note = noteService.getNoteById(id);
        return note.map(noteDto -> ResponseEntity.ok(noteDto))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<NoteDto> createNote(@RequestBody NoteDto noteDto) {
        NoteDto createdNote = noteService.createNote(noteDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdNote);
    }

    @PutMapping//("/{id}")  // Use PUT for full updates  @PathVariable Long id,
    public ResponseEntity<NoteDto> updateNote(@RequestBody NoteDto noteDto) {
        Optional<NoteDto> existingNote = noteService.getNoteById(noteDto.getId()); // Check if the note exists

        if (existingNote.isPresent()) {
            NoteDto updatedNote = noteService.updateNote(noteDto.getId(), noteDto); // Service method to handle the update
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

