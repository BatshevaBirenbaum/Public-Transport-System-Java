package demo.demo.Converters;

import java.util.List;
import java.util.stream.Collectors;

import demo.demo.DTO.BusDTO;
import demo.demo.Models.Bus;

public class BusConverters {
    public static BusDTO toDTO(Bus bus) {
        BusDTO busDTO = new BusDTO();
        busDTO.setId(bus.getId());
        busDTO.setLicensePlate(bus.getLicensePlate());
        busDTO.setSeats(bus.getSeats());
        return busDTO;
    }

    public static Bus toModel(BusDTO busDTO) {
        Bus bus = new Bus();
        bus.setId(busDTO.getId());
        bus.setLicensePlate(busDTO.getLicensePlate());
        bus.setSeats(busDTO.getSeats());
        return bus;
    }

    public static List<BusDTO> toListDto(List<Bus> bus) {
        return bus.stream().map(b -> toDTO(b)).collect(Collectors.toList());
    }

    public static List<Bus> toListModel(List<BusDTO> busDto) {
        return busDto.stream().map(b -> toModel(b)).collect(Collectors.toList());
    }
}
