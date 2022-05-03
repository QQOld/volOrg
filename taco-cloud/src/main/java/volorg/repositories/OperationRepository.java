package volorg.repositories;

import org.springframework.data.repository.CrudRepository;

import volorg.models.Operation;

public interface OperationRepository extends CrudRepository<Operation, Long> {

}
