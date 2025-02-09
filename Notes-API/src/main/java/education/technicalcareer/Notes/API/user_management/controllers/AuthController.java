package education.technicalcareer.Notes.API.user_management.controllers;



// ... (other imports)

import education.technicalcareer.Notes.API.user_management.repositories.User;
import education.technicalcareer.Notes.API.user_management.service.UserService;
import education.technicalcareer.Notes.API.util.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody UserDto loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }


    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@RequestBody UserDto userDto) {
        User createdUser = userService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.convertToDto(createdUser));
    }
    @GetMapping("/logout")
   // @PreAuthorize("hasAuthority('ROLE_USER') and hasAuthority('ROLE_ADMIN')")
    public String logout(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        System.out.println("token 57 " + token);
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // Remove "Bearer " prefix
            System.out.println("token 60 " + token);
        }
        userService.invalidateJwt(token);
        System.out.println("invalidated " + token);
        return "invalidated";
    }
}
