package demo.demo.DTO;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TravelDTO {
    private long id;
    private LocalTime DepartureTime;
    private Long busId;
    private Long driverId;
    private Long lineId;
}
