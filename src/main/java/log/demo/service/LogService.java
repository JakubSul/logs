package log.demo.service;

import com.google.gson.Gson;
import log.demo.entity.Log;
import log.demo.entity.LogAlert;
import log.demo.entity.State;
import log.demo.entity.dto.LogAlertDto;
import log.demo.entity.dto.LogDto;
import log.demo.mapper.LogMapper;
import log.demo.repository.LogAlertRepository;
import log.demo.repository.LogRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
public class LogService {
    private LogRepository logRepository;
    private LogAlertRepository logAlertRepository;
    private LogMapper logMapper;

    public LogService(LogRepository logRepository, LogMapper logMapper, LogAlertRepository logAlertRepository) {
        this.logRepository = logRepository;
        this.logMapper = logMapper;
        this.logAlertRepository = logAlertRepository;
    }

    public void process(){
        List<Log> listOfLogsFromFile = getListOfLogsFromFile();
        List<LogAlert> alerts = getAlerts(listOfLogsFromFile);
        logRepository.saveAll(listOfLogsFromFile);
        logAlertRepository.saveAll(alerts);

    }

    public List<LogDto> getAllLogs(){
        List<LogDto> logDtos = new ArrayList<>();
        logRepository.findAll().forEach(l -> logDtos.add(logMapper.toDto(l)));
        return logDtos;
    }
    public List<LogAlertDto> getAllLogsAlerts(){
        List<LogAlertDto> logDtos = new ArrayList<>();
        logAlertRepository.findAll().forEach(l -> logDtos.add(logMapper.toDtoAlert(l)));
        return logDtos;
    }

    private List<LogAlert> getAlerts(List<Log> listOfLogsFromFile) {
        List<LogAlert> logAlerts = new ArrayList<>();
        Map<String, List<Log>> group = listOfLogsFromFile.stream().collect(Collectors.groupingBy(Log::getSourceId));
        for (String k : group.keySet()){
            List<Log> logs = group.get(k);
            Log start = findLogByState(logs, State.STARTED);
            Log finished = findLogByState(logs, State.FINISHED);
            if (start != null && finished != null){
                long alertTimestamp = finished.getTimestamp() - start.getTimestamp();
                if (alertTimestamp > 4){
                    logAlerts.add(LogAlert.builder()
                                    .sourceId(k)
                                    .timestamp(alertTimestamp)
                            .build());
                }
            }
        }
        return logAlerts;

    }

    private Log findLogByState(List<Log> logs, State state) {
        Optional<Log> log = logs.stream().filter(l -> l.getState().equals(state)).findFirst();
        return log.orElse(null);

    }

    private void findStartLog(List<Log> logs) {

    }

    private List<Log> getListOfLogsFromFile(){
        List<Log> logs = new ArrayList<>();
        Gson g = new Gson();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/static/logs.txt"));
            String line = reader.readLine();
            while (line != null) {
                LogDto logDto = g.fromJson(line, LogDto.class);
                logs.add(logMapper.toEntity(logDto));
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return logs;
    }
}
