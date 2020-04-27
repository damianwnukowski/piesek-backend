package pl.druzyna.pierscienia.piesek.dto.animal;

import lombok.Data;

import java.util.Date;

@Data
public class AnimalDto {
    private Long id;
    private String name;
    private int weight;
    private Date birthDate;
    private boolean isBirthDateApproximated;
    private String description;
    private String species;
}
