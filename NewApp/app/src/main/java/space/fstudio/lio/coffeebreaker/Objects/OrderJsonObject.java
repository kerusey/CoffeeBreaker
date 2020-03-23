package space.fstudio.lio.coffeebreaker.Objects;

public class OrderJsonObject {

    int machineId, strength, volume, sugar;
    private String coffeeType;
    private boolean milk;

    public OrderJsonObject(int machineId, String coffeeType, int strength, int volume, boolean milk, int sugar) {
        this.machineId = machineId;
        this.coffeeType = coffeeType;
        this.strength = strength;
        this.volume = volume;
        this.milk = milk;
        this.sugar = sugar;
    }
}