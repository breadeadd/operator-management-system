package nz.ac.auckland.se281;

public class Activities {
  String name;
  String type;
  String activityId;
  String location;
  Operator operator;
  String operatorLocation;
  int reviewCount;

  public Activities(String name, String type, String activityId, Operator operator) {
    this.name = name;
    this.type = type;
    this.activityId = activityId;
    this.operator = operator;
    location = operator.getName();
    operatorLocation = operator.getLocation();
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

  public String getOperatorLocation() {
    return operatorLocation;
  }

  public int incrementCount(){
    reviewCount++;
    return reviewCount;
  }

  public int getCount() {
    return reviewCount;
  }
}
