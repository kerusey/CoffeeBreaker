package space.fstudio.lio.coffeebreaker.objects;

public class Location {

  private String name;
  private float x, y;

  public Location(String name, float x, float y){
    this.name = name;
    this.x = x;
    this.y = y;
  }

  public String getName() {
    return name;
  }

  public float getY() {
    return y;
  }

  public float getX() {
    return x;
  }
}
