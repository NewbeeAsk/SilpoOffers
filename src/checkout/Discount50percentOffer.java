package checkout;

public class Discount50percentOffer implements Discount {

    public Discount50percentOffer() {
    }

    public void calculate(Check check){
        check.setDiscount(check.getTotalCost()*0.5 + check.getDiscount());
    }
}

