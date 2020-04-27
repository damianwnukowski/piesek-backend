package pl.druzyna.pierscienia.piesek.controller;

import org.springframework.web.bind.annotation.*;
import pl.druzyna.pierscienia.piesek.dto.animal.DiseaseDto;

import java.util.List;

@RestController
@RequestMapping("/animal/{animalId}/disease")
public class DiseaseController {

    @GetMapping
    public List<DiseaseDto> getDiseases(@PathVariable Long animalId) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/{id}")
    public DiseaseDto getDisease(@PathVariable Long animalId, @PathVariable Long id) {
        throw new UnsupportedOperationException();
    }

    @PostMapping
    public void addDisease(@PathVariable Long animalId, @RequestBody DiseaseDto diseaseDto) {
        throw new UnsupportedOperationException();
    }

    @PutMapping
    public void editDisease(@PathVariable Long animalId, @RequestBody DiseaseDto diseaseDto) {
        throw new UnsupportedOperationException();
    }

    @DeleteMapping("/{id}")
    public void deleteDisease(@PathVariable Long animalId, @PathVariable Long id) {
        throw new UnsupportedOperationException();
    }
}
