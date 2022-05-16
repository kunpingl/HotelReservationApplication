package UI;

import java.util.Scanner;

public class MenuUtility {
  protected static Scanner scanner;

  protected static void printMenu(String title, String split, String[] menuOptions) {
    System.out.printf("%s\n%s\n", title, split);
    for (String eachOption : menuOptions) {
      System.out.println(eachOption);
    }
    System.out.println(split);
  }
}
