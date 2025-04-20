package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.HashMap;
import nz.ac.auckland.se281.Types.ActivityType;
import nz.ac.auckland.se281.Types.Location;

public class OperatorManagementSystem {
  private HashMap<String, Integer> counts;
  private ArrayList<Operator> savedOperators;
  private ArrayList<Activities> savedActivities;
  private ArrayList<Review> savedReviews;

  // Do not change the parameters of the constructor
  public OperatorManagementSystem() {
    counts = new HashMap<>();
    savedOperators = new ArrayList<>();
    savedActivities = new ArrayList<>();
    savedReviews = new ArrayList<>();

    // initializing hashmap
    class Initialization {
      private boolean isInitialized = false;

      public void initializeCounts() {
        // Assigning keys and values
        if (!isInitialized) {
          // initalise counts
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
      if (savedOperator.getName().equals(keyword)
          || savedOperator.getLocation().contains(keyword)) {
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

    // setting three digit number - hashmap
    if (counts.containsKey(locationInitials)) {
      counts.put(locationInitials, counts.get(locationInitials) + 1);
    }

    String threeDigitNumber = String.format("%03d", counts.get(locationInitials));

    // Forming operatorID
    String operatorId = operatorInitals + "-" + locationInitials + "-" + threeDigitNumber;

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
      Operator operatorSaved = new Operator(operatorName, operatorId, locationAsString);
      savedOperators.add(operatorSaved);

      MessageCli.OPERATOR_CREATED.printMessage(operatorName, operatorId, locationAsString);
    }
  }

  //////////////////////////////////////////////////
  public void viewActivities(String operatorId) {
    // initalise count
    boolean IdExists = false;
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
        IdExists = true;
        break;
      }
    }

    // if operatorId doesn't exist
    if (!IdExists) {
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
    boolean idExists = false;
    Operator chosenOperator = null;

    // checking if activityName is valid
    if (activityName.trim().length() < 3) {
      MessageCli.ACTIVITY_NOT_CREATED_INVALID_ACTIVITY_NAME.printMessage(activityName);
      return;
    }

    activityName = activityName.trim();

    // check activity type
    // make activity type format correct
    activityType = activityType.trim().toLowerCase();
    activityType = activityType.substring(0, 1).toUpperCase() + activityType.substring(1);

    // check with existing options
    ActivityType typeFound = ActivityType.fromString(activityType);

    // check if operatorId exists
    for (Operator operator : savedOperators) {
      if (operator.getOperatorId().equals(operatorId)) {
        idExists = true;
        chosenOperator = operator;
        break;
      }
    }

    // if operatorId doesn't exist
    if (!idExists) {
      MessageCli.ACTIVITY_NOT_CREATED_INVALID_OPERATOR_ID.printMessage(operatorId);
      return;
    }

    // creating activity count
    chosenOperator.incrementCount();
    int activityCount = chosenOperator.getActivityCount();
    String activityId = String.format("%03d", activityCount);
    activityId = chosenOperator.getOperatorId().concat("-").concat(activityId);

    // adding new activity to list
    Activities newActivity = new Activities(activityName, typeFound, activityId, chosenOperator);
    savedActivities.add(newActivity);

    // Messages
    MessageCli.ACTIVITY_CREATED.printMessage(
        activityName, activityId, typeFound + "", chosenOperator.getName());
  }

  public void searchActivities(String keyword) {
    int activityCount = 0;
    // found array list
    ArrayList<Activities> foundActivities;

    // initialise found lists for each search
    foundActivities = new ArrayList<>();

    keyword = keyword.trim().toLowerCase();
    // need to get to be case insensitive search

    if (keyword.equals("*")) {
      for (Activities activity : savedActivities) {
        foundActivities.add(activity);
        activityCount++;
      }
    } else {
      for (Activities activity : savedActivities) {
        if (activity.getName().toLowerCase().contains(keyword)
            || activity.getType().toLowerCase().contains(keyword)
            || activity.getOperatorLocation().toLowerCase().contains(keyword)) {
          foundActivities.add(activity);
          activityCount++;
        }
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

  /////////////////////////////

  public void addPublicReview(String activityId, String[] options) {
    Boolean idExists = false;
    Activities chosenActivity = null;

    for (Activities checkActivity : savedActivities) {
      // checks if input id is exists in system
      if (checkActivity.getId().equals(activityId)) {
        chosenActivity = checkActivity;
        if (!idExists) {
          idExists = true;
          break;
        }
      }
    }

    // check if activityId exists before proceeding
    if (!idExists) {
      MessageCli.REVIEW_NOT_ADDED_INVALID_ACTIVITY_ID.printMessage(activityId);
      return;
    }

    // Processing options string
    // check if anon exists
    Boolean anon;
    if (options[1].equals("n")) {
      anon = false;
    } else {
      anon = true;
    }

    // setting rating to int type
    int rating = 0;
    switch (options[2]) {
      case "1":
        rating = 1;
        break;
      case "2":
        rating = 2;
        break;
      case "3":
        rating = 3;
        break;
      case "4":
        rating = 4;
        break;
      case "5":
        rating = 5;
        break;

      default:
        break;
    }

    // creating review ID

    chosenActivity.incrementCount();
    int reviewCount = chosenActivity.getCount();
    String reviewId = String.format("R%d", reviewCount);
    reviewId = chosenActivity.getId().concat("-").concat(reviewId);

    // PublicR() name, anon, rating, comment
    PublicR review = new PublicR(options[0], anon, rating, options[3], reviewId);
    savedReviews.add(review);
    MessageCli.REVIEW_ADDED.printMessage("Public", reviewId, chosenActivity.getName());
  }

  public void addPrivateReview(String activityId, String[] options) {
    Boolean idExists = false;
    Activities chosenActivity = null;

    for (Activities checkActivity : savedActivities) {
      // checks if input id is exists in system
      if (checkActivity.getId().equals(activityId)) {
        chosenActivity = checkActivity;
        if (!idExists) {
          idExists = true;
          break;
        }
      }
    }

    // check if activityId exists before proceeding
    if (!idExists) {
      MessageCli.REVIEW_NOT_ADDED_INVALID_ACTIVITY_ID.printMessage(activityId);
      return;
    }

    // setting rating to int type
    int rating = 0;
    switch (options[2]) {
      case "1":
        rating = 1;
        break;
      case "2":
        rating = 2;
        break;
      case "3":
        rating = 3;
        break;
      case "4":
        rating = 4;
        break;
      case "5":
        rating = 5;
        break;

      default:
        break;
    }

    // creating review ID

    chosenActivity.incrementCount();
    int reviewCount = chosenActivity.getCount();
    String reviewId = String.format("R%d", reviewCount);
    reviewId = chosenActivity.getId().concat("-").concat(reviewId);

    // PrivateR() name, anon, rating, comment
    PrivateR review = new PrivateR(options[0], options[1], rating, options[3], reviewId);

    // Checking if follow up
    if (options[4].equals("y")) {
      review.setFollowUp(true);
    } else {
      review.setFollowUp(false);
    }

    savedReviews.add(review);

    MessageCli.REVIEW_ADDED.printMessage("Private", reviewId, chosenActivity.getName());
  }

  public void addExpertReview(String activityId, String[] options) {
    Boolean idExists = false;
    Activities chosenActivity = null;

    for (Activities checkActivity : savedActivities) {
      // checks if input id is exists in system
      if (checkActivity.getId().equals(activityId)) {
        chosenActivity = checkActivity;
        if (!idExists) {
          idExists = true;
          break;
        }
      }
    }

    // check if activityId exists before proceeding
    if (!idExists) {
      MessageCli.REVIEW_NOT_ADDED_INVALID_ACTIVITY_ID.printMessage(activityId);
      return;
    }

    // Processing options string
    // setting rating to int type
    int rating = 0;
    switch (options[1]) {
      case "1":
        rating = 1;
        break;
      case "2":
        rating = 2;
        break;
      case "3":
        rating = 3;
        break;
      case "4":
        rating = 4;
        break;
      case "5":
        rating = 5;
        break;

      default:
        break;
    }

    // creating review ID

    chosenActivity.incrementCount();
    int reviewCount = chosenActivity.getCount();
    String reviewId = String.format("R%d", reviewCount);
    reviewId = chosenActivity.getId().concat("-").concat(reviewId);

    // ExpertR() name, anon, rating, comment
    ExpertR review = new ExpertR(options[0], rating, options[2], reviewId);

    // setting recommendation
    if (options[3].equals("n")) {
      review.setRecommendation(false);
    } else {
      review.setRecommendation(true);
    }

    savedReviews.add(review);
    MessageCli.REVIEW_ADDED.printMessage("Expert", reviewId, chosenActivity.getName());
  }

  public void displayReviews(String activityId) {
    // found reviews
    ArrayList<Review> foundReviews = new ArrayList<>();
    Activities reviewedActivity = null;
    Boolean idExists = false;

    for (Activities checkActivities : savedActivities) {
      // checks if input id is exists in system
      if (checkActivities.getId().equals(activityId)) {
        reviewedActivity = checkActivities;
        if (!idExists) {
          idExists = true;
        }
      }
    }

    if (!idExists) {
      MessageCli.REVIEW_NOT_FOUND.printMessage(activityId);
      return;
    }

    // irrelevant
    if (reviewedActivity == null) {
      return;
    }

    // finding all saved reviews at an activity + counting
    for (Review review : savedReviews) {
      if (review.getId().contains(activityId)) {
        foundReviews.add(review);
      }
    }

    int count = foundReviews.size();

    // checking plural
    String pluralOperator;
    String joiningWord;
    if (count > 1) {
      pluralOperator = "s";
      joiningWord = "are";
    } else {
      pluralOperator = "";
      joiningWord = "is";
    }

    if (count == 0) {
      MessageCli.REVIEWS_FOUND.printMessage("are", "no", "s", reviewedActivity.getName());
      return;
    } else {
      MessageCli.REVIEWS_FOUND.printMessage(
          joiningWord, count + "", pluralOperator, reviewedActivity.getName());

      // for each found review
      for (Review review : foundReviews) {
        // Get Review Type
        String reviewType = "";

        // initalise variables that switch
        String name = review.getName();

        if (review instanceof PublicR) {
          reviewType = "Public";
          PublicR publicReview = (PublicR) review;
          if (publicReview.getAnonymous()) {
            name = "Anonymous";
          }
        } else if (review instanceof PrivateR) {
          reviewType = "Private";
        } else if (review instanceof ExpertR) {
          reviewType = "Expert";
        }

        MessageCli.REVIEW_ENTRY_HEADER.printMessage(
            review.getRating() + "", "5", reviewType, review.getId(), name);
        MessageCli.REVIEW_ENTRY_REVIEW_TEXT.printMessage(review.getComment());

        // message details for public review
        if (review instanceof PublicR) {
          if (((PublicR) review).getEndorsed()) {
            MessageCli.REVIEW_ENTRY_ENDORSED.printMessage();
          }
        }

        // message details for private review
        if (review instanceof PrivateR) {
          // check if follow up is required
          if (((PrivateR) review).getFollowUp()) {
            MessageCli.REVIEW_ENTRY_FOLLOW_UP.printMessage(((PrivateR) review).getEmail());
          } else {
            // display no response or response
            MessageCli.REVIEW_ENTRY_RESOLVED.printMessage(((PrivateR) review).getResponse());
          }
        }

        if (review instanceof ExpertR) {
          // printing recommendation
          if (((ExpertR) review).getRecommendation()) {
            MessageCli.REVIEW_ENTRY_RECOMMENDED.printMessage();
          }

          // printing images
          if (((ExpertR) review).hasImage()) {
            MessageCli.REVIEW_ENTRY_IMAGES.printMessage(((ExpertR) review).getImages());
          }
        }
      }
    }
  }

  public void endorseReview(String reviewId) {
    Boolean reviewExists = false;

    for (Review review : savedReviews) {
      // only occurs at the right review
      if (review.getId().equals(reviewId)) {
        // mark that review exists
        if (!reviewExists) {
          reviewExists = true;
        }

        // check if public review + action
        if (review instanceof PublicR) {
          MessageCli.REVIEW_ENDORSED.printMessage(reviewId);
          ((PublicR) review).setEndorsed(true);
        } else {
          MessageCli.REVIEW_NOT_ENDORSED.printMessage(reviewId);
        }
        return;
      }
    }

    if (!reviewExists) {
      MessageCli.REVIEW_NOT_FOUND.printMessage(reviewId);
      return;
    }
  }

  public void resolveReview(String reviewId, String response) {
    Boolean reviewExists = false;

    for (Review review : savedReviews) {
      if (review.getId().equals(reviewId)) {
        // mark that review exists
        if (!reviewExists) {
          reviewExists = true;
        }

        // check if private review + action
        if (review instanceof PrivateR) {
          ((PrivateR) review).setResponse(response);
          MessageCli.REVIEW_RESOLVED.printMessage(review.getId());
          ((PrivateR) review).setFollowUp(false); // no longer needs response
        } else {
          MessageCli.REVIEW_NOT_RESOLVED.printMessage(reviewId);
        }
        return;
      }
    }

    if (!reviewExists) {
      MessageCli.REVIEW_NOT_FOUND.printMessage(reviewId);
      return;
    }
  }

  public void uploadReviewImage(String reviewId, String imageName) {
    Boolean reviewExists = false;

    for (Review review : savedReviews) {
      if (review.getId().equals(reviewId)) {
        // mark that review exists
        if (!reviewExists) {
          reviewExists = true;
        }

        // check if expert review + action
        if (review instanceof ExpertR) {
          ((ExpertR) review).addImage(imageName);
          MessageCli.REVIEW_IMAGE_ADDED.printMessage(imageName, reviewId);
        } else {
          MessageCli.REVIEW_IMAGE_NOT_ADDED_NOT_EXPERT.printMessage(reviewId);
        }
        return;
      }
    }

    if (!reviewExists) {
      MessageCli.REVIEW_NOT_FOUND.printMessage(reviewId);
      return;
    }
  }

  public void displayTopActivities() {
    for (Location loco : Location.values()) { // for each location
      double topAverage = 0;
      String topActivity = "";
      Boolean hasReviews = false;

      for (Activities activity : savedActivities) {
        if (!activity.getId().contains(loco.getLocationAbbreviation())) {
          continue;
        }

        int count = 0;
        double total = 0;
        for (Review review : savedReviews) {
          // checks if review is from the same activity in the right location
          if (review.getId().contains(activity.getId())) {

            // gather activity avg - top avg
            total += review.getRating();
            count++;
          }
        }

        if (count > 0) {
          hasReviews = true;
          double average = total / count;

          // after checking all reviews compare averages and replace
          if (average > topAverage) {
            // averages.put(locoCode, average);
            topAverage = average;
            topActivity = activity.getName();
          }
        }
      }

      // if count = 0
      if (!hasReviews) {
        MessageCli.NO_REVIEWED_ACTIVITIES.printMessage(loco.getFullName());
      } else {
        // display top activity
        MessageCli.TOP_ACTIVITY.printMessage(loco.getNameEnglish(), topActivity, topAverage + "");
      }
    }
  }
}
