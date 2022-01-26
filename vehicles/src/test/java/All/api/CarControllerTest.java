package All.api;

import All.api.AbstractTest;
import All.api.client.MapsClient;
import All.api.client.PriceClient;
import All.domain.Car.Car;
import All.domain.Car.Details;
import All.domain.Condition;
import All.domain.Location;
import All.domain.manufacuturer.Manufacturer;
import All.service.CarService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class CarControllerTest extends AbstractTest {
    @Autowired
    private JacksonTester<Car> json;


    @MockBean
    private CarService carService;

    @MockBean
    private PriceClient priceClient;

    @MockBean
    private MapsClient mapsClient;

    @Before
    public void setup() {
        super.setUp();
        Car car = getCar();
        car.setId(1L);
        given(carService.save(any())).willReturn(car);
        given(carService.findById(1L)).willReturn(car);
        given(carService.list()).willReturn(Collections.singletonList(car));
    }
    @Test
    public void createCar() throws Exception {
        Car car = getCar();
        mvc.perform(
                        post(new URI("/cars"))
                                .content(json.write(car).getJson())
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated());
    }



    @Test
    public void listCars() throws Exception {
        /**
         * TODO: Add a test to check that the `get` method works by calling
         *   the whole list of vehicles. This should utilize the car from `getCar()`
         *   below (the vehicle will be the first in the list).
         */
        mvc.perform(get("/cars"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.cars[0].details.mileage").value(32280))
                .andDo(print());
    }

    /**
     * Tests the read operation for a single car by ID.
     * @throws Exception if the read operation for a single car fails
     */
    @Test
    public void findCar() throws Exception {
        /**
         * TODO: Add a test to check that the `get` method works by calling
         *   a vehicle by ID. This should utilize the car from `getCar()` below.
         */
        mvc.perform(get("/cars/1"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    /**
     * Tests ID doesn't exist.
     * @throws Exception if returns any other code then 404
     */
    @Test
    public void testCarNotFound() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/cars/1000")
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
    }

    /**
     * Tests the deletion of a single car by ID.
     * @throws Exception if the delete operation of a vehicle fails
     */
    @Test
    public void deleteCar() throws Exception {
        /**
         * TODO: Add a test to check whether a vehicle is appropriately deleted
         *   when the `delete` method is called from the Car Controller. This
         *   should utilize the car from `getCar()` below.
         */
        mvc.perform(delete("/cars/1")).andExpect(status().isNoContent());
    }

    /**
     * Test update of a car.
     * @throws Exception when car updating fails
     */
    @Test
    public void updateCar() throws Exception {
        Car car = getCar();

        car.setCondition(Condition.USED);

        mvc.perform(put("/cars/{id}", 1)
                        .content(json.write(car).getJson())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk());
    }

    /**
     * Creates an example Car object for use in testing.
     * @return an example Car object
     */
    private Car getCar() {
        Car car = new Car();
        car.setLocation(new Location(40.730610, -73.935242));
        Details details = new Details();
        Manufacturer manufacturer = new Manufacturer(101, "Chevrolet");
        details.setManufacturer(manufacturer);
        details.setModel("Impala");
        details.setMileage(32280);
        details.setExternalColor("white");
        details.setBody("sedan");
        details.setEngine("3.6L V6");
        details.setFuelType("Gasoline");
        details.setModelYear(2018);
        details.setProductionYear(2018);
        details.setNumberOfDoors(4);
        car.setDetails(details);
        car.setCondition(Condition.USED);
        return car;
    }


}