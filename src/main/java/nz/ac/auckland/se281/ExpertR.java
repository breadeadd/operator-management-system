package nz.ac.auckland.se281;

public class ExpertR extends Review {
  private boolean recommendation;
  private String image = null;

  public ExpertR(String name, int rating, String comment, String reviewId) {
    super(name, rating, comment, reviewId);
  }

  public void setRecommendation(Boolean set) {
    recommendation = set;
  }

  public boolean getRecommendation() {
    return recommendation;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public String getImage() {
    return image;
  }
}
