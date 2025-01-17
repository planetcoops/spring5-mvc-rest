package guru.springfamework.api.v1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * Created by jt on 9/24/17.
 */
@Data
public class CategoryDTO {
    private Long id;
    private String name;

    @JsonIgnore
    public Long getId() {
        return id;
    }
}
