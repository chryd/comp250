package assignment1;

public class Fruit extends MarketProduct {
    //fields
    private final double weight;
    private final int pricePerKg;

    //methods
    public Fruit(String name, double weight, int price){
        super(name);
        this.weight = weight;
        this.pricePerKg = price;
    }

    public int getCost(){
        return (int) (this.pricePerKg * this.weight);
    }

    public boolean equals(Object someProduct){

        if(someProduct instanceof Fruit) {
            Fruit someFruit = (Fruit) someProduct;

            boolean isSameName = this.getName().equals(someFruit.getName());
            boolean isSameCost = this.getCost() == someFruit.getCost();
            boolean isSameNum = this.weight == someFruit.weight;

            return isSameName && isSameCost && isSameNum;
        }

        return false;
    }
}
