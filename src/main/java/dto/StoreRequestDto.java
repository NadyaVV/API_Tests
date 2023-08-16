
package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoreRequestDto {

    private Boolean complete;
    private Long id;
    private Long petId;
    private Long quantity;
    private String shipDate;
    private String status;
}
