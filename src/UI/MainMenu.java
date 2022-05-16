package UI;

import api.HotelResource;
import model.IRoom;
import model.Reservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class MainMenu {
  private static final AdminMenu adminMenu = AdminMenu.getInstance();  private static MainMenu INSTANCE = MainMenu.getInstance();
  private static final HotelResource hotelResource = HotelResource.getInstance();
  private final String[] menuOptions = initMenuOptions();

  public static MainMenu getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new MainMenu();
    }
    return INSTANCE;
  }

  public static void main(String[] args) throws ParseException {
    MenuUtility.scanner = new Scanner(System.in);
    INSTANCE.startAction();
    MenuUtility.scanner.close();
  }

  private String[] initMenuOptions() {
    return new String[] {
            "1. Find and reserve a room",
            "2. See my reservations",
            "3. Create an Account",
            "4. Admin",
            "5. Exit"
    };
  }

  private boolean hasAccount() {
    System.out.println("Do you have an account with us? y/n");
    while (true) {
      String input = MenuUtility.scanner.nextLine();
      if (ValidationMethods.isYes(input)) {
        return true;
      } else if (ValidationMethods.isNo(input)) {
        return false;
      } else {
        System.out.println("Please enter Y (Yes) or N (No): ");
      }
    }
  }

  private void findAndReserve() throws ParseException {
    String customerEmail = (!hasAccount()) ? createAccount() : getCustomerEmail();
    if (customerEmail == null) return;

    Date checkInDate =
        getDate(
            new Date(),
                """
                        Enter check in date in format mm/dd/yyyy. Example: 05/10/2018
                        Notice: Feb only has 28 or 29 days, and some months has only 30 days.""",
            "You can only check in after today.\n");
    Date checkOutDate =
        getDate(
            checkInDate,
                """
                        Enter check in date out format mm/dd/yyyy. Example: 05/10/2018
                        Notice: Feb only has 28 or 29 days, and some months has only 30 days.""",
            "You can only check out after the check in date.\n");

    bookARoom(customerEmail, checkInDate, checkOutDate);
  }

  private void bookARoom(String customerEmail, Date checkInDate, Date checkOutDate) {
    boolean hasBookDone = false;
    while (!hasBookDone) {
      System.out.println("Rooms available at the given date range: ");
      List<IRoom> allAvailableRooms = hotelResource.findRoom(checkInDate, checkOutDate);
      if (allAvailableRooms.size() == 0) {
        System.out.println("There is no room available :(\n");
        break;
      }
      System.out.println("What room number would you like to reserve?");
      printAllAvailableRooms(allAvailableRooms);

      String input = MenuUtility.scanner.nextLine();
      if (ValidationMethods.validateRoomNumber(input)) {
        IRoom roomToBook = hotelResource.getRoom(input);
        if (roomToBook != null) {
          Reservation reservation = hotelResource.bookARoom(customerEmail, roomToBook, checkInDate, checkOutDate);
          System.out.println(reservation);
          hasBookDone = true;
        } else {
          System.out.println("There is no such a room %s available during the given date range.");
        }
      } else {
        System.out.println("The room number your entered is not valid." +
                " The room number is consists of 4 digits except 0000." +
                "\nExample: 0001, 9999, 1234");
      }
    }
  }

  private void printAllAvailableRooms(List<IRoom> allAvailableRooms) {
    for (IRoom eachRoom : allAvailableRooms) {
      System.out.println(eachRoom);
    }
    System.out.println();
  }

  private void printAllReservationsOfCustomer() {
    String customerEmail = getCustomerEmail();
    if (customerEmail != null) {
      for (Reservation eachReservation : hotelResource.getCustomersReservations(customerEmail)) {
        System.out.println(eachReservation);
      }
      System.out.println();
    }
  }

  private String getCustomerEmail() {
    boolean isExisted = false;
    String customerEmail = null;
    while (!isExisted) {
      customerEmail = getEmail(false);
      isExisted = hotelResource.containsEmail(customerEmail);
      if (!isExisted) {
        System.out.println("Your email is not in our system. Please create an account first.\n");
        return null;
      }
    }
    return customerEmail;
  }

  private Date getDate(Date bottomLine, String introMsg, String errorMsg) throws ParseException {
    while (true) {
      System.out.println(introMsg);
      String input = MenuUtility.scanner.nextLine();
      if (ValidationMethods.isDate(input)) {
        Date enteredDate = new SimpleDateFormat("MM/dd/yyyy").parse(input);
        if (enteredDate.compareTo(bottomLine) > 0) {
          return enteredDate;
        }
        System.out.println(errorMsg);
      }
    }
  }

  private String createAccount() {
    boolean isAccountCreated;
    while (true) {
      String email = getEmail(true);
      String firstName = getName("Please enter your first name without whitespace: ");
      String lastName = getName("Please enter your last name without whitespace: ");
      //TODO: Delete since this checking logic might be trivial
      isAccountCreated = hotelResource.createACustomer(firstName, lastName, email);
      if (!isAccountCreated) {
        System.out.println("Fail to create an account since the email you entered was existed in our system." +
                " Please try another email:");
      } else {
        System.out.printf("Account created successful! Glad to meet you, %s %s\n\n", firstName, lastName);
        return email;
      }
    }
  }

  private String getEmail(boolean checkContainment) {
    while (true) {
      System.out.println("Please enter your email in format: name@domain.com");
      String input = MenuUtility.scanner.nextLine();
      if (ValidationMethods.validateEmail(input, hotelResource, checkContainment)) {
        return input;
      } else {
        System.out.println("Your entered email is not valid, or this email has been used by other account.");
      }
    }
  }

  private String getName(String msg) {
    while (true) {
      System.out.println(msg);
      String input = MenuUtility.scanner.nextLine();
      if (ValidationMethods.validateName(input)) {
        if (Character.isLowerCase((input.charAt(0)))) {
          char[] temp = input.toCharArray();
          temp[0] = Character.toUpperCase(temp[0]);
          return new String(temp);
        }
        return input;
      }
    }
  }

  private void startAdmin() {
    adminMenu.startAction();
  }

  protected void startAction() throws ParseException {
    System.out.println("\nWelcome to the Hotel Reservation Application\n");
    boolean isExit = false;
    while (!isExit) {
      MenuUtility.printMenu(
              "Main Menu",
              "---------------------------------------------------",
              menuOptions);
      System.out.println("Please select a number for the menu option: ");

      String userInput = MenuUtility.scanner.nextLine();
      switch (userInput) {
        case "1" -> findAndReserve();
        case "2" -> printAllReservationsOfCustomer();
        case "3" -> createAccount();
        case "4" -> startAdmin();
        case "5" -> {
          isExit = true;
          System.out.println("Have a great day, Good Bye!");
        }
        //TODO: catching "quit" to break some while loops and go back to the previous level of option
        default -> System.out.println("Please enter number from 1 to 5 to select a menu item: ");
      }
    }
  }


}
