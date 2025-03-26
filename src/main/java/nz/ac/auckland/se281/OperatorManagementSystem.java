package nz.ac.auckland.se281;

import java.util.HashMap;
import nz.ac.auckland.se281.Types.Location;

public class OperatorManagementSystem {

  // Do not change the parameters of the constructor
  public OperatorManagementSystem() {}

  public void searchOperators(String keyword) {
    MessageCli.OPERATORS_FOUND.printMessage("are", "no", "s", ".");
  }

  public void createOperator(String operatorName, String location) {
    Location locationFound = Location.fromString(location); // finds inputs as coded location
    String locationAsString = locationFound.getFullName(); // finds locations full name

    // Getting Location initials
    String locationInitials = locationFound.getLocationAbbreviation();

    // Creating operator initials
    String[] words = operatorName.split(" ");
    String operatorInitals = "";

    for (String word : words) {
      operatorInitals = operatorInitals + word.charAt(0);
    }

    // get three digit number for each new operator in location
    // Create 3 digit number base
    // Verify what if operator already exists in location
    int AKLcount = 0;
    int HZLcount = 0;
    int TRGcount = 0;
    int TUOcount = 0;
    int WLGcount = 0;
    int NSNcount = 0;
    int CHCcount = 0;
    int DUDcount = 0;

    // create Hashmap
    HashMap<String, Integer> counts = new HashMap<String, Integer>();
    // Assigning keys and values
    counts.put("AKL", AKLcount);
    counts.put("HZL", HZLcount);
    counts.put("TRG", TRGcount);
    counts.put("TUO", TUOcount);
    counts.put("WLG", WLGcount);
    counts.put("NSN", NSNcount);
    counts.put("CHC", CHCcount);
    counts.put("DUD", DUDcount);

    if (counts.containsKey(locationInitials)) {
      counts.put(locationInitials, counts.get(locationInitials) + 1);
    }

    String threeDigitNumber = String.format("%03d", counts.get(locationInitials));

    // Forming operatorID
    String operatorID = operatorInitals + "-" + locationInitials + "-" + threeDigitNumber;

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
