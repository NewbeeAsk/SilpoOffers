package checkout;

import java.util.ArrayList;
import java.util.List;

public class CheckoutService {

    private Check check;
    private List<Offer> usedOffer = new ArrayList<>();

    public void openCheck() {
        check = new Check();
    }

    public void addProduct(Product product) {
        if (check == null) {
            openCheck();
        }
        check.addProduct(product);
    }

    public Check closeCheck() {
        if(usedOffer.size() > 0){
            for(Offer o : usedOffer){
                o.useApply(check);
                o.setDiscountFromStrategy(check);
            }
        }
        Check closedCheck = check;
        check = null;
        return closedCheck;
    }

    public void useOffer(Offer offer) {
        usedOffer.add(offer);
    }
}
