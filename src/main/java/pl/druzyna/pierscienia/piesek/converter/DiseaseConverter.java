package pl.druzyna.pierscienia.piesek.converter;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import pl.druzyna.pierscienia.piesek.dto.animal.DiseaseDto;
import pl.druzyna.pierscienia.piesek.entity.Disease;

@Component
public class DiseaseConverter {
    public Disease convertToEntity(DiseaseDto diseaseDto) {
        Disease disease = new Disease();
        BeanUtils.copyProperties(diseaseDto, disease);
        return disease;
    }

    public DiseaseDto convertToDto(Disease disease) {
        DiseaseDto diseaseDto = new DiseaseDto();
        BeanUtils.copyProperties(disease, diseaseDto);
        return diseaseDto;
    }
}
