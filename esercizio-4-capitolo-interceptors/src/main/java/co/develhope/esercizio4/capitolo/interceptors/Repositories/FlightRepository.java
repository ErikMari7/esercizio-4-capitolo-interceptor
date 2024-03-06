package co.develhope.esercizio4.capitolo.interceptors.Repositories;

import co.develhope.esercizio4.capitolo.interceptors.Entities.Flight;
import co.develhope.esercizio4.capitolo.interceptors.Enum.FlightStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight,Long> {

    List<Flight> findByFlightStatus(FlightStatus flightStatus);

    List<Flight> findAllByFlightStatusIn(Collection<FlightStatus> flightStatuses);
}
