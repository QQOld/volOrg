package volorg.repositories;

import org.springframework.data.repository.CrudRepository;

import volorg.models.Comment;

public interface CommentRepository extends CrudRepository<Comment, Long> {
		
}
