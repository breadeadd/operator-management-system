package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.HashMap;
import nz.ac.auckland.se281.Types.Location;

public class OperatorManagementSystem {
  HashMap<String, Integer> counts;
  ArrayList<String> savedOperators;
  ArrayList<String> savedOpDetails;
  ArrayList<String> savedOperatorIDs;
  ArrayList<Integer> activityCounts;

  // Do not change the parameters of the constructor
  public OperatorManagementSystem() {
    counts = new HashMap<>();
    savedOperators = new ArrayList<>();
    savedOpDetails = new ArrayList<>();
    savedOperatorIDs = new ArrayList<>();
    activityCounts = new ArrayList<>();

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
    ArrayList<String> foundOperators;

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

    // cycling through all saved operators for matches
    for (int i = 0; i < savedOpDetails.size(); i++) {
      String ignoreCaseOperator = savedOpDetails.get(i);
      ignoreCaseOperator = ignoreCaseOperator.toLowerCase();
      if (ignoreCaseOperator.contains(checkKeyword)) {
        operatorCount++;
        String operatorSubmit = savedOperators.get(i);
        foundOperators.add(operatorSubmit);
      }
    }

    // print all if inputs is *
    if (checkKeyword.equals("*")) {
      for (String allOp : savedOperators) {
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
      for (String foundOp : foundOperators) {
        System.out.println(foundOp);
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

    // Save operators - save operator ID to array
    String operatorSaved =
        MessageCli.OPERATOR_ENTRY.getMessage(operatorName, operatorIdentity, locationAsString);

    // Save Operator details
    String savedDetails =
        operatorName.concat(" ").concat(operatorIdentity).concat(" ").concat(locationAsString);
    savedDetails = savedDetails.toLowerCase(); // makes sure comparissons are case insensitive

    // Setting initial conditions to check
    String lowerOperatorName = operatorName.toLowerCase();
    boolean operatorExists = false;

    // Checking if operator exists
    for (String opSaved : savedOpDetails) {
      if (opSaved.contains(operatorInitals.toLowerCase())
          && opSaved.contains(locationInitials.toLowerCase())
          && opSaved.contains(lowerOperatorName)) {
        operatorExists = true;
        break;
      }
    }

    // Deciding print & save details
    if (operatorExists) {
      MessageCli.OPERATOR_NOT_CREATED_ALREADY_EXISTS_SAME_LOCATION.printMessage(
          operatorName, locationAsString);
    } else {
      savedOperators.add(operatorSaved);
      savedOpDetails.add(savedDetails);
      savedOperatorIDs.add(operatorIdentity);
      MessageCli.OPERATOR_CREATED.printMessage(operatorName, operatorIdentity, locationAsString);
    }
  }

  //////////////////////////////////////////////////
  public void viewActivities(String operatorId) {
    // initalise count
    boolean IDExists = false;
    String searchedID = null;
    int count = 0;

    // check if operatorId exists
    for (String identity : savedOperatorIDs) {
      if (identity.contains(operatorId)) {
        IDExists = true;
        searchedID = operatorId;
        break;
      }
    }

    // if operatorId doesn't exist
    if (!IDExists) {
      MessageCli.OPERATOR_NOT_FOUND.printMessage(operatorId);
      return;
    }

    // Check how many operators exist with searched ID
    for (String operators : savedOpDetails) {
      if (operators.contains(searchedID)) {
        count++;
      }
    }

    if (count == 0) {
      MessageCli.ACTIVITIES_FOUND.printMessage("are", "no", "ies", ".");
    }
  }

  public void createActivity(String activityName, String activityType, String operatorId) {
    // intialise conditions
    boolean IDExists = false;

    // checking if activityName is valid
    if (activityName.length() < 3) {
      MessageCli.ACTIVITY_NOT_CREATED_INVALID_ACTIVITY_NAME.printMessage(activityName);
    }

    // check if operatorId exists
    for (String identity : savedOperatorIDs) {
      if (identity.contains(operatorId)) {
        IDExists = true;
        break;
      }
    }

    // if operatorId doesn't exist
    if (!IDExists) {
      MessageCli.ACTIVITY_NOT_CREATED_INVALID_OPERATOR_ID.printMessage(operatorId);
      return;
    } else {
      MessageCli.ACTIVITY_CREATED.printMessage(
          "activityName", "operatorId", activityType, "location");
    }
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
