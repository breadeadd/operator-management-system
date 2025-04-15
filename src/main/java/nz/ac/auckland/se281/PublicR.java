package nz.ac.auckland.se281;

public class PublicR extends Review {
  Boolean anonymous;
  Boolean endorsed = false;

  public PublicR(String name, Boolean anonymous, int rating, String comment, String reviewId) {
    super(name, rating, comment, reviewId);
    this.anonymous = anonymous;
  }

  public Boolean getAnonymous() {
    return anonymous;
  }

  public Boolean getEndorsed() {
    return endorsed;
  }

  public void setEndorsed(Boolean set) {
    endorsed = set;
  }
}
