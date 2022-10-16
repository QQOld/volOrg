package volorg.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import volorg.models.SearchRequest;

public interface SearchRequestRepository extends PagingAndSortingRepository<SearchRequest, Long> {
	Page<SearchRequest> findByStatus(String status, Pageable pageable);
}
