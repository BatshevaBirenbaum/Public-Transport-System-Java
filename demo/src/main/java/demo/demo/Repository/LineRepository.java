package demo.demo.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import demo.demo.Models.Line;

@Repository
public interface LineRepository extends JpaRepository<Line, Long> {
        Optional<Line> findByNumber(String number);

}
