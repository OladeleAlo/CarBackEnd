package All.service;

import All.api.client.MapsClient;
import All.api.client.PriceClient;
import All.domain.Car.Car;
import All.domain.Car.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;
    private final PriceClient priceClient;
    private final MapsClient mapsClient;

    public List<Car> list() {
        return carRepository.findAll();
    }
    public Car findById(Long id) {
        Car car = new Car();
        Optional<Car> carOptional = carRepository.findById(id);
        if (carOptional.isPresent()) {
            car = carOptional.get();
            car.setPrice(priceClient.getPrice(id));
            car.setLocation(mapsClient.getAddress(car.getLocation()));
        } else {
            throw new CarNotFoundException();
        }
        return car;
    }

    public Car save(Car car) {
        if (car.getId() != null) {
            return carRepository.findById(car.getId())
                    .map(carToBeUpdated -> {
                        carToBeUpdated.setDetails(car.getDetails());
                        carToBeUpdated.setLocation(car.getLocation());
                        return carRepository.save(carToBeUpdated);
                    }).orElseThrow(CarNotFoundException::new);
        }

        return carRepository.save(car);
    }

    public void delete(Long id) throws ChangeSetPersister.NotFoundException {
        Optional<Car> carOptional = Optional.ofNullable(carRepository.getById(id));
        Car car = carOptional.orElseThrow(ChangeSetPersister.NotFoundException::new);
        carRepository.delete(car);
    }

}
