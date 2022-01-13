package All.api.client;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Price {
    private String currency;
    private BigDecimal price;
    private Long vehicleId;
}
