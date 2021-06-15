import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;

import static filters.CustomLogFilter.customLogFilter;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class APISteps {
    String guestCookie;
    String actualQuantity;
    Integer expectedQuantity;

    @Step("Get Guest Cookie")
    public void getGuestCookie() {
        this.guestCookie =
                given().when()
                        .get("/cart")
                        .then()
                        .statusCode(200)
                        .log().cookies().extract().cookie("Nop.customer");
    }

    @Step("Get Actual Quantity Of Items In Cart")
    public void getActualQuantityOfItemsInCart() {
        this.actualQuantity =
                given()
                        .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                        .body("product_attribute_74_5_26=81&product_attribute_74_6_27=83&product_attribute_74_3_28=86&addtocart_74.EnteredQuantity=1")
                        .cookie("Nop.customer", guestCookie)
                        .when()
                        .post("/addproducttocart/details/74/1")
                        .then()
                        .statusCode(200)
                        .log().body().extract().body().path("updatetopcartsectionhtml");
    }

    @Step("Check Quantity After Adding Another Item To Cart")
    public void checkQuantityAfterAddingAnotherItemToCart() {
        this.expectedQuantity = Integer.parseInt(actualQuantity.replaceAll("[()]", "")) + 1;
        given()
                .filter(customLogFilter().withCustomTemplates())
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .body("product_attribute_74_5_26=81&product_attribute_74_6_27=83&product_attribute_74_3_28=86&addtocart_74.EnteredQuantity=1")
                .cookie("Nop.customer", guestCookie)
                .when()
                .post("/addproducttocart/details/74/1")
                .then()
                .statusCode(200)
                .log().body()
                .body("success", is(true))
                .body("updatetopcartsectionhtml", is("(" + expectedQuantity + ")"));
    }
}
