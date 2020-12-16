package checkout;

import java.time.LocalDate;

public abstract class Offer {
    protected LocalDate expiration;

    public Offer(LocalDate expiration) {
        this.expiration = expiration;
    }
    protected abstract void setDiscountFromStrategy(Check check);
    protected abstract boolean isApplicable(Check check);
    protected abstract int getOfferPoints(Check check);

    public void useApply(Check check) {
        if(!LocalDate.now().isAfter(expiration)){
            if(isApplicable(check)){
                check.addPoints(getOfferPoints(check));
            }
        }
    }

}