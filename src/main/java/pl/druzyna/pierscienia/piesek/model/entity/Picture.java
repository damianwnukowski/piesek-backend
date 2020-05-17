package pl.druzyna.pierscienia.piesek.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.util.UUID;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Picture {
    private UUID uuid;
    private String mimeType;
}
