package space.fstudio.lio.coffeebreaker.objects;

public class CoffeeTypeObject {

    private int coffeeIcon;
    private String coffeeName, coffeeDescription;

    public CoffeeTypeObject(int coffeeIcon, String coffeeName, String coffeeDescription) {
        this.coffeeIcon = coffeeIcon;
        this.coffeeName = coffeeName;
        this.coffeeDescription = coffeeDescription;
    }

    public int getIcon() {
        return coffeeIcon;
    }

    public String getCoffeeName() {
        return coffeeName;
    }

    public String getCoffeeDescription() {
        return coffeeDescription;
    }

}
