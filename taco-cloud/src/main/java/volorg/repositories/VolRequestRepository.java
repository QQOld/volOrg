package volorg.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import volorg.models.VolRequest;

public interface VolRequestRepository extends PagingAndSortingRepository<VolRequest, Long> {
	Page<VolRequest> findByStatus(String status, Pageable pageable);
}
