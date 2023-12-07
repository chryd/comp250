import assignment1.*;

import java.util.ArrayList;
import java.util.Arrays;

public class Test {

    public static void main(String[] args){

       //some products
       Egg e1 = new Egg("Large White Eggs", 12, 299);
       Egg e2 = new Egg("Medium Brown Eggs", 30, 178);
       Fruit f1 = new Fruit("MacIntosh Apple", 2, 198);
       SeasonalFruit sf1 = new SeasonalFruit("Clementine", 0.800, 550);
       Jam j1 = new Jam("Strawberry Jam", 1, 599);
       Jam j2 = new Jam("Apricot Jam", 2, 299);

       //customer and balance
       Customer c = new Customer("Bob", 2000);
       int funds = 500;

       System.out.println("Name: " + c.getName() +
               "\tBalance: " + c.getBalance() + "￠" +
               "\nYou added " + funds + "￠ to your balance. New Balance: " + c.addFunds(funds) + "￠");

       //shopping
       c.addToBasket(e1);
       c.addToBasket(e2);
       System.out.println(e1.getName() + " has been removed from your basket: " + c.removeFromBasket(e1));
       System.out.println(f1.getName() + " has been removed from your basket: " + c.removeFromBasket(f1)); //false
       c.addToBasket(j1);
       c.addToBasket(j2);
       System.out.println(j1.getName() + " has been removed from your basket: " + c.removeFromBasket(j1));
       c.addToBasket(f1);
       c.addToBasket(sf1);

       MarketProduct[] copy = c.getBasket().getProducts();
       System.out.println("Your basket has " + c.getBasket().getNumOfProducts() + " items." + //4 items
               "\nThese are the references of your products: " + Arrays.toString(copy) + ". ");

       //let's mess w the copy
       copy[0] = e1; //the first item should be Medium Brown Eggs on the receipt
       System.out.println("Copy[0]: " + copy[0].getName()); //even if copy[0] is assigned to LWE

       //receipt
       System.out.println("\nReceipt:\n" + c.checkOut() +
               "\nBalance: " + c.getBalance() +
               "\nProducts in cart: " + c.getBasket().getNumOfProducts());

       //Exceptions
       //System.out.println("\nYou added -2.00$ to your balance. New Balance: " + c.addFunds(-200));

       //c.addToBasket(j2);
       //c.addToBasket(f1);
       //System.out.println("\nReceipt:\n" + c.checkOut());

       ArrayList<Egg> n = new ArrayList<Egg>();
       n.add(new Egg("a", 2, 88));
       n.add(new Egg("b", 6, 199));

       System.out.println(n.size());
    }
}
