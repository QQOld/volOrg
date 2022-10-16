package volorg.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import volorg.models.Chat;

public interface ChatRepository extends PagingAndSortingRepository<Chat, Long> {

}
