package education.technicalcareer.Notes.API.service;

// ... (imports)

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import education.technicalcareer.Notes.API.repositories.entities.Note;
import education.technicalcareer.Notes.API.repositories.NoteRepository;
import education.technicalcareer.Notes.API.controllers.dtos.NoteDTO;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    // ... (other methods)

    //Helper Methods for DTO and Entity Conversion (Simplified)
    private NoteDTO convertToDto(Note note) {
        return new NoteDTO(note.getId(),note.getTitle(), note.getBody(),note.getCreatedDate(), note.getColor()); // Using AllArgsConstructor
    }

    private Note convertToEntity(NoteDTO noteDto) {
        return new Note(null, noteDto.getTitle(), noteDto.getBody(),noteDto.getCreatedDate(),noteDto.getColor()); // Using AllArgsConstructor, setting id to null for new entities
    }

    public Optional<NoteDTO> getNoteById(Long id) {

        if( noteRepository.findById(id).isPresent() )
            return Optional.of(convertToDto(noteRepository.findById(id).get()));
        else
           return Optional.empty();

    }

    public List<NoteDTO> getAllNotes() {
        return noteRepository.findAll().stream().map(this::convertToDto).toList();

    }

    public void deleteNote(Long id) {
        if( noteRepository.findById(id).isPresent() )
            noteRepository.deleteById(id);
        else
            throw  new RuntimeException("Note not found. Please try with correct note id ");
    }

    public NoteDTO createNote(NoteDTO noteDto) {
        return convertToDto( noteRepository.save(convertToEntity(noteDto)));

    }

    public NoteDTO updateNote(Long id, NoteDTO noteDto) {
        var noteEntityOptional = noteRepository.findById(id);
        if( noteEntityOptional.isPresent()) {
            var entity = noteEntityOptional.get();
            entity.setId(noteDto.getId());
            entity.setTitle(noteDto.getTitle());
            entity.setBody(noteDto.getBody());
            entity.setCreatedDate(noteDto.getCreatedDate());
            entity.setColor(noteDto.getColor());
            return convertToDto(noteRepository.save(entity));
        }

            throw  new RuntimeException("Note not found. Please try with correct note id ");

    }
}