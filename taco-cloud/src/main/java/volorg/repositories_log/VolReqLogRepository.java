package volorg.repositories_log;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import volorg.models_log.VolReqLog;

@Repository
public interface VolReqLogRepository extends CrudRepository<VolReqLog, Long> {
	
	void deleteByEmail(String email);
}
