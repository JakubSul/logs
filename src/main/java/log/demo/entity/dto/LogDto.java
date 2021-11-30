package log.demo.entity.dto;

import log.demo.entity.State;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LogDto {
    private String id;
    private State state;
    private String type;
    private String host;
    private Long timestamp;
}
