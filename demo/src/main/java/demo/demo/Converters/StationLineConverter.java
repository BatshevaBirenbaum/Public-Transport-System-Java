package demo.demo.Converters;

import java.util.List;
import java.util.stream.Collectors;

import demo.demo.DTO.StationLineDTO;
import demo.demo.Models.Line;
import demo.demo.Models.LineStationId;
import demo.demo.Models.Station;
import demo.demo.Models.StationLine;

public class StationLineConverter {

    public static StationLineDTO toDto(StationLine stationLine) {
        StationLineDTO stationLineDTO = new StationLineDTO();
        stationLineDTO.setLineId(stationLine.getLine().getId());
        stationLineDTO.setStationId(stationLine.getStation().getId());
        stationLineDTO.setStationOrder(stationLine.getStationOrder());
        return stationLineDTO;
    }

    public static StationLine toModel(StationLineDTO dto) {
        StationLine stationLine = new StationLine();

        LineStationId id = new LineStationId(dto.getLineId(), dto.getStationId());
        stationLine.setId(id);
        stationLine.setStationOrder(dto.getStationOrder());

        Line line = new Line();
        line.setId(dto.getLineId());
        stationLine.setLine(line);

        Station station = new Station();
        station.setId(dto.getStationId());
        stationLine.setStation(station);

        return stationLine;
    }

    public static List<StationLineDTO> toListDto(List<StationLine> stationLines) {
        return stationLines.stream().map(b -> toDto(b)).collect(Collectors.toList());
    }

    public static List<StationLine> toListModel(List<StationLineDTO> stationLineDTOs) {
        return stationLineDTOs.stream().map(b -> toModel(b)).collect(Collectors.toList());
    }
}
