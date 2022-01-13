package pricingService.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pricingService.domain.Price;
import pricingService.service.PriceException;
import pricingService.service.PricingService;

@RestController
@RequestMapping("/services/price")
public class pricingController {

    @GetMapping
    public Price getPrice(@RequestParam Long vehicleId){
        try {
            return PricingService.getPrice(vehicleId);
        } catch (PriceException ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Price Not Found", ex);
        }
    }
}
