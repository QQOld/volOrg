package volorg.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import volorg.models.User;

public interface UserRepository extends PagingAndSortingRepository<User, Long>{

	User findByEmail(String email);
}
