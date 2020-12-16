package checkout;

import java.time.LocalDate;

public class PointsForExactProductOffer extends Offer {
    public Category discountCategory;

    public PointsForExactProductOffer(LocalDate expiration, Category discountCategory) {
        super(expiration);
        this.discountCategory = discountCategory;
    }

    @Override
    protected void setDiscountFromStrategy(Check check) {
        check.setDiscount(check.getDiscount());
    }

    @Override
    public boolean isApplicable(Check check){
        for(Product productInCheck : check.getProducts()){
            if(productInCheck.category == discountCategory){
                return true;
            }
        }
        return false;
    }

    @Override
    public int getOfferPoints(Check check) {
        return check.getCostByCategory(this.discountCategory);
    }

}
