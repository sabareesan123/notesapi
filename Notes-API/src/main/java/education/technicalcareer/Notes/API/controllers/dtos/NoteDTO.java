package education.technicalcareer.Notes.API.controllers.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import education.technicalcareer.Notes.API.repositories.entities.Color;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoteDTO {
    private Long id;
    private String title;
    private String body;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdDate;

    private Color color;
}