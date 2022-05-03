package volorg.repositories;

import org.springframework.data.repository.CrudRepository;

import volorg.models.Chat;

public interface ChatRepository extends CrudRepository<Chat, Long> {

}
