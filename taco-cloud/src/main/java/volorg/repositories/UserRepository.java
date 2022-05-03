package volorg.repositories;

import org.springframework.data.repository.CrudRepository;

import volorg.models.User;

public interface UserRepository extends CrudRepository<User, Long>{

}
