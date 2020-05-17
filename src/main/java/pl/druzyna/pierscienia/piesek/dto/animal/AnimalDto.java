package pl.druzyna.pierscienia.piesek.dto.animal;

import lombok.Data;

import java.net.URI;
import java.util.Date;
import java.util.List;

@Data
public class AnimalDto {
    private Long id;
    private String name;
    private int weight;
    private Date birthDate;
    private boolean isBirthDateApproximated;
    private String description;
    private String species;
    private URI pictureLocation;
    private List<DiseaseDto> diseases;
    private Date createdDate;
}
