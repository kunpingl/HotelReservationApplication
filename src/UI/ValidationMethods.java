package UI;

import api.HotelResource;

import java.util.regex.Pattern;

public class ValidationMethods {
  private static final Pattern datePattern = Pattern.compile("^\\d{2}/\\d{2}/\\d{4}$");
  private static final Pattern emailPattern = Pattern.compile("^(.+)@(.+).(com|edu)$");
  private static final Pattern namePattern = Pattern.compile("^([a-z]|[A-Z])+$");
  private static final Pattern roomNumberPattern = Pattern.compile("^[0-9]{4}$");

  protected static boolean isYes(String input) {
    return input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("y");
  }

  protected static boolean isNo(String input) {
    return input.equalsIgnoreCase("no") || input.equalsIgnoreCase("n");
  }

  protected static boolean isDate(String input) {
    if (!datePattern.matcher(input).matches()) return false;
    String[] dateTokens = input.split("/");
    int month = Integer.parseInt(dateTokens[0]);
    int day = Integer.parseInt(dateTokens[1]);
    int year = Integer.parseInt(dateTokens[2]);
    return validateDate(month, day, year);
  }

  private static boolean isLeapYear(int year) {
    return (year % 4 == 0 && year % 100 != 0)
        || (year % 400 == 0 && year % 3200 != 0)
        || year % 172800 == 0;
  }

  private static boolean isThirtyDayMonth(int month) {
    return month == 4 || month == 6 || month == 9 || month == 11;
  }

  private static boolean validateDate(int month, int day, int year) {
    if (month < 1 || month > 12) return false;
    if (day < 1) return false;
    if (month == 2 && isLeapYear(year)) {
      return day <= 29;
    } else if (month == 2 && !isLeapYear(year)) {
      return day <= 28;
    } else if (isThirtyDayMonth(month)) {
      return day <= 30;
    } else {
      return day <= 31;
    }
  }

  protected static boolean validateEmail(
      String input, HotelResource hotelResource, boolean checkContainment) {
    if (checkContainment && hotelResource.containsEmail(input)) return false;
    return emailPattern.matcher(input).matches();
  }

  protected static boolean validateName(String input) {
    return namePattern.matcher(input).matches();
  }

  protected static boolean validateRoomNumber(String input) {
    // "0000" is reserved for admin room
    if (input.equals("0000")) return false;
    return roomNumberPattern.matcher(input).matches();
  }

  protected static boolean isSingle(String input) {
    return input.equalsIgnoreCase("Single") || input.equalsIgnoreCase("S");
  }

  protected static boolean isDouble(String input) {
    return input.equalsIgnoreCase("Double") || input.equalsIgnoreCase("D");
  }
}
