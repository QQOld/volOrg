package volorg.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import volorg.models.SearchRequest;

public interface SearchRequestRepository extends CrudRepository<SearchRequest, Long> {
	List<SearchRequest> findByStatus(String status);
}
