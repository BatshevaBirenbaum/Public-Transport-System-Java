package demo.demo.Models;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "station_line")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StationLine {

    @EmbeddedId
    private LineStationId id;

    @Column(name = "station_order")
    private int stationOrder;

    @ManyToOne
    @MapsId("lineId")
    @JoinColumn(name = "line_id")
    private Line line;

    @ManyToOne
    @MapsId("stationId")
    @JoinColumn(name = "station_id")
    private Station station;
}
