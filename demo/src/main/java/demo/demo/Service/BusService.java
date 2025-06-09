package demo.demo.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import demo.demo.Converters.BusConverters;
import demo.demo.DTO.BusDTO;
import demo.demo.Models.Bus;
import demo.demo.Repository.BusRepository;

@Service
public class BusService {
    @Autowired
    private BusRepository busRepository;

    public List<BusDTO> getAllBuses() {
        return BusConverters.toListDto(busRepository.findAll());
    }

    public boolean createBus(BusDTO busDTO) {
        Bus bus = BusConverters.toModel(busDTO);
        if (!busRepository.exists(Example.of(bus))) {
            busRepository.save(bus);
            return true;
        }
        return false;
    }
}
