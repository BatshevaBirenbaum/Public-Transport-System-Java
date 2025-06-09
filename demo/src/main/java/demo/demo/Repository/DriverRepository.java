package demo.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.demo.Models.Driver;

public interface DriverRepository extends JpaRepository<Driver,Long>{
}
