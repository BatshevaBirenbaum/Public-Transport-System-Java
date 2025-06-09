package demo.demo.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import demo.demo.Converters.TravelConverters;
import demo.demo.DTO.TravelDTO;
import demo.demo.Models.Line;
import demo.demo.Models.Travel;
import demo.demo.Repository.LineRepository;
import demo.demo.Repository.TravelRepository;

@Service
public class TravelService {
    @Autowired
    private TravelRepository travelRepository;
    @Autowired
    private LineRepository lineRepository;

    public boolean createTravel(TravelDTO travelDTO) {
        Travel travel = TravelConverters.toModel(travelDTO);
        if (!travelRepository.exists(Example.of(travel))) {
            travelRepository.save(travel);
            return true;
        }
        return false;
    }

    public List<TravelDTO> getAllTravel() {
        return TravelConverters.toListDto(travelRepository.findAll());
    }

    // לוחות זמנים

    // כל הנסיעות
    public List<LocalTime> allTheTravels(String lineNumber) {

        List<LocalTime> travelTimes = new ArrayList<>();
        Optional<Line> line = lineRepository.findByNumber(lineNumber);
        List<Travel> travelsByLine = travelRepository.findByLineId(line.get().getId());
        for (Travel t : travelsByLine) {
            travelTimes.add(t.getDepartureTime());
        }
        Collections.sort(travelTimes);
        return travelTimes;
    }

    // נסיעות בשעה מסויימת
    public List<LocalTime> travelAtCertainTime(String lineNumber, LocalTime time) {
        List<LocalTime> travelAt = new ArrayList<>();
        Optional<Line> line = lineRepository.findByNumber(lineNumber);
        if (line.isEmpty()) {
            return travelAt;
        }

        List<Travel> travels = travelRepository.findByLineId(line.get().getId());
        travels.sort(Comparator.comparing(Travel::getDepartureTime));

        List<Travel> filteTravels = travels.stream()
                .filter(t -> !t.getDepartureTime().isBefore(time))
                .collect(Collectors.toList());

        filteTravels.sort(Comparator.comparing(Travel::getDepartureTime));

        for (int i = 0; i < 5 && i < filteTravels.size(); i++) {
            travelAt.add(filteTravels.get(i).getDepartureTime());
        }
        if (travelAt.size() < 5) {
            int more = 5 - travelAt.size();
            for (int j = 0; j < more; j++) {
                travelAt.add(travels.get(j).getDepartureTime());
            }
        }
        return travelAt;

    }

    // נסיעה אחרונה ביום
    public LocalTime lastTravel(String lineNumber) {
        Optional<Line> line = lineRepository.findByNumber(lineNumber);
        List<Travel> travelsByLine = travelRepository.findByLineId(line.get().getId());
        travelsByLine.sort(Comparator.comparing(Travel::getDepartureTime));
        return travelsByLine.get(travelsByLine.size() - 1).getDepartureTime();
    }

}
