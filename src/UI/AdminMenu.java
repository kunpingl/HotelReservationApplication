package UI;

import api.AdminResource;
import model.Customer;
import model.IRoom;
import model.Reservation;
import model.RoomType;

public class AdminMenu {
  private static AdminMenu INSTANCE;
  private static final MainMenu mainMenu = MainMenu.getInstance();
  private static final AdminResource adminResource = AdminResource.getInstance();
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

  private void printAllCustomers() {
    System.out.println("All customers in our system:");
    for (Customer eachCustomer : adminResource.getAllCustomers()) {
      System.out.println(eachCustomer);
    }
    System.out.println();
  }

  private void printAllRooms() {
    System.out.println("All rooms in our system:");
    for (IRoom eachRoom : adminResource.getAllRooms()) {
      System.out.println(eachRoom);
    }
    System.out.println();
  }

  private void printAllReservations() {
    adminResource.displayAllReservations();
  }

  private void addARoom() {
    boolean isRoomAdded = false;
    while (!isRoomAdded) {
      String roomNumber = getRoomNumber();
      Double price = getPrice();
      RoomType roomType = getRoomType();
      if (adminResource.addARoom(roomNumber, price, roomType)) {
        isRoomAdded = true;
      } else {
        System.out.println("Failed to add a room. Room number must be unique.\n");
      }
    }
  }

  private String getRoomNumber() {
    while (true) {
      System.out.println("Enter room number: ");
      String input = MenuUtility.scanner.nextLine();
      if (ValidationMethods.validateRoomNumber(input)) {
        return input;
      } else {
        System.out.println("Room number need to be having 4 digits, except 0000.\nExample: 0001, 9999, 1234\n");
      }
    }
  }

  private Double getPrice() {
    while (true) {
      System.out.println("Enter room price: ");
      Double input = parseDouble();
      if (input != null) {
        return input;
      }
    }
  }

  private Double parseDouble() {
    try {
      return MenuUtility.scanner.nextDouble();
    } catch (Exception ex) {
      System.out.println("Please enter a number, either integer or double: ");
      return null;
    } finally {
      MenuUtility.scanner.nextLine();
    }
  }

  private RoomType getRoomType() {
    while (true) {
      System.out.println("Enter room type, either Single(S) or Double(D): ");
      String input = MenuUtility.scanner.nextLine();
      if (ValidationMethods.isSingle(input)) {
        return RoomType.SINGLE;
      } else if (ValidationMethods.isDouble(input)) {
        return RoomType.DOUBLE;
      } else {
        System.out.println("Input for RoomType is invalid.\n");
      }
    }
  }

  private void addTestingData() {
    System.out.println("TODO");
  }

  protected void startAction() {
    boolean isExit = false;
      while (!isExit) {
          MenuUtility.printMenu(
                  "Admin Menu",
                  "---------------------------------------------------",
                  menuOptions);
          System.out.println("Please select a number for the menu option: ");
          String userInput = MenuUtility.scanner.nextLine();
          switch (userInput) {
            case "1" -> printAllCustomers();
            case "2" -> printAllRooms();
            case "3" -> printAllReservations();
            case "4" -> addARoom();
            case "5" -> addTestingData();
            case "6" -> isExit = true;
            default -> System.out.println("Please enter number from 1 to 6 to select a menu item:");
          }
        }
  }
}
