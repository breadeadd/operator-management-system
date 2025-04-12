package nz.ac.auckland.se281;

public class PublicR extends Review {
  Boolean anon;

  public PublicR(String name, Boolean anon, int rating, String comment) {
    super(name, rating, comment);
    this.anon = anon;
  }
}
