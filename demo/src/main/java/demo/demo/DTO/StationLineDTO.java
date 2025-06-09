package demo.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StationLineDTO {

    private Long lineId;
    private Long stationId;
    private int stationOrder;
}
