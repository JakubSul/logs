package log.demo.mapper;

import log.demo.entity.Log;
import log.demo.entity.LogAlert;
import log.demo.entity.dto.LogAlertDto;
import log.demo.entity.dto.LogDto;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LogMapper {

    public Log toEntity(LogDto dto){
        return Log.builder()
                .host(Optional.ofNullable(dto.getHost()).orElse(null))
                .state(dto.getState())
                .type(Optional.ofNullable(dto.getType()).orElse(null))
                .timestamp(dto.getTimestamp())
                .sourceId(dto.getId()).build();
    }
    public LogDto toDto(Log log){
        return LogDto.builder()
                .host(log.getHost())
                .timestamp(log.getTimestamp())
                .state(log.getState())
                .type(log.getType())
                .id(log.getSourceId())
                .build();
    }
    public LogAlertDto toDtoAlert(LogAlert log){
        return LogAlertDto.builder()
                .timestamp(log.getTimestamp())
                .sourceId(log.getSourceId())
                .id(log.getId())
                .build();
    }
}
