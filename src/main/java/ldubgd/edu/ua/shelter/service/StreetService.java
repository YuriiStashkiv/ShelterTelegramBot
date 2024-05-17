package ldubgd.edu.ua.shelter.service;

import ldubgd.edu.ua.shelter.algorithms.Algorithms;
import ldubgd.edu.ua.shelter.models.Street;
import ldubgd.edu.ua.shelter.repo.StreetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StreetService implements StreetServiceInterface{

    @Autowired
    private StreetRepository streetRepository;
    @Autowired
    private Algorithms algorithms;

    @Override
    public Street saveStreet(Street street) {
        return streetRepository.save(street);
    }

    @Override
    public List<Street> getAllStreets() {
        return streetRepository.findAll();
    }

    @Override
    public List<Street> getAllNeededStreets(double longitudeFrom, double longitudeTo, double latitudeFrom, double latitudeTo){
        return streetRepository.findByLongitudeBetweenAndLatitudeBetween(longitudeFrom, longitudeTo, latitudeFrom, latitudeTo);
    }

    @Override
    public List<Street> getNeededStreetsByUserCoordinates(double longitude, double latitude){
        double[] doubles = algorithms.UserCoordinatesToSquare(longitude, latitude);
        return getAllNeededStreets(doubles[0], doubles[1], doubles[2], doubles[3]);
    }

}
