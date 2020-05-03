package pl.druzyna.pierscienia.piesek.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
public class Disease {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Date startDate;

    private Date endDate;

    @Length(max = 1000)
    private String description;

    @NotEmpty
    private String name;
}
