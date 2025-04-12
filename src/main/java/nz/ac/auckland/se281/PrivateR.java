package nz.ac.auckland.se281;

public class PrivateR extends Review {
  private String email;
  private Boolean followUp;

  public PrivateR(String name, String email, int rating, String comment, String reviewId) {
    super(name, rating, comment, reviewId);
    this.email = email;
  }

  public String getEmail() {
    return email;
  }

  public Boolean getFollowUp() {
    return followUp;
  }
}
