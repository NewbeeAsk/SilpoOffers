package checkout;

import java.util.List;

public class Discount1yeOffer implements Discount {

    public Discount1yeOffer() {
    }

    public void calculate(Check check){
        check.setDiscount(check.getProducts().get(0).price - 1 + check.getDiscount());
    }

}