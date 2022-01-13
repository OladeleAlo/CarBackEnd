package All.api.client;

import lombok.*;

@Data

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {
    private String address;
    private String city;
    private String state;
    private String zip;


}
