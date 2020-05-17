package pl.druzyna.pierscienia.piesek.controller;

import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.druzyna.pierscienia.piesek.converter.AnimalConverter;
import pl.druzyna.pierscienia.piesek.dto.animal.AnimalDto;
import pl.druzyna.pierscienia.piesek.model.entity.Animal;
import pl.druzyna.pierscienia.piesek.service.AnimalService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/animal")
public class AnimalController {

    private final AnimalService animalService;
    private final AnimalConverter animalConverter;

    public AnimalController(AnimalService animalService, AnimalConverter animalConverter) {
        this.animalService = animalService;
        this.animalConverter = animalConverter;
    }

    @GetMapping("/{id}")
    public AnimalDto getAnimal(@PathVariable Long id) {
        return animalConverter.convertEntityToDto(animalService.getAnimal(id));
    }

    @GetMapping
    public Page<AnimalDto> getAnimals(@RequestParam(required = false) String name,
                                      @RequestParam(required = false) String species,
                                      Pageable pageable) {
        return animalService
                .getAnimals(pageable, Optional.ofNullable(name).orElse(""),
                        Optional.ofNullable(species).orElse(""))
                .map(animalConverter::convertEntityToDto);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Long addAnimal(@RequestPart(name = "animal") AnimalDto animalDto,
                          @RequestPart(required = false) MultipartFile image) throws IOException {
        var animal = animalConverter.convertDtoToEntity(animalDto);
        return animalService.save(animal, image).getId();
    }

    @GetMapping("/{animalId}/picture/{pictureId}")
    public ResponseEntity<byte[]> getPicture(@PathVariable Long animalId, @PathVariable UUID pictureId)
            throws IOException {
        var image = animalService.getImage(pictureId, animalId);

        var headers = new HttpHeaders();

        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
        headers.setContentType(MediaType.valueOf(image.getSecond()));
        return new ResponseEntity<>(image.getFirst(), headers, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteAnimal(@PathVariable Long id, HttpServletResponse response) {
        if (animalService.getAnimal(id) == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        animalService.delete(id);
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AnimalDto> updateAnimal(@RequestPart(name = "animal") AnimalDto animalDto,
                                                  @RequestPart(required = false) MultipartFile image) throws IOException {
        if (animalService.getAnimal(animalDto.getId()) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Animal animal = animalConverter.convertDtoToEntity(animalDto);
        return new ResponseEntity<>(animalConverter.convertEntityToDto(animalService.save(animal, image)),
                HttpStatus.OK);
    }
}
