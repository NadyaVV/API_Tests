package services;

import dto.StoreRequestDto;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class StoreApi {

    private static final String BASE_URL = "https://petstore.swagger.io/v2/";
    private RequestSpecification spec;
    public static final String STORE = "/store/order";
    public static final String STORE_BY_ORDERID = "/store/order/{orderId}";

    public StoreApi() {
        spec = given()
            .baseUri(BASE_URL)
            .contentType(ContentType.JSON);
    }

    public ValidatableResponse createStore(StoreRequestDto storeRequestDto) {

        return given(spec)
            .log().all()
            .body(storeRequestDto)
            .when()
            .post(STORE)
            .then()
            .log().all();
    }

    public ValidatableResponse deleteStore(Long orderId) {
        return given(spec)
            .log().all()
            .pathParam("orderId", orderId)
            .when()
            .delete(STORE_BY_ORDERID)
            .then()
            .log().all();
    }

    public ValidatableResponse getStore(Long orderId) {
        return given(spec)
            .log().all()
            .pathParam("orderId", orderId)
            .when()
            .get(STORE_BY_ORDERID)
            .then()
            .log().all();
    }
}
