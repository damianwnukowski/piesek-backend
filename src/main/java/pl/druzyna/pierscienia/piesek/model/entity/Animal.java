package pl.druzyna.pierscienia.piesek.model.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

import static javax.persistence.TemporalType.TIMESTAMP;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String name;

    private int weight;

    private Date birthDate;

    @CreatedDate
    @Temporal(TIMESTAMP)
    private Date createdDate;

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
