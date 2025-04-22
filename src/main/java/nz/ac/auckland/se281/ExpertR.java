package nz.ac.auckland.se281;

import java.util.ArrayList;

public class ExpertR extends Review {
  private boolean recommendation;
  private ArrayList<String> images = new ArrayList<>();

  public ExpertR(String name, int rating, String comment, String reviewId) {
    super(name, rating, comment, reviewId);
  }

  public void setRecommendation(Boolean set) {
    recommendation = set;
  }

  public boolean getRecommendation() {
    return recommendation;
  }

  // add image to list
  public void addImage(String image) {
    images.add(image);
  }

  // return image text
  public String getImages() {
    String returnString = "";

    for (String image : images) {
      if (returnString.equals("")) {
        // if only one image/ first image without comma
        returnString = image;
      } else {
        // additional images
        returnString = returnString.concat(String.format(",%s", image));
      }
    }

    return returnString;
  }

  // determine if review has images
  public boolean hasImage() {
    if (images.isEmpty()) {
      return false;
    } else {
      return true;
    }
  }
}
