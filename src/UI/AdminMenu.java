package UI;

public class AdminMenu {
  private static AdminMenu INSTANCE;
  private static final MainMenu mainMenu = MainMenu.getInstance();
  private final String[] menuOptions = initMenuOptions();

  public static AdminMenu getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new AdminMenu();
    }
    return INSTANCE;
  }

  private String[] initMenuOptions() {
    return new String[] {
      "1. See all Customers",
      "2. See all Rooms",
      "3. See all Reservations",
      "4. Add a room",
      "5. Add testing data",
      "6. Back to Main Menu"
    };
  }

  protected void startAction() {
    System.out.println("Please select a number for the menu option: ");
    boolean isExit = false;

    //    while (!isExit) {
    //      String userInput = MenuUtility.scanner.nextLine();
    //      switch (userInput) {
    //        case "1" -> ;
    //        case "2" -> printAllReservationsOfCustomer();
    //        case "3" -> createAccount();
    //        case "4" -> startAdmin();
    //        case "5" -> {
    //          isExit = true;
    //          System.out.println("Have a great day, Good Bye!");
    //        }
    //        case "6" -> ;
    //        default -> System.out.println("Please enter number from 1 to 5 to select a menu item:
    // ");
    //      }
    //    }
  }
}
