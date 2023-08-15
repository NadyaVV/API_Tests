package usertests;

import dto.UserDto;
import dto.UserResponseDto;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import services.UserApi;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateNewUser {

    @Test
    @DisplayName("Check user creation by API")
    public void createUser() {
        UserApi userApi = new UserApi();

        UserDto user = UserDto.builder()
            .email("email")
            .id(450L)
            .firstName("name")
            .lastName("lastName")
            .password("password")
            .username("login")
            .userStatus(600l)
            .build();

        ValidatableResponse response = userApi.createUser(user)
            .statusCode(HttpStatus.SC_OK);

        UserResponseDto userResponse = response.extract().body().as(UserResponseDto.class);
        assertAll(
            "Grouped Assertions of User",
            () -> assertEquals("unknown", userResponse.getType(), "Incorrect type"),
            () -> assertEquals("450", userResponse.getMessage(), "Incorrect message"),
            () -> assertEquals(200, userResponse.getCode(), "Incorrect code"));
    }
}
