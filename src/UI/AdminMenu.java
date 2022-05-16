package UI;

public class AdminMenu {
  private static AdminMenu INSTANCE;

  public static AdminMenu getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new AdminMenu();
    }
    return INSTANCE;
  }

  public void startAction() {}
}
