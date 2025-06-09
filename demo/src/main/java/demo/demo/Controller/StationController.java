package demo.demo.Controller;

import demo.demo.DTO.StationDTO;
import demo.demo.Service.StationService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController()
@RequestMapping("/stations")
public class StationController {

    @Autowired
    private StationService stationService;

    @GetMapping("/getAll")
    public ResponseEntity<List<StationDTO>> getAll() {
        return ResponseEntity.ok().body(stationService.getAllStations());
    }

    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody StationDTO stationDTO) {
        if (stationService.createStation(stationDTO))
            return ResponseEntity.status(HttpStatus.CREATED).build();
        return ResponseEntity.badRequest().build();
    }

}
