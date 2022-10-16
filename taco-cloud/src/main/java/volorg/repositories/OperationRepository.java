package volorg.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import volorg.models.Operation;
import volorg.models.SearchRequest;

public interface OperationRepository extends PagingAndSortingRepository<Operation, Long> {
	Page<Operation> findByStatus(String status, Pageable pageable);
}
