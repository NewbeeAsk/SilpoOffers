package checkout;

import java.util.ArrayList;
import java.util.List;

public class Check {

    private List<Product> products = new ArrayList<>();
    private int points = 0;
    private double discount = 0;//добавить дискаунт

    public double getDiscount() {
        return discount;
    }

    public List<Product> getProducts() {
        return products;
    }

    public double getTotalCost() {
        int totalCost = 0;
        for (Product product : this.products) {
            totalCost += product.price;
        }
        return totalCost - discount;//отнять дисканут
    }

    public void setDiscount(double discount){
        this.discount = discount;
    }// добавить setDiscount

    void addProduct(Product product) {
        products.add(product);
    }

    public double getTotalPoints() {
        return getTotalCost() + points;
    }

    void addPoints(int points) {
        this.points += points;
    }

    int getCostByCategory(Category category) {
        return products.stream()
                .filter(p -> p.category == category)
                .mapToInt(p -> p.price)
                .reduce(0, (a, b) -> a + b);
    }
}
