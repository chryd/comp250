package assignment1;

public abstract class MarketProduct {
    private final String productName;

    public MarketProduct(String name){
        this.productName = name;
    }

    public String getName(){
        return this.productName;
    }

    public abstract int getCost();

    public abstract boolean equals(Object someProduct);

}
