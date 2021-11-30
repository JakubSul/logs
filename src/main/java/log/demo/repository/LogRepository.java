package log.demo.repository;

import log.demo.entity.Log;
import org.springframework.data.repository.CrudRepository;

public interface LogRepository extends CrudRepository<Log,Long> {
}
