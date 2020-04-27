package pl.druzyna.pierscienia.piesek.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.druzyna.pierscienia.piesek.entity.Animal;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {
}
