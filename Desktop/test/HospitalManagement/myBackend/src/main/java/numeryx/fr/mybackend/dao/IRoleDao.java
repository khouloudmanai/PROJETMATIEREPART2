package numeryx.fr.mybackend.dao;

import numeryx.fr.mybackend.entities.Role;
import numeryx.fr.mybackend.entities.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface IRoleDao extends JpaRepository<Role, Long>{


    Set<Role> findByIdIn(Set<Long> roleNames);

	Optional<Role> findByName(ERole name);
}
