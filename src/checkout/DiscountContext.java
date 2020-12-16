package checkout;

import java.time.LocalDate;

public class DiscountContext extends Offer {
    private Discount discountStrategy;

    public DiscountContext(Discount discountStrategy, LocalDate expiration){
        super(expiration);
        this.discountStrategy = discountStrategy;
    }

     public void setDiscountFromStrategy(Check check){
         discountStrategy.calculate(check);
     }

    @Override
    protected boolean isApplicable(Check check) {
        return true;
    }

    @Override
    protected int getOfferPoints(Check check) {
        return 0;
    }
}
