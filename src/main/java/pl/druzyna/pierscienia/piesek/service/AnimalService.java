package pl.druzyna.pierscienia.piesek.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import pl.druzyna.pierscienia.piesek.entity.Animal;
import pl.druzyna.pierscienia.piesek.repository.AnimalRepository;

import javax.transaction.Transactional;


@Service
@PreAuthorize("hasRole('MANAGE_PET_CATALOG')")
public class AnimalService {

    private final AnimalRepository animalRepository;

    public AnimalService(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    @Transactional
    public void save(Animal animal) {
        animalRepository.save(animal);
    }

    @Transactional
    public Page<Animal> getAnimals(Pageable pageable) {
        return animalRepository.findAll(pageable);
    }

    @Transactional
    public Animal getAnimalDto(Long id) {
        return animalRepository.findById(id).orElse(null);
    }
}
