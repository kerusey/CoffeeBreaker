package space.fstudio.lio.coffeebreaker.Objects;

public class OrderJsonObject {
    private String coffeeType;
    private boolean milk;
    private int sugar, strength, machineId;
    private float volume;

    public OrderJsonObject(int machineId, String coffeeType, int sugar, boolean milk, float volume, int strength) {
        this.machineId = machineId;
        this.coffeeType = coffeeType;
        this.strength = strength;
        this.volume = volume;
        this.milk = milk;
        this.sugar = sugar;
    }
}


