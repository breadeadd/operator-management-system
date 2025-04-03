package nz.ac.auckland.se281;

public class Activities {
  public String name;
  public String type;
  public String activityId;
  public String location;
  public Operator operator;

  public Activities(String name, String type, String activityId, Operator operator) {
    this.name = name;
    this.type = type;
    this.activityId = activityId;
    this.operator = operator;
    location = operator.getName();
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

  public String getActivityLocation() {
    return location;
  }

  public Operator getOperator() {
    return operator;
  }
}
