package nz.ac.auckland.se281;

public class Activities {
  public String name;
  public String type;
  public String activityId;
  public String location;
  public Operator operator;

  public Activities(String name, String type, Operator operator) {
    this.name = name;
    this.type = type;
    activityId = operator.getOperatorId(); // need to add number count
    location = operator.getLocation();
  }

  public String getName() {
    return name;
  }

  public String getType() {
    return type;
  }

  public String getId() {
    return activityId;
  }

  public String getLocation() {
    return location;
  }
}
