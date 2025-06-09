package demo.demo.Service;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import demo.demo.Converters.LineConverter;
import demo.demo.DTO.LineDTO;
import demo.demo.Models.Line;
import demo.demo.Models.Station;
import demo.demo.Models.StationLine;
import demo.demo.Models.Travel;
import demo.demo.Repository.LineRepository;
import demo.demo.Repository.StationLineRepository;
import demo.demo.Repository.StationRepository;
import demo.demo.Repository.TravelRepository;

@Service
public class LineService {
    @Autowired
    private LineRepository lineRepository;
    @Autowired
    private StationLineRepository stationLineRepository;
    @Autowired
    private StationRepository stationRepository;
    @Autowired
    private TravelRepository travelRepository;

    public List<LineDTO> getAllLines() {
        return LineConverter.toListDto(lineRepository.findAll());
    }

    public boolean createLine(LineDTO lineDTO) {
        Line line = LineConverter.toModel(lineDTO);
        if (!lineRepository.exists(Example.of(line))) {
            lineRepository.save(line);
            return true;
        }
        return false;
    }

    // חיפוש לפי קו
    // למידע על מיקומם של האוטובוסים לאורך כל ציר הנסיעה

    public List<String> theLocationOfTheBusesAlongTheRoute(String lineNumber) {
        List<String> locations = new ArrayList<>();

        // line - מכיל את אובייקט הקו לפי מספר קו שהוכנס
        Optional<Line> line = lineRepository.findByNumber(lineNumber);
        if (line.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Line not found");
        }
        Long lineId = line.get().getId();

        // מכיל את כל הנסיעות של אותו קו lineTravels
        List<Travel> lineTravels = travelRepository.findByLineId(lineId);

        // מסנן נסיעות שאין להן שעת יציאה
        lineTravels.removeIf(travel -> travel.getDepartureTime() == null);
        // ממיין את הנסיעות לפי שעת היציאה שלהן, מהכי מוקדם להכי מאוחר
        lineTravels.sort(Comparator.comparing(Travel::getDepartureTime));

        // את כל התחנות של הקו
        List<StationLine> staionList = stationLineRepository.findByLineId(lineId);
        // מספר התחנות של הקו
        int numStation = staionList.size();

        List<Travel> onTravels = new ArrayList<>();
        LocalTime minTime = LocalTime.now().minusMinutes(numStation);
        // נסיעותשיצאו לפני יותר ממספר התחנות של הקו הסתיימו
        for (Travel travel : lineTravels) {
            if (!travel.getDepartureTime().isBefore(minTime)) {
                onTravels.add(travel);
            }
        }

        if (onTravels.isEmpty()) {
            // מצא את שעת הנסיעה הראשונה ביום
            Travel firstTravel = lineTravels.stream()
                    .filter(travel -> travel.getDepartureTime() != null)
                    .findFirst()
                    .orElse(null);

            if (firstTravel != null) {
                LocalTime firstDepartureTime = firstTravel.getDepartureTime();
                return List.of("Departed from the starting station at  " + firstDepartureTime + " o'clock.");
            } else {
                return List.of("No travels available for this line.");
            }
        }

        onTravels.sort(Comparator.comparing(Travel::getDepartureTime));

        for (int i = 0; i < onTravels.size(); i++) {
            Travel t = onTravels.get(i);
            LocalTime departure = t.getDepartureTime();

            // מחשב כמה דקות עברו מאז שיצאה הנסיעה
            if (minTime.isBefore(departure)) {
                long minutesDiff = Duration.between(departure, LocalTime.now()).toMinutes() + 1;
                int roundedMinutes = (int) Math.ceil((double) minutesDiff);

                // מנסה למצוא את התחנה לפי הסדר שלה – כלומר לפי כמה דקות עברו מאז היציאה

                StationLine byOrder = staionList.stream()
                        .filter(sl -> sl.getStationOrder() == roundedMinutes)
                        .findFirst()
                        .orElse(null);

                if (byOrder == null) {
                    locations.add("Departed from the starting station at " + departure + " o'clock");
                    break;
                } else {
                    Optional<Station> station = stationRepository.findById(byOrder.getStation().getId());
                    if (station.isPresent()) {
                        locations.add("Near the " + station.get().getName() + " station.");
                    } else {
                        System.out.println(" Station not found for ID: " + byOrder.getStation().getId());
                    }
                }
            }
        }
        return locations;
    }

    // לשמיעת כל תחנות הקו
    public List<String> allStationsOnTheLine(String lineNumber) {

        List<String> allStations = new ArrayList<>();
        Optional<Line> lines = lineRepository.findByNumber(lineNumber);
        List<StationLine> stations = stationLineRepository.findByLineId(lines.get().getId());
        for (StationLine sl : stations) {
            Optional<Station> station = stationRepository.findById(sl.getStation().getId());

            if (station.isPresent()) {
                allStations.add(station.get().getName() + " - " + station.get().getNumber());
            }
        }
        return allStations;

    }

}
