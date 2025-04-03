package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.HashMap;
import nz.ac.auckland.se281.Types.Location;

public class OperatorManagementSystem {
  HashMap<String, Integer> counts;
  ArrayList<Operator> savedOperators;
  ArrayList<Activities> savedActivities;

  // Do not change the parameters of the constructor
  public OperatorManagementSystem() {
    counts = new HashMap<>();
    savedOperators = new ArrayList<>();
    savedActivities = new ArrayList<>();

    // initializing hashmap
    class Initialization {
      private boolean isInitialized = false;

      public void initializeCounts() {
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

    new Initialization().initializeCounts();
  }

  public void searchOperators(String keyword) {
    int operatorCount = 0;
    ArrayList<Operator> foundOperators;

    // initialise found lists for each search
    foundOperators = new ArrayList<>();

    // ensuring case insensitive
    keyword = keyword.trim();
    String checkKeyword = keyword.toLowerCase();

    // ensure 'located' or 'is' do not count as operatorIDs
    if (checkKeyword.equals("located") || checkKeyword.equals("is") || checkKeyword.equals("-")) {
      MessageCli.OPERATOR_NOT_FOUND.printMessage(keyword);
      return;
    }

    for (Operator savedOperator : savedOperators) {
      if (savedOperator.name.equals(keyword) || savedOperator.location.contains(keyword)) {
        operatorCount++;
        foundOperators.add(savedOperator);
      }
    }

    // print all if inputs is *
    if (checkKeyword.equals("*")) {
      for (Operator allOp : savedOperators) {
        operatorCount++;
        foundOperators.add(allOp);
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
      for (Operator foundOp : foundOperators) {
        MessageCli.OPERATOR_ENTRY.printMessage(
            foundOp.getName(), foundOp.getOperatorId(), foundOp.getLocation());
      }
    } else {
      MessageCli.OPERATORS_FOUND.printMessage("are", "no", "s", ".");
    }
  }

  //////////

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
      return;
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
    String operatorIdentity = operatorInitals + "-" + locationInitials + "-" + threeDigitNumber;

    // Setting initial conditions to check
    boolean operatorExists = false;

    // Checking if operator exists
    for (Operator opSaved : savedOperators) {
      if (opSaved.getName().equals(operatorName)
          && opSaved.getLocation().equals(locationAsString)) {
        operatorExists = true;
        break;
      }
    }

    // Deciding print & save details
    if (operatorExists) {
      MessageCli.OPERATOR_NOT_CREATED_ALREADY_EXISTS_SAME_LOCATION.printMessage(
          operatorName, locationAsString);
    } else {
      // Save operators - save operator ID to array
      Operator operatorSaved = new Operator(operatorName, operatorIdentity, locationAsString);
      savedOperators.add(operatorSaved);

      MessageCli.OPERATOR_CREATED.printMessage(operatorName, operatorIdentity, locationAsString);
    }
  }

  //////////////////////////////////////////////////
  public void viewActivities(String operatorId) {
    // initalise count
    boolean IDExists = false;
    Operator chosenOperator = null;
    int activityCount = 0;

    // found array list
    ArrayList<Activities> foundActivities;

    // initialise found lists for each search
    foundActivities = new ArrayList<>();

    // check if operatorId exists
    for (Operator identity : savedOperators) {
      if (identity.getOperatorId().equals(operatorId)) {
        chosenOperator = identity;
        IDExists = true;
        break;
      }
    }

    // if operatorId doesn't exist
    if (!IDExists) {
      MessageCli.OPERATOR_NOT_FOUND.printMessage(operatorId);
      return;
    }

    // checking if operators match of all activities
    for (Activities activity : savedActivities) {
      if (activity.getOperator().equals(chosenOperator)) {
        activityCount++;
        foundActivities.add(activity);
      }
    }

    // checking plural
    String pluralOperator;
    String joiningWord;
    if (activityCount > 1) {
      pluralOperator = "ies";
      joiningWord = "are";
    } else {
      pluralOperator = "y";
      joiningWord = "is";
    }

    // if now activities don't exist
    if (savedActivities.isEmpty()) {
      MessageCli.ACTIVITIES_FOUND.printMessage("are", "no", "ies", ".");
      return;
    }

    MessageCli.ACTIVITIES_FOUND.printMessage(
        joiningWord, String.valueOf(activityCount), pluralOperator, ":");

    for (Activities activity : foundActivities) {
      MessageCli.ACTIVITY_ENTRY.printMessage(
          activity.getName(), activity.getId(), activity.getType(), activity.getActivityLocation());
    }
  }

  ///////
  public void createActivity(String activityName, String activityType, String operatorId) {
    // intialise conditions
    boolean IDExists = false;
    Operator chosenOperator = null;

    // checking if activityName is valid
    if (activityName.length() < 3) {
      MessageCli.ACTIVITY_NOT_CREATED_INVALID_ACTIVITY_NAME.printMessage(activityName);
      return;
    }

    // check if operatorId exists
    for (Operator operator : savedOperators) {
      if (operator.getOperatorId().equals(operatorId)) {
        IDExists = true;
        chosenOperator = operator;
        break;
      }
    }

    // if operatorId doesn't exist
    if (!IDExists) {
      MessageCli.ACTIVITY_NOT_CREATED_INVALID_OPERATOR_ID.printMessage(operatorId);
      return;
    }

    // creating activity count
    chosenOperator.incrementCount();
    int activityCount = chosenOperator.getActivityCount();
    String activityId = String.format("%03d", activityCount);
    activityId = chosenOperator.getOperatorId().concat("-").concat(activityId);

    // adding new activity to list
    Activities newActivity = new Activities(activityName, activityType, activityId, chosenOperator);
    savedActivities.add(newActivity);

    // Messages
    MessageCli.ACTIVITY_CREATED.printMessage(
        activityName, activityId, activityType, chosenOperator.getName());
  }

  public void searchActivities(String keyword) {
    int activityCount = 0;
    // found array list
    ArrayList<Activities> foundActivities;

    // initialise found lists for each search
    foundActivities = new ArrayList<>();

    if (keyword.equals("*")) {
      for (Activities activity : savedActivities) {
        foundActivities.add(activity);
        activityCount++;
      }
    }

    if (foundActivities.isEmpty()) {
      MessageCli.ACTIVITIES_FOUND.printMessage("are", "no", "ies", ".");
      return;
    } else {
      // checking plural
      String pluralOperator;
      String joiningWord;
      if (activityCount > 1) {
        pluralOperator = "ies";
        joiningWord = "are";
      } else {
        pluralOperator = "y";
        joiningWord = "is";
      }

      MessageCli.ACTIVITIES_FOUND.printMessage(
          joiningWord, activityCount + "", pluralOperator, ":");
    }

    for (Activities activity : foundActivities) {
      MessageCli.ACTIVITY_ENTRY.printMessage(
          activity.getName(), activity.getId(), activity.getType(), activity.getActivityLocation());
    }
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
