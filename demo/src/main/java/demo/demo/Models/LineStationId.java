package demo.demo.Models;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LineStationId implements Serializable {

    private static final long serialVersionUID = 1L;

    private long lineId;
    private long stationId;
}
