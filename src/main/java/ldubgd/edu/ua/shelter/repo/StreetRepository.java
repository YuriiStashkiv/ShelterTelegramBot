package ldubgd.edu.ua.shelter.repo;

import ldubgd.edu.ua.shelter.models.Street;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StreetRepository extends JpaRepository<Street, Long> {
    List<Street> findByLongitudeBetweenAndLatitudeBetween(double longitudeFrom, double longitudeTo, double latitudeFrom, double latitudeTo);
}
