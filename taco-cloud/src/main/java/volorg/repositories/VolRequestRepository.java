package volorg.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import volorg.models.VolRequest;

public interface VolRequestRepository extends CrudRepository<VolRequest, Long> {
	List<VolRequest> findByStatus(String status);
}
