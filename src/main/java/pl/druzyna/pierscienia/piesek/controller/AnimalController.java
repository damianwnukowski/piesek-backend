package pl.druzyna.pierscienia.piesek.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.druzyna.pierscienia.piesek.dto.animal.AnimalDto;
import pl.druzyna.pierscienia.piesek.entity.Animal;
import pl.druzyna.pierscienia.piesek.service.AnimalService;

@RestController
@RequestMapping("/animal")
public class AnimalController {

    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @GetMapping("/{id}")
    public AnimalDto getAnimal(@PathVariable Long id) {
        AnimalDto animalDto = new AnimalDto();
        BeanUtils.copyProperties(animalService.getAnimalDto(id), animalDto);
        return animalDto;
    }

    @GetMapping
    public Page<AnimalDto> getAnimals(Pageable pageable) {
        return animalService.getAnimals(pageable)
                .map(animal -> {
                    AnimalDto animalDto = new AnimalDto();
                    BeanUtils.copyProperties(animal, animalDto);
                    return animalDto;
                });
    }

    @PostMapping
    public void addAnimal(@RequestBody AnimalDto animalDto) {
        Animal animal = new Animal();
        BeanUtils.copyProperties(animalDto, animal);
        animalService.save(animal);
    }

    @PostMapping("/{animalId}/picture")
    public void uploadPicture(@PathVariable String animalId, @RequestParam("file") MultipartFile multipartFile) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/{animalId}/picture")
    public ResponseEntity<Resource> downloadFile(@PathVariable String animalId) {
        throw new UnsupportedOperationException();
    }

    @DeleteMapping("/{id}")
    public void deleteAnimal(@PathVariable Long id) {
        throw new UnsupportedOperationException();
    }

    @PutMapping("/{id}")
    public void updateAnimal(@RequestBody AnimalDto animalDto) {
        throw new UnsupportedOperationException();
    }
}
