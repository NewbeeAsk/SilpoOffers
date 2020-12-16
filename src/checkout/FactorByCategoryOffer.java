package checkout;

import java.time.LocalDate;

public class FactorByCategoryOffer extends Offer {
    final Category category;
    final int factor;

    public FactorByCategoryOffer(Category category, int factor, LocalDate expiration) {
        super(expiration);
        this.category = category;
        this.factor = factor;
    }

    @Override
    protected void setDiscountFromStrategy(Check check) {
        check.setDiscount(check.getDiscount());}

    @Override
    public boolean isApplicable(Check check) {
        int points = check.getCostByCategory(this.category);
        return points > 0;
    }

    @Override
    public int getOfferPoints(Check check) {
        int points = check.getCostByCategory(this.category);
        return points * (this.factor - 1);
    }

}
