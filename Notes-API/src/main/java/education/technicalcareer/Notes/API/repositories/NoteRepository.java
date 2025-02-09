package education.technicalcareer.Notes.API.repositories;

// Repository (NoteRepository.java)

import education.technicalcareer.Notes.API.repositories.entities.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    // Add custom queries if needed (e.g., findByTitleContaining(String keyword);)
}