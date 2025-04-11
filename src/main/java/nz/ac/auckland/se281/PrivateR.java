package nz.ac.auckland.se281;

public class PrivateR extends Review {
  private String email;
  private Boolean followUp;

  public PrivateR(String name, String email, int rating, String comment) {
    super(name, rating, comment);
    this.email = email;
  }
}
