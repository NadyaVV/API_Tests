package usertests;

import dto.StoreRequestDto;
import dto.StoreResponseDto;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import services.StoreApi;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateAndDeleteStore {


    @Test
    @DisplayName("Check store creation by API")
    public void createStore() {
        StoreApi storeApi = new StoreApi();

        StoreRequestDto store = StoreRequestDto.builder()
            .id(450L)
            .petId(22L)
            .quantity(27L)
            .shipDate("2023-08-15T21:39:48.629Z")
            .status("placed")
            .complete(true)
            .build();

        ValidatableResponse response = storeApi.createStore(store)
            .statusCode(HttpStatus.SC_OK);

        StoreResponseDto storeResponse = response.extract().body().as(StoreResponseDto.class);
        assertAll(
            "Grouped Assertions of Store",
            () -> assertEquals("2023-08-15T21:39:48.629+0000", storeResponse.getShipDate(), "Incorrect type"),
            () -> assertEquals("placed", storeResponse.getStatus(), "Incorrect message"),
            () -> assertEquals(true, storeResponse.getComplete(), "Incorrect code"));
    }

    @Test
    @DisplayName("Check store deletion by API")
    public void deleteStore() {
        StoreApi storeApi = new StoreApi();

        StoreRequestDto store = StoreRequestDto.builder()
            .id(450L)
            .petId(22L)
            .quantity(27L)
            .shipDate("2023-08-15T21:39:48.629Z")
            .status("placed")
            .complete(true)
            .build();

        Long orderId = storeApi.createStore(store).extract().as(StoreResponseDto.class).getId();

        storeApi.deleteStore(orderId).statusCode(HttpStatus.SC_OK);
        Assertions.assertEquals("Order not found", storeApi.getStore(orderId)
            .extract().jsonPath().get("message"), "Message is incorrect");
    }
}
