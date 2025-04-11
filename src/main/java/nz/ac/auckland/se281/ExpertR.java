package nz.ac.auckland.se281;

public class ExpertR extends Review {
  private boolean recommendation;

  public ExpertR(String name, int rating, String comment, Boolean recommendation) {
    super(name, rating, comment);
    this.recommendation = recommendation;
  }
}
