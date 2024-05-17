package ldubgd.edu.ua.shelter.service;

import ldubgd.edu.ua.shelter.models.Street;

import java.util.List;

public interface StreetServiceInterface {
    public Street saveStreet(Street street);

    public List<Street> getAllStreets();

    public List<Street> getAllNeededStreets(double longitudeFrom, double longitudeTo, double latitudeFrom, double latitudeTo);

    public List<Street> getNeededStreetsByUserCoordinates(double longitude, double latitude);
}
