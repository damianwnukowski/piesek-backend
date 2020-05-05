package pl.druzyna.pierscienia.piesek.converter;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.druzyna.pierscienia.piesek.dto.animal.AnimalDto;
import pl.druzyna.pierscienia.piesek.entity.Animal;
import pl.druzyna.pierscienia.piesek.entity.Disease;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AnimalConverter {
    private final DiseaseConverter diseaseConverter;

    public AnimalConverter(DiseaseConverter diseaseConverter) {
        this.diseaseConverter = diseaseConverter;
    }

    public Animal convertDtoToEntity(AnimalDto animalDto) {
        var animal = new Animal();
        BeanUtils.copyProperties(animalDto, animal);
        List<Disease> diseases = animalDto.getDiseases().stream()
                .map(diseaseConverter::convertToEntity)
                .collect(Collectors.toList());
        animal.setDiseases(diseases);
        return animal;
    }

    public AnimalDto convertEntityToDto(Animal animal) {
        var animalDto = new AnimalDto();
        BeanUtils.copyProperties(animal, animalDto);
        var diseaseDtos = animal.getDiseases().stream()
                .map(diseaseConverter::convertToDto)
                .collect(Collectors.toList());
        animalDto.setDiseases(diseaseDtos);

        if (animal.getPicture() != null) {
            animalDto.setPictureLocation(
                    ServletUriComponentsBuilder.fromCurrentContextPath()
                            .path("/animal/{animalId}/picture/{id}")
                            .queryParams(null)
                            .buildAndExpand(animal.getId(), animal.getPicture().getUuid()).toUri());
        }
        return animalDto;
    }
}
