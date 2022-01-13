package All.domain.manufacuturer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor@Data
public class Manufacturer {
    @Id
    private Integer code;
    private String name;
}
