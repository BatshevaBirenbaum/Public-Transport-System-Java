package demo.demo.Controller;

import java.time.LocalTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.demo.DTO.TravelDTO;
import demo.demo.Service.TravelService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController()
@RequestMapping("/travel")
public class TravelController {
    @Autowired
    private TravelService travelService;

    @GetMapping("getAll")
    public ResponseEntity<List<TravelDTO>> getAll() {
        return ResponseEntity.ok().body(travelService.getAllTravel());
    }

    @PostMapping("create")
    public ResponseEntity<Void> create(@RequestBody TravelDTO travelDTO) {
        if (travelService.createTravel(travelDTO))
            return ResponseEntity.status(HttpStatus.CREATED).build();
        return ResponseEntity.badRequest().build();

    }

    @GetMapping("allTheTravels/{lineNumber}")
    public ResponseEntity<List<LocalTime>> allTheTravels(@PathVariable String lineNumber) {
        List<LocalTime> times = travelService.allTheTravels(lineNumber);
        return ResponseEntity.ok().body(times);
    }

    @GetMapping("lastTravel/{lineNumber}")
    public ResponseEntity<LocalTime> lastTravel(@PathVariable String lineNumber) {
        LocalTime lastTravel = travelService.lastTravel(lineNumber);
        return ResponseEntity.ok().body(lastTravel);
    }

    @GetMapping("travelAtCertainTime/{lineNumber}/{time}")
    public ResponseEntity<List<LocalTime>> travelAtCertainTime(@PathVariable String lineNumber,
            @PathVariable LocalTime time) {
        List<LocalTime> certainTime = travelService.travelAtCertainTime(lineNumber, time);
        return ResponseEntity.ok().body(certainTime);
    }

}
