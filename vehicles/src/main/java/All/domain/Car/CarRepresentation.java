package All.domain.Car;

import All.domain.Condition;
import All.domain.Location;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDateTime;

@Builder
@Getter
@EqualsAndHashCode(callSuper = false)
@Relation(itemRelation = "car", collectionRelation = "cars")
public class CarRepresentation extends RepresentationModel<CarRepresentation> {
    private final Long id;
    private final LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Condition condition;
    private Details details;
    private Location location;
    private String price;
}
