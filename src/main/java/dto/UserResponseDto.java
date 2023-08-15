package dto;

import lombok.Data;

@Data
public class UserResponseDto {

    private Long code;
    private String message;
    private String type;
}
