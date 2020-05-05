package pl.druzyna.pierscienia.piesek.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Animal {

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

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "animal_id")
    private List<Disease> diseases;

    @Embedded
    private Picture picture;
}
