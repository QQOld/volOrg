package volorg.repositories;

import org.springframework.data.repository.CrudRepository;

import volorg.models.SearchRequest;

public interface SearchRequestRepository extends CrudRepository<SearchRequest, Long> {

}
