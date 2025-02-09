package education.technicalcareer.Notes.API.user_management.repositories;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users") // Use a different table name to avoid conflicts with H2's default "user"
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role; // Define roles (e.g., ADMIN, USER)

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // You can customize this
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // You can customize this
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // You can customize this
    }

    @Override
    public boolean isEnabled() {
        return true; // You can customize this
    }

    public enum Role {
        USER, ADMIN
    }
}