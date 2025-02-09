package education.technicalcareer.Notes.API.user_management.service;


import education.technicalcareer.Notes.API.user_management.controllers.UserDto;
import education.technicalcareer.Notes.API.user_management.repositories.JwtBlocked;
import education.technicalcareer.Notes.API.user_management.repositories.JwtBlockRepository;
import education.technicalcareer.Notes.API.user_management.repositories.User;
import education.technicalcareer.Notes.API.user_management.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;  // Inject PasswordEncoder

    @Autowired
    private JwtBlockRepository jwtBlockRepository;

    public User createUser(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());

        // *IMPORTANT*: Hash the password before saving!
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        user.setPassword(encodedPassword);

        user.setRole(userDto.getRole()); // Set the role
        return userRepository.save(user);
    }


    public UserDto convertToDto(User user) {
        return new UserDto(user.getUsername(), user.getPassword(), user.getRole());
    }

    public User convertToEntity(UserDto userDto) {
        return new User(null, userDto.getUsername(), userDto.getPassword(), userDto.getRole());
    }

    public void invalidateJwt(String jwt) {

        JwtBlocked jwtBlocked = new JwtBlocked();
        jwtBlocked.setJwt(jwt);
        jwtBlockRepository.save(jwtBlocked);

    }
}