import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class CartTests {
    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "http://demowebshop.tricentis.com";
    }

    private APISteps apisteps = new APISteps();

    @Test
    @Tag("API")
    public void addAnotherItemToCartByApi() {
        apisteps.getGuestCookie();
        apisteps.getActualQuantityOfItemsInCart();
        apisteps.checkQuantityAfterAddingAnotherItemToCart();
    }


}


