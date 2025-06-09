package demo.demo.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import demo.demo.DTO.StationLineDTO;
import demo.demo.Service.StationLineService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController()
@RequestMapping("/stationsLine")
public class StationLineController {
    @Autowired
    private StationLineService stationLineService;

    @GetMapping("getAll")
    public ResponseEntity<List<StationLineDTO>> getAll() {
        return ResponseEntity.ok().body(stationLineService.getAllStationLines());
    }

    @PostMapping("create")
    public ResponseEntity<Void> create(@RequestBody StationLineDTO stationLineDTO) {
        System.out.println("Received StationLineDTO: " + stationLineDTO);
        if (stationLineService.createStationLine(stationLineDTO))
            return ResponseEntity.status(HttpStatus.CREATED).build();
        return ResponseEntity.badRequest().build();

    }

    @GetMapping("searchByStation/{stationNumber}/{lineNumber}")
    public ResponseEntity<List<String>> searchByStation(@PathVariable String stationNumber,
            @PathVariable String lineNumber) {
        if (stationNumber == null || lineNumber == null) {
            System.out.println("Bad request: stationNumber or lineNumber is null");
            return ResponseEntity.badRequest().build();
        }

        List<String> results = stationLineService.searchByStation(stationNumber, lineNumber);

        if (results.isEmpty()) {
            System.out
                    .println("No content found for stationNumber: {} and lineNumber: {}" + stationNumber + lineNumber);
            return ResponseEntity.noContent().build(); // או ResponseEntity.ok(results); אם אתה רוצה להחזיר רשימה ריקה
        }

        System.out.println("Returning results for stationNumber: {} and lineNumber: {}" + stationNumber + lineNumber);
        return ResponseEntity.ok(results);
    }

    @DeleteMapping("/deleteByStationIdAndLineId/{lineId}/{stationId}")
    public ResponseEntity<Void> deleteByStationIdAndLineId(@PathVariable Long lineId, @PathVariable Long stationId) {
        if (stationLineService.deleteByStationIdAndLineId(lineId, stationId))
            return ResponseEntity.noContent().build();
        return ResponseEntity.badRequest().build();
    }

}
