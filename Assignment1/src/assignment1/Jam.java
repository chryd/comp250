package assignment1;

public class Jam extends MarketProduct {
    //fields
    private final int numOfJar;
    private final int pricePerJar;

    //methods
    public Jam(String name, int number, int price){
        super(name);
        this.numOfJar = number;
        this.pricePerJar = price;
    }

    public int getCost(){
        return this.pricePerJar * this.numOfJar;
    }

    public boolean equals(Object someProduct){

        if(someProduct instanceof Jam) {
            Jam someJam = (Jam) someProduct;

            boolean isSameName = this.getName().equals(someJam.getName());
            boolean isSameCost = this.getCost() == (someJam).getCost();
            boolean isSameNum = this.numOfJar == (someJam).numOfJar;

            return isSameName && isSameCost && isSameNum;
        }

        return false;
    }

}
