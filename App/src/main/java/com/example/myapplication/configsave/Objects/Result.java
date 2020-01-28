package space.fstudio.configsave.Objects;

public class Result {

  int id;
  String choice, sugar, milk, strenght;
  float volume, price;

  public Result(int id, String choice, String sugar, String milk, float volume, float price, String strenght){
    this.id = id;
    this.choice = choice;
    this.sugar = sugar;
    this.milk = milk;
    this.volume = volume;
    this.price = price;
    this.strenght = strenght;
  }
}
