package usertests;

import dto.UserDto;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import services.UserApi;

public class ValidateJsonSchemaOfUserEntity {

    @Test
    @DisplayName("Check user creation by API")
    public void checkJsonSchemaUser() {
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

        userApi.createUser(user)
            .statusCode(HttpStatus.SC_OK)
            .body(JsonSchemaValidator.matchesJsonSchemaInClasspath(
                "schema/CreateUser.json"));
    }
}
