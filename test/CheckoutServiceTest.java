import checkout.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CheckoutServiceTest {

    private Product milk_7;
    private CheckoutService checkoutService;
    private Product bred_3;

    @BeforeEach
    void setUp() {
        checkoutService = new CheckoutService();
        checkoutService.openCheck();

        milk_7 = new Product(7, "Milk", Category.MILK);
        bred_3 = new Product(3, "Bred");
    }

    @Test
    void closeCheck__withOneProduct() {
        checkoutService.addProduct(milk_7);
        Check check = checkoutService.closeCheck();

        assertThat(check.getTotalCost(), is(7.0));
    }

    @Test
    void closeCheck__withTwoProducts() {
        checkoutService.addProduct(milk_7);
        checkoutService.addProduct(bred_3);
        Check check = checkoutService.closeCheck();

        assertThat(check.getTotalCost(), is(10.0));
    }

    @Test
    void addProduct__whenCheckIsClosed__opensNewCheck() {
        checkoutService.addProduct(milk_7);
        Check milkCheck = checkoutService.closeCheck();
        assertThat(milkCheck.getTotalCost(), is(7.0));

        checkoutService.addProduct(bred_3);
        Check bredCheck = checkoutService.closeCheck();
        assertThat(bredCheck.getTotalCost(), is(3.0));
    }

    @Test
    void closeCheck__calcTotalPoints() {
        checkoutService.addProduct(milk_7);
        checkoutService.addProduct(bred_3);
        Check check = checkoutService.closeCheck();

        assertThat(check.getTotalPoints(), is(10.0));
    }

    @Test
    void useOffer__addOfferPoints() {
        checkoutService.addProduct(milk_7);
        checkoutService.addProduct(bred_3);

        checkoutService.useOffer(new AnyGoodsOffer(6, 2, LocalDate.now()));
        Check check = checkoutService.closeCheck();

        assertThat(check.getTotalPoints(), is(12.0));
    }

    @Test
    void useOffer__whenCostLessThanRequired__doNothing() {
        checkoutService.addProduct(bred_3);

        checkoutService.useOffer(new AnyGoodsOffer(6, 2, LocalDate.now()));
        Check check = checkoutService.closeCheck();

        assertThat(check.getTotalPoints(), is(3.0));
    }

    @Test
    void useOffer__factorByCategory() {
        checkoutService.addProduct(milk_7);
        checkoutService.addProduct(milk_7);
        checkoutService.addProduct(bred_3);

        checkoutService.useOffer(new FactorByCategoryOffer(Category.MILK, 2, LocalDate.now()));
        Check check = checkoutService.closeCheck();

        assertThat(check.getTotalPoints(), is(31.0));
    }

    @Test
    void useOffer__whileShopping() {
        checkoutService.addProduct(milk_7);
        checkoutService.addProduct(bred_3);

        checkoutService.useOffer(new FactorByCategoryOffer(Category.MILK, 2, LocalDate.now()));
        checkoutService.addProduct(milk_7);
        checkoutService.addProduct(milk_7);
        Check check = checkoutService.closeCheck();

        assertThat(check.getTotalPoints(), is(45.0));
    }

    @Test
    void useOffer__whileUsingSeveralOffers() {
        checkoutService.addProduct(milk_7);

        checkoutService.useOffer(new AnyGoodsOffer(6, 2, LocalDate.now()));

        checkoutService.addProduct(bred_3);

        checkoutService.useOffer(new FactorByCategoryOffer(Category.MILK, 2, LocalDate.now()));

        checkoutService.addProduct(milk_7);
        checkoutService.addProduct(milk_7);
        Check check = checkoutService.closeCheck();

        assertThat(check.getTotalPoints(), is(47.0));
    }

    @Test
    void useOffer__companyOrProductOffer() {
        checkoutService.addProduct(milk_7);
        checkoutService.addProduct(bred_3);
        checkoutService.useOffer(new PointsForExactProductOffer(LocalDate.now(), Category.MILK));
        Check check = checkoutService.closeCheck();

        assertThat(check.getTotalPoints(), is(17.0));
    }

    @Test
    void useOffer__Discount50PercentOffer() {
        checkoutService.addProduct(milk_7);
        checkoutService.addProduct(bred_3);
        checkoutService.useOffer(new DiscountContext(new Discount50percentOffer(), LocalDate.now()));
        Check check = checkoutService.closeCheck();

        assertThat(check.getTotalCost(), is(5.0));
    }

    @Test
    void useOffer__Discount1yeProductOffer() {
        checkoutService.addProduct(milk_7);
        checkoutService.addProduct(bred_3);
        checkoutService.useOffer(new DiscountContext(new Discount1yeOffer(), LocalDate.now()));
        Check check = checkoutService.closeCheck();

        assertThat(check.getTotalCost(), is(4.0));
    }
}
