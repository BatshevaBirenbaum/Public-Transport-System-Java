package demo.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.demo.DTO.BusDTO;
import demo.demo.Service.BusService;

@RestController
@RequestMapping("/bus")
public class BusController {
    @Autowired
    private BusService busService;

    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody BusDTO busDTO) {
        if (busService.createBus(busDTO))
            return ResponseEntity.status(HttpStatus.CREATED).build();
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<BusDTO>> getAll() {
        return ResponseEntity.ok().body(busService.getAllBuses());
    }
}
