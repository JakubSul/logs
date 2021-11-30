package log.demo.controller;

import log.demo.entity.dto.LogAlertDto;
import log.demo.entity.dto.LogDto;
import log.demo.service.LogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
public class LogController {

    private LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    @PostMapping("/process")
    public void process () {
        logService.process();
    }
    @GetMapping("/logs")
    public ResponseEntity<List<LogDto>> getLogs(){
        return ResponseEntity.ok(logService.getAllLogs());
    }
    @GetMapping("/logs/alerts")
    public ResponseEntity<List<LogAlertDto>> getLogAlerts(){
        return ResponseEntity.ok(logService.getAllLogsAlerts());
    }
}
