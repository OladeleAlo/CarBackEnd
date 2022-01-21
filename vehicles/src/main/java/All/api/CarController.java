package All.api;
import All.domain.Car.Car;
import All.domain.Car.CarRepresentation;
import All.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;
    private final CarRepresentationAssembler assembler;


    @GetMapping
    ResponseEntity<CollectionModel<CarRepresentation>> list() {
        return ResponseEntity.ok(assembler.toCollectionModel(carService.list()));
    }
    @GetMapping("/{id}")
    ResponseEntity<Object> get(@PathVariable Long id) {
        Car car = carService.findById(id);
        if (car== null){
            return ResponseEntity.notFound().build();
        }
        CarRepresentation carRepresentation = assembler.toModel(car);
        return ResponseEntity.ok(carRepresentation);
    }
    @PostMapping
    ResponseEntity<?> post(@RequestBody Car car) {
        car = carService.save(car);
        CarRepresentation carRepresentation = assembler.toModel(new Car());
        Link link = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(CarController.class)
                        .post(car))
                .withSelfRel();
        return ResponseEntity.created(link.toUri()).body(carRepresentation);
    }
    @PutMapping("/{id}")
    ResponseEntity<?> put(@PathVariable Long id, @RequestBody Car car) {
        car.setId(id);
        CarRepresentation carRepresentation = assembler.toModel(new Car());
        return ResponseEntity.ok(carRepresentation);
    }
    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {
        carService.delete(id);
        return ResponseEntity.noContent().build();
    }



}

