package volorg.repositories;

import org.springframework.data.repository.CrudRepository;

import volorg.models.Message;

public interface MessageRepository extends CrudRepository<Message, Long> {

}
