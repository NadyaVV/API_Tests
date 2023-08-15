package services;

import dto.UserDto;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class UserApi {

    private static final String BASE_URL = "https://petstore.swagger.io/v2/";
    private RequestSpecification spec;
    public static final String USER = "/user";

    public UserApi() {
        spec = given()
            .baseUri(BASE_URL)
            .contentType(ContentType.JSON);
    }

    public ValidatableResponse createUser(UserDto userDto) {

        return given(spec)
            .log().all()
            .body(userDto)
            .when()
            .post(USER)
            .then()
            .log().all();
    }
}
