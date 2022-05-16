package UI;

import api.HotelResource;
import model.IRoom;
import model.Reservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

public class MainMenu {
  private static MainMenu INSTANCE = MainMenu.getInstance();
  private static final AdminMenu adminMenu = AdminMenu.getInstance();
  private static final HotelResource hotelResource = HotelResource.getInstance();
  private String[] menuOptions;
  protected static Scanner scanner;

  public static MainMenu getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new MainMenu();
    }
    return INSTANCE;
  }

  public static void main(String[] args) throws ParseException {
    scanner = new Scanner(System.in);
    INSTANCE.initMenuOptions();
    INSTANCE.startAction();
    scanner.close();
  }

  private void initMenuOptions() {
    menuOptions =
        new String[] {
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
      String input = scanner.nextLine();
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
    String customerEmail = (!hasAccount()) ? createAccount() : getEmail(true);

    Date checkInDate =
        getDate(
            new Date(),
                """
                        Enter check in date in format mm/dd/yyyy:
                        Example: 05/10/2018
                        Notice: Feb only has 28 or 29 days, and some months has only 30 days.""",
            "You can only check in after today.");
    Date checkOutDate =
        getDate(
            checkInDate,
                """
                        Enter check in date out format mm/dd/yyyy:
                        Example: 05/10/2018
                        Notice: Feb only has 28 or 29 days, and some months has only 30 days.""",
            "You can only check out after the check in date.");

    bookARoom(customerEmail, checkInDate, checkOutDate);
  }

  private void bookARoom(String customerEmail, Date checkInDate, Date checkOutDate) {
    boolean hasBookDone = false;
    while (!hasBookDone) {
      System.out.println("Rooms available at the given date range: ");
      System.out.println(hotelResource.findRoom(checkInDate, checkOutDate));
      System.out.println("What room number would you like to reserve?");

      String input = scanner.nextLine();
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

  private void printAllReservationsOfCustomer() {
    boolean isEmailExisted = false;
    String customerEmail = null;
    while (!isEmailExisted) {
      customerEmail = getEmail(false);
      isEmailExisted = hotelResource.containsEmail(customerEmail);
      if (!isEmailExisted) {
        System.out.println("Your entered email is not in our system.");
      }
    }
    System.out.println(hotelResource.getCustomersReservations(customerEmail));
  }

  private Date getDate(Date bottomLine, String introMsg, String errorMsg) throws ParseException {
    while (true) {
      System.out.println(introMsg);
      String input = scanner.nextLine();
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
      String firstName = getName("Please enter your first name: ");
      String lastName = getName("Please enter your last name: ");
      //TODO: Delete since this checking logic might be trivial
      isAccountCreated = hotelResource.createACustomer(email, firstName, lastName);
      if (!isAccountCreated) {
        System.out.println("Fail to create an account since the email you entered was existed in our system." +
                " Please try another email:");
      } else {
        System.out.printf("Account created successful! Glad to meet you, %s %s\n", firstName, lastName);
        return email;
      }
    }
  }

  private String getEmail(boolean checkContainment) {
    while (true) {
      System.out.println("Please enter your email:\nEmail in format: name@domain.com");
      String input = scanner.nextLine();
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
      String input = scanner.nextLine();
      if (ValidationMethods.validateName(input)) {
        if (Character.isLowerCase((input.charAt(0)))) {
          char[] temp = input.toCharArray();
          temp[0] = Character.toUpperCase(temp[0]);
          return Arrays.toString(temp);
        }
        return input;
      }
    }
  }

  private void startAdmin() {
    adminMenu.startAction();
  }

  private void startAction() throws ParseException {
    MenuUtility.printMenu(
        "Welcome to the Hotel Reservation Application",
        "---------------------------------------------------",
        menuOptions);

    System.out.println("Please select a number for the menu option: ");
    boolean isExit = false;

    while (!isExit) {
      String userInput = scanner.nextLine();
      switch (userInput) {
        case "1" -> findAndReserve();
        case "2" -> printAllReservationsOfCustomer();
        case "3" -> createAccount();
        case "4" -> startAdmin();
        case "5" -> {
          isExit = true;
          System.out.println("Have a great day, Good Bye!");
        }
        default -> System.out.println("Please enter number from 1 to 5 to select a menu item: ");
      }
    }
  }
}
