package assignment1;

public class Basket {

    private MarketProduct[] inCart;
    private static int numProducts = 0;

    public Basket() {
        this.inCart =  new MarketProduct[numProducts];
    }

    public MarketProduct[] getProducts(){
        MarketProduct[] productCopy = new MarketProduct[this.inCart.length];
        for (int i = 0; i < this.inCart.length; i++){
            productCopy[i] = this.inCart[i];
        }
        return productCopy;
    }

    public void add(MarketProduct newProduct){

        numProducts++;
        MarketProduct[] newCart = new MarketProduct[numProducts];

        for (int i = 0; i < numProducts - 1; i++) {
            newCart[i] = this.inCart[i];
        }

        newCart[numProducts - 1] = newProduct;
        this.inCart = newCart;
    }

    public boolean remove(MarketProduct removeProduct){

        for (int i = 0; i < numProducts; i++) {

            if (this.inCart[i].equals(removeProduct)) {

                numProducts--;
                MarketProduct[] newCart = new MarketProduct[numProducts];

                for (int j = 0; j < numProducts; j++){
                    if (j < i)
                        newCart[j] = this.inCart[j];
                    else
                        newCart[j] = this.inCart[j+1];
                }
                this.inCart = newCart;
                return true;
            }
        }

        return false;
    }

    public void clear(){
        numProducts = 0;
        this.inCart = new MarketProduct[numProducts];
    }

    public int getNumOfProducts(){
        return numProducts;
    }

    public int getSubTotal(){
        int subTotal = 0;
        for (int i = 0; i < numProducts; i++){
            subTotal += this.inCart[i].getCost();
        }
        return subTotal;
    }

    public int getTotalTax(){
        double tax = 0;
        for (int i = 0; i < numProducts; i++){
            if (this.inCart[i] instanceof Jam)
                tax += this.inCart[i].getCost() * 0.15;
        }
        return (int) tax;
    }

    public int getTotalCost(){
        return this.getSubTotal() + this.getTotalTax();
    }

    private static String centsToDollars(int priceInCents){

        if (priceInCents <= 0)
            return "-";

        String dollars = Double.toString(priceInCents / 100.00 );

        if (dollars.charAt(dollars.length() - 3) != '.')
            if (dollars.charAt(dollars.length() - 2) != '.')
                dollars += "00";
            else
                dollars += "0";

        return dollars;
    }

    public String toString(){
        String receipt = "";

        for (int i = 0; i < numProducts; i++){
            receipt += this.inCart[i].getName() + "\t" + centsToDollars(this.inCart[i].getCost()) + "\n";
        }

        receipt += "\nSubtotal\t" + centsToDollars(this.getSubTotal()) +
                "\nTotal Tax\t" +  centsToDollars(this.getTotalTax()) +
                "\n\nTotal Cost\t" +  centsToDollars(this.getTotalCost());

        return receipt;
    }
}
