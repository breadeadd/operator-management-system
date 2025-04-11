package nz.ac.auckland.se281;

public abstract class Review {
  private String name;
  private int rating;
  private String comment;

  public Review(String name, int rating, String comment) {
    this.name = name;
    this.rating = rating;
    this.comment = comment;
  }
}
