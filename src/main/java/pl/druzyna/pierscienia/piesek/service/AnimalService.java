package pl.druzyna.pierscienia.piesek.service;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.druzyna.pierscienia.piesek.entity.Animal;
import pl.druzyna.pierscienia.piesek.entity.Picture;
import pl.druzyna.pierscienia.piesek.repository.AnimalRepository;

import javax.transaction.Transactional;
import javax.validation.ValidationException;
import java.io.*;
import java.nio.file.Files;
import java.util.UUID;


@Service
public class AnimalService {

    private final AnimalRepository animalRepository;

    @Value("${animalPictures.directory}")
    private String picturesDirectory;

    public AnimalService(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    @Transactional
    @PreAuthorize("hasRole('MANAGE_PET_CATALOG')")
    public Animal save(Animal animal, MultipartFile image) throws IOException {
        if (image != null) {
            var pictureUuid = UUID.randomUUID();
            if (image.getContentType() == null || !image.getContentType().startsWith("image/")) {
                throw new ValidationException("File must be an image.");
            }
            var dir = new File(picturesDirectory);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            try (FileOutputStream fileOutputStream =
                         new FileOutputStream(picturesDirectory + "/" + pictureUuid)) {
                IOUtils.copy(image.getInputStream(), fileOutputStream);
            }
            var picture = new Picture(pictureUuid, image.getContentType());
            animal.setPicture(picture);
        } else {
            animal.setPicture(null);
        }
        return animalRepository.save(animal);
    }

    @Transactional
    @PreAuthorize("hasRole('MANAGE_PET_CATALOG')")
    public Page<Animal> getAnimals(Pageable pageable) {
        return animalRepository.findAll(pageable);
    }

    @Transactional
    @PreAuthorize("hasRole('MANAGE_PET_CATALOG')")
    public Animal getAnimal(Long id) {
        return animalRepository.findById(id).orElse(null);
    }

    public Pair<byte[], String> getImage(UUID pictureId, Long animalId) throws IOException {
        var bytes = Files.readAllBytes(new File(picturesDirectory + "/" + pictureId.toString()).toPath());
        var mimeType = this.getAnimal(animalId).getPicture().getMimeType();
        return Pair.of(bytes, mimeType);
    }

    @Transactional
    @PreAuthorize("hasRole('MANAGE_PET_CATALOG')")
    public void delete(Long id) {
        animalRepository.deleteById(id);
    }
}
