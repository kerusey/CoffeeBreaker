package space.fstudio.lio.coffeebreaker.Objects;

public class OrderJsonObject {
    private String coffeeType;
    private int sugar, strength, machineId, volume, milk;

    public OrderJsonObject(int machineId, String coffeeType, int sugar, int milk, int volume, int strength) {
        this.machineId = machineId;
        this.coffeeType = coffeeType;
        this.strength = strength;
        this.volume = volume;
        this.milk = milk;
        this.sugar = sugar;
    }
}


