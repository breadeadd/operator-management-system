package nz.ac.auckland.se281;

public abstract class Review {
  private String name;
  private int rating;
  private String comment;
  private String reviewId;

  public Review(String name, int rating, String comment, String reviewId) {
    this.name = name;
    this.rating = rating;
    this.comment = comment;
    this.reviewId = reviewId;
  }

  public String getName() {
    return name;
  }

  public int getRating() {
    return rating;
  }

  public String getComment() {
    return comment;
  }

  public String getId() {
    return reviewId;
  }
}
