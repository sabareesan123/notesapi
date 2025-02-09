package education.technicalcareer.Notes.API.repositories.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String body;

    @Column
    private LocalDate createdDate = LocalDate.now(); // Default to current date

    @Enumerated(EnumType.STRING)  // Store enum as a string in DB
    @Column(nullable = false)
    private Color color;
}