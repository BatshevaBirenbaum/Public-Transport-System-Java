package demo.demo.Converters;

import java.util.List;
import java.util.stream.Collectors;

import demo.demo.DTO.DriverDTO;
import demo.demo.Models.Driver;

public class DriverConverters {

    public static DriverDTO toDTO(Driver driver) {

        DriverDTO driverDTO = new DriverDTO();
        driverDTO.setId(driver.getId());
        driverDTO.setName(driver.getName());
        driverDTO.setPhone(driver.getPhone());
        driverDTO.setRating(driver.getRating());

        return driverDTO;
    }

    public static Driver toModel(DriverDTO driverDTO) {
        Driver driver = new Driver();
        driver.setId(driverDTO.getId());
        driver.setName(driverDTO.getName());
        driver.setPhone(driverDTO.getPhone());
        driver.setRating(driverDTO.getRating());

        return driver;
    }

    public static List<DriverDTO> toListDto(List<Driver> drivers) {
        return drivers.stream().map(d -> toDTO(d)).collect(Collectors.toList());
    }

    public static List<Driver> toListModel(List<DriverDTO> driverDTOs) {
        return driverDTOs.stream().map(d -> toModel(d)).collect(Collectors.toList());
    }
}
