package demo.demo.Converters;

import java.util.List;
import java.util.stream.Collectors;

import demo.demo.DTO.TravelDTO;
import demo.demo.Models.Bus;
import demo.demo.Models.Driver;
import demo.demo.Models.Line;
import demo.demo.Models.Travel;

public class TravelConverters {

  public static TravelDTO tDto(Travel travel) {

    TravelDTO travelDTO = new TravelDTO();
    travelDTO.setId(travel.getId());
    travelDTO.setDepartureTime(travel.getDepartureTime());
    travelDTO.setBusId(travel.getBus().getId());
    travelDTO.setDriverId(travel.getDriver().getId());
    travelDTO.setLineId(travel.getLine().getId());

    return travelDTO;
  }

  public static Travel toModel(TravelDTO travelDTO) {
    Travel travel = new Travel();
    travel.setId(travelDTO.getId());
    travel.setDepartureTime(travelDTO.getDepartureTime());

    Bus bus = new Bus();
    bus.setId(travelDTO.getBusId());
    travel.setBus(bus);

    Driver driver = new Driver();
    driver.setId(travelDTO.getDriverId());
    travel.setDriver(driver);

    Line line = new Line();
    line.setId(travelDTO.getLineId());
    travel.setLine(line);

    return travel;
  }

  public static List<TravelDTO> toListDto(List<Travel> travels) {
    return travels.stream().map(b -> tDto(b)).collect(Collectors.toList());
  }

  public static List<Travel> toListModel(List<TravelDTO> travelDTOs) {
    return travelDTOs.stream().map(b -> toModel(b)).collect(Collectors.toList());
  }

}
