package demo.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.demo.Models.LineStationId;
import demo.demo.Models.StationLine;

public interface StationLineRepository extends JpaRepository<StationLine, LineStationId> {
    List<StationLine> findByLineId(Long lineId);

    List<StationLine> findByStationId(Long lineId);

    StationLine findByStationOrder(int order);

    boolean deleteByStationIdAndLineId(Long LineId, Long StationId);
}
