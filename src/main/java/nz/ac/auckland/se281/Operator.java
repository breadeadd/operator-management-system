package nz.ac.auckland.se281;

public class Operator {
  private String name;
  private String location;
  private String operatorId;
  private int activityCount;

  public Operator(String name, String operatorId, String location) {
    this.name = name;
    this.location = location;
    this.operatorId = operatorId;
    this.activityCount = 0;
  }

  public String getName() {
    return name;
  }

  public String getLocation() {
    return location;
  }

  public String getOperatorId() {
    return operatorId;
  }

  public int getActivityCount() {
    return activityCount;
  }

  public int incrementCount() {
    activityCount++;
    return activityCount;
  }
}
