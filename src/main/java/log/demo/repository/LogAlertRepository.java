package log.demo.repository;

import log.demo.entity.LogAlert;
import org.springframework.data.repository.CrudRepository;

public interface LogAlertRepository extends CrudRepository<LogAlert, Long> {
}
