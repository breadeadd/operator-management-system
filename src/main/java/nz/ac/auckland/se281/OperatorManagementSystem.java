package nz.ac.auckland.se281;

public class OperatorManagementSystem {

  // Do not change the parameters of the constructor
  public OperatorManagementSystem() {}

  public void searchOperators(String keyword) {
    MessageCli.OPERATORS_FOUND.printMessage("are", "no", "s", ".");
  }

  public void createOperator(String operatorName, String location) {
    nz.ac.auckland.se281.Types.Location locationFound =
        nz.ac.auckland.se281.Types.Location.fromString(location);
    String locationAsString = locationFound.getFullName();

    // Creating operator initials
    String[] words = operatorName.split(" ");
    String operatorInitals = "";

    for (String word : words) {
      operatorInitals = operatorInitals + word.charAt(0);
    }

    // get three digit number for each new operator in location
    // Create 3 digit number base
    // Verify what if operator already exists in location
    String AKLcount = "000";
    String HZLcount = "000";
    String TRGcount = "000";
    String TUOcount = "000";
    String WLGcount = "000";
    String NSNcount = "000";
    String CHCcount = "000";
    String DUDcount = "000";

    // formatting count
    int count = 0;
    count++;

    String threeDigitNumber = String.format("%03d", count);

    // Forming operatorID
    String operatorID = operatorInitals + "-" + location + "-" + threeDigitNumber;

    // Print Info
    MessageCli.OPERATOR_CREATED.printMessage(operatorName, operatorID, locationAsString);
  }

  public void viewActivities(String operatorId) {
    // TODO implement
  }

  public void createActivity(String activityName, String activityType, String operatorId) {
    // TODO implement
  }

  public void searchActivities(String keyword) {
    // TODO implement
  }

  public void addPublicReview(String activityId, String[] options) {
    // TODO implement
  }

  public void addPrivateReview(String activityId, String[] options) {
    // TODO implement
  }

  public void addExpertReview(String activityId, String[] options) {
    // TODO implement
  }

  public void displayReviews(String activityId) {
    // TODO implement
  }

  public void endorseReview(String reviewId) {
    // TODO implement
  }

  public void resolveReview(String reviewId, String response) {
    // TODO implement
  }

  public void uploadReviewImage(String reviewId, String imageName) {
    // TODO implement
  }

  public void displayTopActivities() {
    // TODO implement
  }
}
