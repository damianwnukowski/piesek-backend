package pl.druzyna.pierscienia.piesek.entity;

import lombok.Data;
import pl.druzyna.pierscienia.piesek.dto.animal.DiseaseDto;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Animal {
    public Animal() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String name;

    private int weight;

    private Date birthDate;

    @NotNull
    private boolean isBirthDateApproximated;

    private String description;

    private String species;

    @OneToMany
    private List<Disease> diseases;

    private String pictureId;
}
