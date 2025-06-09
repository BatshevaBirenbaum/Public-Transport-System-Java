package demo.demo.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import demo.demo.DTO.DriverDTO;
import demo.demo.Service.DriverService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("driver")
public class DriverController {
    @Autowired
    private DriverService driverService;

    @GetMapping("/getAll")
    public ResponseEntity<List<DriverDTO>> getAll() {
        return ResponseEntity.ok().body(driverService.getAllDrivers());
    }

    @PostMapping("create")
    public ResponseEntity<Void> create(@RequestBody DriverDTO driverDTO) {
        if (driverService.createDriver(driverDTO))
            return ResponseEntity.status(HttpStatus.CREATED).build();
        return ResponseEntity.badRequest().build();
    }

}
