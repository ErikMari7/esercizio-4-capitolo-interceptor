package co.develhope.esercizio4.capitolo.interceptors.Controllers;

import co.develhope.esercizio4.capitolo.interceptors.Entities.Flight;
import co.develhope.esercizio4.capitolo.interceptors.Enum.FlightStatus;
import co.develhope.esercizio4.capitolo.interceptors.Repositories.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/flights")
public class FlightController {
    @Autowired
    private FlightRepository flightRepository;

    private static final Random random = new Random();

    public String generateRandomString() {
        return random.ints(97, 122)
                .limit(10)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public FlightStatus generateRandomStatus() {
        FlightStatus[] statuses = FlightStatus.values();
        int randomIndex = random.nextInt(statuses.length);
        return statuses[randomIndex];
    }

    @PostMapping("/provision")
    public List<Flight> provisionFlights(@RequestParam Optional<Integer> n) {
        int numberOfFlights = n.orElse(100);
        List<Flight> flightList = new ArrayList<>();
        for (int i = 1; i <= numberOfFlights; i++) {
            Flight flight = new Flight();
            flight.setDescription(generateRandomString());
            flight.setFromAirport(generateRandomString());
            flight.setToAirport(generateRandomString());
            flight.setFlightStatus(generateRandomStatus());
            flightList.add(flight);
        }
        return flightRepository.saveAll(flightList);
    }
    @GetMapping("/retrievingall")
    public Page<Flight> retrievingAllFlights(@RequestParam int page,@RequestParam int size){
        String sortByField = "fromAirport";
        Pageable pageable = PageRequest.of(page,size,Sort.by(Sort.Direction.ASC,sortByField));
        return flightRepository.findAll(pageable);
    }
    @GetMapping("/retrievingontime")
    public List<Flight> retrievingAllFlightOnTime(){
        return flightRepository.findByFlightStatus(FlightStatus.ONTIME);
    }
    @GetMapping("/retrievingallinp1orp2")
    public List<Flight> retrievingAllFlightInP1OrP2(@RequestParam FlightStatus p1,@RequestParam FlightStatus p2){
        List<FlightStatus> statusList = Arrays.asList(p1,p2);
        return flightRepository.findAllByFlightStatusIn(statusList);
    }
}
