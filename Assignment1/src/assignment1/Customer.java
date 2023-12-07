package assignment1;

public class Customer {
    private final String name;
    private int balance;
    private final Basket basket;

    public Customer(String name, int initialBalance){
        this.name = name;
        this.balance = initialBalance;
        this.basket = new Basket();
    }

    public String getName(){
        return this.name;
    }

    public int getBalance(){
        return this.balance;
    }

    public Basket getBasket(){
        return this.basket;
    }

    public int addFunds(int newFunds){
        if (newFunds < 0){
            throw new IllegalArgumentException("You cannot add negative funds");
        }
        this.balance += newFunds;
        return this.balance;
    }

    public void addToBasket(MarketProduct newProduct){
        this.basket.add(newProduct);
    }

    public boolean removeFromBasket(MarketProduct removeProduct){
        return this.basket.remove(removeProduct);
    }

    public String checkOut(){

        int totalCost = this.basket.getTotalCost();

        if (this.balance < totalCost){
            throw new IllegalStateException("Insufficient Funds in your account.");
        }

        String receipt = this.basket.toString();
        this.balance -= totalCost;
        this.basket.clear();

        return receipt;
    }

}
