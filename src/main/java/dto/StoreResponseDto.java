
package dto;

import lombok.Data;

@Data
public class StoreResponseDto {

    private Boolean complete;
    private Long id;
    private Long petId;
    private Long quantity;
    private String shipDate;
    private String status;
}
