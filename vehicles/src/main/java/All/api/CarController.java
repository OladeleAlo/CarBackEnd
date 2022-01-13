package All.api;

import All.domain.Car.Car;
import All.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;
    private final CarResourceAssembler assembler;

    @GetMapping
    Resources<Resource<Car>> list() {
        List<Resource<Car>> resources = carService.list().stream().map(assembler::toResource)
                .collect(Collectors.toList());
        return new Resources<>(resources,
                linkTo(methodOn(CarController.class).list()).withSelfRel());
    }
    @GetMapping("/{id}")
    ResponseEntity<Object> get(@PathVariable Long id) {
        Car car = carService.findById(id);
        if (car== null){
            return ResponseEntity.notFound().build();
        }
        Resource<Car> resource = assembler.toResource(car);
        return ResponseEntity.ok(resource);
    }


    @PostMapping
    ResponseEntity<?> post( @RequestBody Car car) throws URISyntaxException {
        car = carService.save(car);
        Resource<Car> resource = assembler.toResource(new Car());
        return ResponseEntity.created(new URI(resource.getId().expand().getHref())).body(resource);
    }


    @PutMapping("/{id}")
    ResponseEntity<?> put(@PathVariable Long id, @RequestBody Car car) {
        car.setId(id);

        Resource<Car> resource = assembler.toResource(new Car());
        return ResponseEntity.ok(resource);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {
        carService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
