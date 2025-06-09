package demo.demo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import demo.demo.Converters.StationConverter;
import demo.demo.DTO.StationDTO;
import demo.demo.Models.Station;
import demo.demo.Repository.StationRepository;

@Service
public class StationService {
    @Autowired
    private StationRepository stationRepository;

    public List<StationDTO> getAllStations() {
        return StationConverter.toListDto(stationRepository.findAll());
    }

    public boolean createStation(StationDTO stationDTO) {
        Station station = StationConverter.toModel(stationDTO);
        if (!stationRepository.exists(Example.of(station))) {
            stationRepository.save(station);
            return true;
        }
        return false;
    }

}
