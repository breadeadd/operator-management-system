package nz.ac.auckland.se281;

public class PrivateR extends Review {
  private String email;
  private Boolean followUp; // if follow up true = needs to be emailed
  private String followUpResponse = "-";

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

  public void setFollowUp(Boolean set) {
    followUp = set;
  }

  public String getResponse() {
    return followUpResponse;
  }

  public void setResponse(String response) {
    followUpResponse = response;
  }
}
