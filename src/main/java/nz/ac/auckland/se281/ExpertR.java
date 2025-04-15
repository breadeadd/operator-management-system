package nz.ac.auckland.se281;

public class ExpertR extends Review {
  private boolean recommendation;

  public ExpertR(String name, int rating, String comment, String reviewId) {
    super(name, rating, comment, reviewId);
  }

  public void setRecommendation(Boolean set) {
    recommendation = set;
  }

  public boolean getRecommendation() {
    return recommendation;
  }
}
