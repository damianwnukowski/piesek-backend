package pl.druzyna.pierscienia.piesek.dto.animal;

import lombok.Data;

import java.util.Date;

@Data
public class DiseaseDto {
    private Long id;
    private Date startDate;
    private Date endDate;
    private String name;
    private String description;
}
