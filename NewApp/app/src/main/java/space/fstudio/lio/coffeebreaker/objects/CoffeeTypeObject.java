package space.fstudio.lio.coffeebreaker.objects;

public class CoffeeTypeObject {

    private final int coffeeIcon;
    private final String coffeeName;
    private final String coffeeDescription;

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
