package demo.demo.Converters;

import java.util.List;
import java.util.stream.Collectors;

import demo.demo.DTO.StationDTO;
import demo.demo.Models.Station;

public class StationConverter {

    public static StationDTO toDTO(Station station) {

        StationDTO stationDTO = new StationDTO();
        stationDTO.setId(station.getId());
        stationDTO.setName(station.getName());
        stationDTO.setNumber(station.getNumber());
        return stationDTO;
    }

    public static Station toModel(StationDTO stationDTO) {
        Station station = new Station();
        station.setId(stationDTO.getId());
        station.setName(stationDTO.getName());
        station.setNumber(stationDTO.getNumber());
        return station;
    }

    public static List<StationDTO> toListDto(List<Station> station) {
        return station.stream().map(s -> toDTO(s)).collect(Collectors.toList());
    }

    public static List<Station> toListModel(List<StationDTO> stationDto) {
        return stationDto.stream().map(s -> toModel(s)).collect(Collectors.toList());
    }
}
