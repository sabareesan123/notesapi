package education.technicalcareer.Notes.API.user_management.controllers;

import education.technicalcareer.Notes.API.user_management.repositories.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String username;
    private String password; // In a real app, hash this before storing!
    private User.Role role;
}