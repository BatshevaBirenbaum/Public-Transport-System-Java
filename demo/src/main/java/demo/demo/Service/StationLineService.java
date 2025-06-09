package demo.demo.Service;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import demo.demo.Converters.StationLineConverter;
import demo.demo.DTO.ArrivalTimesDTO;
import demo.demo.DTO.StationLineDTO;
import demo.demo.Models.Line;
import demo.demo.Models.Station;
import demo.demo.Models.StationLine;
import demo.demo.Models.Travel;
import demo.demo.Repository.LineRepository;
import demo.demo.Repository.StationLineRepository;
import demo.demo.Repository.StationRepository;
import demo.demo.Repository.TravelRepository;

@Service
public class StationLineService {
    @Autowired
    private StationLineRepository stationLineRepository;
    @Autowired
    private LineRepository lineRepository;
    @Autowired
    private StationRepository stationRepository;
    @Autowired
    private TravelRepository travelRepository;

    public List<StationLineDTO> getAllStationLines() {
        return StationLineConverter.toListDto(stationLineRepository.findAll());
    }

    public boolean createStationLine(StationLineDTO stationLineDTO) {
        boolean flag = false;
        StationLine stationLine = StationLineConverter.toModel(stationLineDTO);
        List<StationLine> slList = stationLineRepository.findByLineId(stationLineDTO.getLineId());
        slList.sort(Comparator.comparing(StationLine::getStationOrder));

        for (StationLine slDto : slList) {
            if (slDto.getStationOrder() == stationLineDTO.getStationOrder()) {
                flag = true;
            }
            if (flag == true) {
                slDto.setStationOrder(slDto.getStationOrder() + 1);
                stationLineRepository.save(slDto);
            }

        }

        try {
            Optional<Line> optionalLine = lineRepository.findById(stationLineDTO.getLineId());
            Optional<Station> optionalStation = stationRepository.findById(stationLineDTO.getStationId());

            if (optionalLine.isEmpty() || optionalStation.isEmpty()) {
                return false;
            }

            stationLine.setLine(optionalLine.get());
            stationLine.setStation(optionalStation.get());

            if (!stationLineRepository.exists(Example.of(stationLine))) {
                stationLineRepository.save(stationLine);
                return true;
            } else {
                System.out.println("StationLine already exists.");
            }
        } catch (Exception e) {
            System.out.println("Error during save: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteByStationIdAndLineId(Long lineId, Long stationId) {
        List<StationLine> slList = stationLineRepository.findByLineId(lineId);
        slList.sort(Comparator.comparing(StationLine::getStationOrder));
        boolean flag = false;
        for (StationLine sl : slList) {
            if (!flag && sl.getStation().getId() == stationId) {
                stationLineRepository.delete(sl);
                flag = true;
            } else if (flag) {
                sl.setStationOrder(sl.getStationOrder() - 1);
                stationLineRepository.save(sl);
            }

        }
        if (flag == true)
            return true;
        return false;
    }

    // חיפוש לפי תחנה
    public List<String> searchByStation(String stationNumber, String lineNumber) {
        // רשימה מסוג מחלקה שעשיתי כדי שאני אשמור בה שדה עם שעת הגעה.busController
        // ואז מיינתי לפי שעות הגעה מהקטן לגדול
        List<ArrivalTimesDTO> ArrivalTimes = new ArrayList<>();
        List<String> results = new ArrayList<>();
        // כל אובייקט התחנה שעליה מתבצע חיפוש
        Optional<Station> station = stationRepository.findByNumber(stationNumber);
        // אם אין כזו תחנה
        if (station.isEmpty()) {
            results.add("לא נמצא מידע בזמן הקרוב או שחלק מהנתונים שהוקשו שגוי");
            return results;
        }
        // קוד תחנה
        Long stationId = station.get().getId();
        // יכיל רשימה של קודי קו שיש בתחנה המבוקשת
        List<Long> allLinesId = new ArrayList<>();
        // כל אובייקט תחנה-קו התחנה שעליה מתבצע חיפוש
        List<StationLine> slList = stationLineRepository.findByStationId(stationId);
        // אם המשתמש מחפש קו מסויים ולא את כל הקווים בתחנה
        if (!lineNumber.equals("*")) {
            // אפשר למצוא בקלות את הקו שרוצים בתחנה המבוקשת
            Optional<Line> line = lineRepository.findByNumber(lineNumber);
            // אם הקו לא קיים
            if (line.isEmpty()) {
                results.add("הקו שהוזן לא קיים");
                return results;
            }
            allLinesId.add(line.get().getId());
        } else {
            // * אם המשתמש הכניס
            // עובד בלולאה על כל הקווים שנמצאים בתחנה המבוקשת ומכניסים לרשימה
            for (StationLine sl : slList) {
                allLinesId.add(sl.getLine().getId());
            }
        }
        // מעבר על רשימת הקווים
        for (Long lineId : allLinesId) {
            // מציאת אובייקט הקו לפי קוד קו
            Optional<Line> thisLine = lineRepository.findById(lineId);
            // מציאת אובייקט קו-תחנה לפי קוד קו
            List<StationLine> allStationByLine = stationLineRepository.findByLineId(lineId);
            // מציאת האובייקט המדוייק גם לפי תחנה וגם לפי קו
            Optional<StationLine> stationInLine = allStationByLine.stream()
                    .filter(s -> s.getStation().getId() == stationId)
                    .findFirst();

            if (stationInLine == null) {
                results.add("התחנה לא משויכת לקו הזה");
                return results;
            }

            // רשימת כל הנסיעות של הקו
            List<Travel> lTravels = travelRepository.findByLineId(lineId);
            lTravels.sort(Comparator.comparing(Travel::getDepartureTime));

            // יציאת רשימה שמסיינת את הנסיעות שסוננו כבר לפי קוד קו יסוננו עכשיו רק לנסיעות
            // שעוד לא חלפו על התחנה
            List<Travel> filteTravels = lTravels.stream()
                    .filter(t -> !t.getDepartureTime()
                            .isBefore(LocalTime.now().minusMinutes(stationInLine.get().getStationOrder())))
                    .collect(Collectors.toList());

            int count = 0;
            // מעבר על רשימת הנסיעות המסוננת
            for (Travel travel : filteTravels) {
                if (count == 3)
                    break;
                LocalTime now = LocalTime.now();
                // חישוב זמן יציאת + מס התחנות עד לתחנה הנוכחית
                // כלומר שעת הגעה
                LocalTime expectedTime = travel.getDepartureTime().plusMinutes(stationInLine.get().getStationOrder());
                // מחשב עוד כמה זמן יגיע לתחנה
                long minutesDiff = Duration.between(now, expectedTime).toMinutes();
                // לבדיקה האם האוטובוס יצא או לא
                long diff = Duration.between(now, travel.getDepartureTime()).toMinutes();
                // אם הוא יצא והוא כבר על המסלול
                if (diff < 0) {
                    String str = "line " + thisLine.get().getNumber() + " from " + thisLine.get().getSource() + "to "
                            + thisLine.get().getDestination()
                            + " Will arrive in a while "
                            + minutesDiff+" minutes.";
                    // ArrivalTimesDTO הוספה לאובייקט
                    ArrivalTimesDTO item = new ArrivalTimesDTO(str, LocalTime.now().plusMinutes(minutesDiff));

                    ArrivalTimes.add(item);

                }
                // אם עוד לא יצא מתחנת המוצא
                else {
                    String str = "line " + thisLine.get().getNumber() + " Departed from the starting station at "
                            + travel.getDepartureTime() + " and will arrive at the requested station at "
                            + expectedTime;
                    LocalTime departureTime = travel.getDepartureTime()
                            .plusMinutes(stationInLine.get().getStationOrder());
                    ArrivalTimesDTO item = new ArrivalTimesDTO(str, departureTime);
                    ArrivalTimes.add(item);
                    // עד 3 הודעות על קו שלא יצא
                    count++;
                }
            }
        }
        // מיון הרשימה לפי שעת הכעה מהקטן לגדול
        ArrivalTimes.sort(Comparator.comparing(ArrivalTimesDTO::getArrivalTime));

        // צודיע מקסימום על 7 נתונים
        for (int i = 0; i < ArrivalTimes.size() && i < 7; i++) {
            results.add(ArrivalTimes.get(i).getMessage());
        }

        return results;
    }

}