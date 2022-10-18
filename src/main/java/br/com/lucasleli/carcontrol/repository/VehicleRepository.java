package br.com.lucasleli.carcontrol.repository;

import br.com.lucasleli.carcontrol.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

}
