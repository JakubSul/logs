package log.demo.entity.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LogAlertDto {
    private Long id;
    private String sourceId;
    private Long timestamp;
}
