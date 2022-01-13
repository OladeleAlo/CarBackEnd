package pricingService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pricingService.api.pricingController;
import pricingService.service.PricingService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(pricingController.class)
class PricingServiceApplicationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    PricingService pricingService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void getPriceByVehicleId() throws Exception {
        mockMvc.perform(get("/services/price?vehicleId=2"))
                .andExpect(status().isOk());
    }

    @Test
    public void getVehicleIdNotFound() throws Exception {
        mockMvc.perform(get("/services/price?vehicleId=2000"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getVehicleIdNotInteger() throws Exception {
        mockMvc.perform(get("/services/price?vehicleId=e"))
                .andExpect(status().isBadRequest());
    }
}