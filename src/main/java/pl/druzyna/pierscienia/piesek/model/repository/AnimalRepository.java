package pl.druzyna.pierscienia.piesek.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.druzyna.pierscienia.piesek.model.entity.Animal;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {
    Page<Animal> findAllByNameContainingIgnoreCaseAndSpeciesContainingIgnoreCase(Pageable pageable, String name,
                                                                                 String species);
}
