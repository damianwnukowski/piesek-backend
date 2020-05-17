package pl.druzyna.pierscienia.piesek.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.druzyna.pierscienia.piesek.model.entity.Disease;

@Repository
public interface DiseaseRepository extends JpaRepository<Disease, Long> {
}
