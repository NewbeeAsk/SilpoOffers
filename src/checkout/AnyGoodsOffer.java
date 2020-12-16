package checkout;

import java.time.LocalDate;

public class AnyGoodsOffer extends Offer {
    public final int totalCost;
    public final int points;

    public AnyGoodsOffer(int totalCost, int points, LocalDate expiration) {
        super(expiration);
        this.totalCost = totalCost;
        this.points = points;
    }

    @Override
    protected void setDiscountFromStrategy(Check check) {
        check.setDiscount(check.getDiscount());}

    @Override
    protected boolean isApplicable(Check check) {
        return this.totalCost <= check.getTotalCost();
    }

    @Override
    public int getOfferPoints(Check check) {
        return this.points;
    }

}
