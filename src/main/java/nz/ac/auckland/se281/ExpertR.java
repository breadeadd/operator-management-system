package nz.ac.auckland.se281;

public class ExpertR extends Review {
  private boolean recommendation;

  public ExpertR(String name, int rating, String comment, Boolean recommendation, String reviewId) {
    super(name, rating, comment, reviewId);
    this.recommendation = recommendation;
  }

  public boolean getRecommendation() {
    return recommendation;
  }
}
