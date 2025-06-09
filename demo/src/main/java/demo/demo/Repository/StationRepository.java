package demo.demo.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.demo.Models.Station;

public interface StationRepository extends JpaRepository<Station, Long> {
    Optional<Station> findByNumber(String number);

}
