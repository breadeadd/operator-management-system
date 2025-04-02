package nz.ac.auckland.se281;

public class Operator {
  public String name;
  public String location;
  public String operatorId;

  public Operator(String name, String operatorId, String location) {
    this.name = name;
    this.location = location;
    this.operatorId = operatorId;
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
}
