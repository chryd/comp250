package assignment1;

import java.util.ArrayList;

public class Egg extends MarketProduct {
    private final int numOfEggs;
    private final int pricePerDozen;

    public Egg(String name, int number, int price){
        super(name);
        this.numOfEggs = number;
        this.pricePerDozen = price;
    }

    public int getCost(){
        return (int) (((double)this.pricePerDozen/12) * this.numOfEggs);
    }

    public boolean equals(Object someProduct){

        if(someProduct instanceof Egg) {
            Egg someEgg = (Egg) someProduct;

            boolean isSameName = this.getName().equals(someEgg.getName());
            boolean isSameCost = this.getCost() == (someEgg).getCost();
            boolean isSameNum = this.numOfEggs == (someEgg).numOfEggs;

            return isSameName && isSameCost && isSameNum;
        }

        return false;
    }

}