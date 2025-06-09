package demo.demo.Repository;

import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.demo.Models.Travel;

public interface TravelRepository extends JpaRepository<Travel, Long> {
    List<Travel> findByLineId(Long id);

    List<Travel> findByDepartureTimeGreaterThanEqual(LocalTime time);

}
