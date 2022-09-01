package volorg.repositories;

import org.springframework.data.repository.CrudRepository;

import volorg.models.Role;

public interface RoleRepository extends CrudRepository<Role, Long>{
	
	Role findByName(String name);
}
