class Data {

    private boolean milk;
    private String date, time, type;
    private int id, sugar, volume, strength;

    public Data(String date, String time, int id, String type, int sugar, boolean milk, int strength, int volume) {
        this.date = date;
        this.time = time;
        this.type = type;
        this.id = id;
        this.sugar = sugar;
        this.milk = milk;
        this.strength = strength;
        this.volume = volume;

    }

    public int getVolume() {
        return volume;
    }

    public boolean getMilk() {
        return milk;
    }

    public String getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public int getSugar() {
        return sugar;
    }

    public int getStrength() {
        return strength;
    }

}

