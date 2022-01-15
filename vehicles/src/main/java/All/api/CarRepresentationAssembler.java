package All.api;

import All.domain.Car.Car;
import All.domain.Car.CarRepresentation;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CarRepresentationAssembler implements RepresentationModelAssembler<Car,CarRepresentation> {
    @Override
    public CarRepresentation toModel(Car entity) {
        CarRepresentation carRepresentation = CarRepresentation.builder()
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())
                .modifiedAt(entity.getModifiedAt())
                .condition(entity.getCondition())
                .details(entity.getDetails())
                .location(entity.getLocation())
                .price(entity.getPrice())
                .build();
        carRepresentation.add(linkTo(methodOn(CarController.class).get(carRepresentation.getId())).withSelfRel());
        carRepresentation.add(linkTo(methodOn(CarController.class).list()).withRel("cars"));
        return carRepresentation;
    }

    @Override
    public CollectionModel<CarRepresentation> toCollectionModel(Iterable<? extends Car> entities) {
        CollectionModel<CarRepresentation> carRepresentations = RepresentationModelAssembler.super.toCollectionModel(entities);
        carRepresentations.add(linkTo(methodOn(CarController.class).list()).withSelfRel());
        return carRepresentations;
    }
}
