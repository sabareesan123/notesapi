package education.technicalcareer.Notes.API.service;

// ... (imports)

import education.technicalcareer.Notes.API.controllers.dtos.NoteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import education.technicalcareer.Notes.API.repositories.entities.Note;
import education.technicalcareer.Notes.API.repositories.NoteRepository;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    // ... (other methods)

    //Helper Methods for DTO and Entity Conversion (Simplified)
    private NoteDto convertToDto(Note note) {
        return new NoteDto(note.getId(),note.getTitle(), note.getContent()); // Using AllArgsConstructor
    }

    private Note convertToEntity(NoteDto noteDto) {
        return new Note(null, noteDto.getTitle(), noteDto.getContent()); // Using AllArgsConstructor, setting id to null for new entities
    }

    public Optional<NoteDto> getNoteById(Long id) {

        if( noteRepository.findById(id).isPresent() )
            return Optional.of(convertToDto(noteRepository.findById(id).get()));
        else
           return Optional.empty();

    }

    public List<NoteDto> getAllNotes() {
        return noteRepository.findAll().stream().map(this::convertToDto).toList();

    }

    public void deleteNote(Long id) {
        if( noteRepository.findById(id).isPresent() )
            noteRepository.deleteById(id);
        else
            throw  new RuntimeException("Note not found. Please try with correct note id ");
    }

    public NoteDto createNote(NoteDto noteDto) {
        return convertToDto( noteRepository.save(convertToEntity(noteDto)));

    }

    public NoteDto updateNote(Long id, NoteDto noteDto) {
        var noteEntityOptional = noteRepository.findById(id);
        if( noteEntityOptional.isPresent()) {
            var entity = noteEntityOptional.get();
            entity.setContent(noteDto.getContent());
            entity.setTitle(noteDto.getTitle());
            return convertToDto(noteRepository.save(entity));
        }

            throw  new RuntimeException("Note not found. Please try with correct note id ");

    }
}