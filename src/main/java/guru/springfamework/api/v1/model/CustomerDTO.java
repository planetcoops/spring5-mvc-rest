package guru.springfamework.api.v1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * Created by jt on 9/24/17.
 */
@Data
public class CustomerDTO {
    private Long id;
    private String firstname;
    private String lastname;
    private String customerUrl;

    @JsonIgnore
    public Long getId() {
        return id;
    }

    public String getCustomerUrl() {
        return "/api/v1/customers/" + id;
    }
}
