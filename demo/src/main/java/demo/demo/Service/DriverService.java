package demo.demo.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import demo.demo.Converters.DriverConverters;
import demo.demo.DTO.DriverDTO;
import demo.demo.Models.Driver;
import demo.demo.Repository.DriverRepository;

@Service
public class DriverService {

    @Autowired
    private DriverRepository driverRepository;

    public boolean createDriver(DriverDTO driverDTO) {
        Driver driver = DriverConverters.toModel(driverDTO);
        if (!driverRepository.exists(Example.of(driver))) {
            driverRepository.save(driver);
            return true;
        }
        return false;
    }

    public List<DriverDTO> getAllDrivers() {
        return DriverConverters.toListDto(driverRepository.findAll());
    }
}
