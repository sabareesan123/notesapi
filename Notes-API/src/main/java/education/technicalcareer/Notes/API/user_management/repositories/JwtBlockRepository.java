package education.technicalcareer.Notes.API.user_management.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JwtBlockRepository extends JpaRepository<JwtBlocked, Integer>{
	
	JwtBlocked findByJwt(String jwt);

}
