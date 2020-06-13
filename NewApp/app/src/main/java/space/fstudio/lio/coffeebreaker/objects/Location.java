package space.fstudio.lio.coffeebreaker.objects;

import com.google.gson.annotations.SerializedName;

public class Location {
  @SerializedName("name")
  public String name;

  @SerializedName("xCoord")
  public float xCoord;

  @SerializedName("yCoord")
  public float yCoord;

  @SerializedName("clusterID")
  public int clusterID;
}

