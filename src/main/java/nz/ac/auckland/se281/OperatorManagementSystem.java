package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.HashMap;
import nz.ac.auckland.se281.Types.Location;

public class OperatorManagementSystem {
  public HashMap<String, Integer> counts;
  public ArrayList<String> savedOperators;

  // Do not change the parameters of the constructor
  public OperatorManagementSystem() {
    counts = new HashMap<>();
    savedOperators = new ArrayList<>();
    // initializing hashmap
    class Initialization {
      private boolean isInitialized = false;

      public void InitializeCounts() {
        // Assigning keys and values
        if (!isInitialized) {
          counts.put("AKL", 0);
          counts.put("HLZ", 0);
          counts.put("TRG", 0);
          counts.put("TUO", 0);
          counts.put("WLG", 0);
          counts.put("NSN", 0);
          counts.put("CHC", 0);
          counts.put("DUD", 0);
          isInitialized = true;
        }
      }
    }

    new Initialization().InitializeCounts();
  }

  public void searchOperators(String keyword) {
    int operatorCount = 0;
    ArrayList<String> foundOperators;

    // initialise found lists for each search
    foundOperators = new ArrayList<>();

    // ensuring case insensitive
    String checkKeyword = keyword.toLowerCase();
    checkKeyword = checkKeyword.trim();

    // cycling through all saved operators for matches
    for (String operator : savedOperators) {
      String ignoreCaseOperator = operator.toLowerCase();
      if (ignoreCaseOperator.contains(checkKeyword)) {
        operatorCount++;
        foundOperators.add(operator);
      }
    }

    // if keyword is not found anywhere
    if (foundOperators.isEmpty()) {
      MessageCli.OPERATOR_NOT_FOUND.printMessage(keyword);
    }

    // checking plural
    String pluralOperator;
    String joiningWord;
    if (operatorCount > 1) {
      pluralOperator = "s";
      joiningWord = "are";
    } else {
      pluralOperator = "";
      joiningWord = "is";
    }

    if (operatorCount > 0) {
      MessageCli.OPERATORS_FOUND.printMessage(
          joiningWord, Integer.toString(operatorCount), pluralOperator, ":");
      // print for each found operator
      for (String foundOp : foundOperators) {
        System.out.println(foundOp);
      }
    } else {
      MessageCli.OPERATORS_FOUND.printMessage("are", "no", "s", ".");
    }
  }

  public void createOperator(String operatorName, String location) {
    Location locationFound = Location.fromString(location); // finds inputs as coded location

    // if Location is not Found - exit
    if (locationFound == null) {
      MessageCli.OPERATOR_NOT_CREATED_INVALID_LOCATION.printMessage(location);
      return;
    }

    String locationAsString = locationFound.getFullName(); // finds locations full name

    // Determining if operator name is valid
    String checkOperator = operatorName.trim();
    if (checkOperator.length() < 3) {
      MessageCli.OPERATOR_NOT_CREATED_INVALID_OPERATOR_NAME.printMessage(operatorName);
    }

    // Getting Location initials
    String locationInitials = locationFound.getLocationAbbreviation();

    // Creating operator initials
    String[] words = operatorName.split(" ");
    String operatorInitals = "";

    for (String word : words) {
      operatorInitals = operatorInitals + word.charAt(0);
    }

    // setting three digit number
    if (counts.containsKey(locationInitials)) {
      counts.put(locationInitials, counts.get(locationInitials) + 1);
    }

    String threeDigitNumber = String.format("%03d", counts.get(locationInitials));

    // Forming operatorID
    String operatorID = operatorInitals + "-" + locationInitials + "-" + threeDigitNumber;

    // Save operators - save operator ID to array
    String operatorSaved =
        MessageCli.OPERATOR_ENTRY.getMessage(operatorName, operatorID, locationAsString);

    boolean operatorExists = false;
    // Checking if operator exists
    for (String opSaved : savedOperators) {
      if (opSaved.contains(operatorInitals) && opSaved.contains(locationInitials)) {
        operatorExists = true;
        break;
      }
    }

    // Deciding print
    if (operatorExists) {
      MessageCli.OPERATOR_NOT_CREATED_ALREADY_EXISTS_SAME_LOCATION.printMessage(
          operatorName, locationAsString);
    } else {
      savedOperators.add(operatorSaved);
      MessageCli.OPERATOR_CREATED.printMessage(operatorName, operatorID, locationAsString);
    }
  }

  //////////////////////////////////////////////////
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
