package ldubgd.edu.ua.shelter.algorithms;

import ldubgd.edu.ua.shelter.models.Street;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Algorithms {

    // Радіус Землі в кілометрах
    private static final double EARTH_RADIUS = 6371.0;
    public double[] UserCoordinatesToSquare(double longitude, double latitude){
        double[] userCoordinatesToSquare = new double[4];
        userCoordinatesToSquare[0] = (longitude - 0.002);
        userCoordinatesToSquare[1] = (longitude + 0.002);
        userCoordinatesToSquare[2] = (latitude - 0.002);
        userCoordinatesToSquare[3] = (latitude + 0.002);
        return userCoordinatesToSquare;
    }

    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        // Перевести градуси в радіани
        double lat1Rad = Math.toRadians(lat1);
        double lat2Rad = Math.toRadians(lat2);
        double lon1Rad = Math.toRadians(lon1);
        double lon2Rad = Math.toRadians(lon2);

        // Формула Гаверсина
        double dLat = lat2Rad - lat1Rad;
        double dLon = lon2Rad - lon1Rad;
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Обчислити відстань у метрах
        double distance = (EARTH_RADIUS * c) * 1000;

        return distance;
    }
}
