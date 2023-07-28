package kg.center.mayak.repository;

import java.util.Optional;

import kg.center.mayak.model.ERole;
import kg.center.mayak.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;




@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
	Optional<Role> findByName(ERole name);
}
