package assignment1;

public class SeasonalFruit extends Fruit {

    public SeasonalFruit(String name, double weight, int price){
        super(name, weight, price);
    }

    public int getCost(){
        return (int) (super.getCost() * 0.85);
    }
}
