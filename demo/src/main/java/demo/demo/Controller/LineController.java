package demo.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.demo.DTO.LineDTO;
import demo.demo.Service.LineService;

@RestController
@RequestMapping("/lines")
public class LineController {
    @Autowired
    private LineService lineService;

    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody LineDTO lineDTO) {
        if (lineService.createLine(lineDTO))
            return ResponseEntity.status(HttpStatus.CREATED).build();
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<LineDTO>> getAll() {
        return ResponseEntity.ok().body(lineService.getAllLines());
    }

    @GetMapping("allStationsOnTheLine/{lineNumber}")
    public ResponseEntity<List<String>> allStationsOnTheLine(@PathVariable String lineNumber) {
        if (lineNumber == null)
            return ResponseEntity.badRequest().build();

        List<String> allStations = lineService.allStationsOnTheLine(lineNumber);
        return ResponseEntity.ok().body(allStations);
    }

    @GetMapping("locations/{lineNumber}")
    public ResponseEntity<List<String>> theLocationOfTheBusesAlongTheRoute(@PathVariable String lineNumber) {
        List<String> locations = lineService.theLocationOfTheBusesAlongTheRoute(lineNumber);
        return ResponseEntity.ok().body(locations);
    }
}
