package demo.demo.Repository;


import org.springframework.data.jpa.repository.JpaRepository;

import demo.demo.Models.Bus;


public interface BusRepository extends JpaRepository<Bus, Long> {
}
