package All.domain;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.Transient;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder


public class Location {
    @NotNull
    private Double lat;

    @NotNull
    private Double lon;

    @Transient
    private String address;

    @Transient
    private String city;

    @Transient
    private String state;

    @Transient
    private String zip;

    public Location(Double lat, Double lon) {
        this.lat = lat;
        this.lon = lon;
    }
}
