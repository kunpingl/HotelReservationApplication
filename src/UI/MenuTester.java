package UI;

import java.text.ParseException;

public class MenuTester {
    public static void main(String[] args) {
        MainMenu mainMenu = MainMenu.getInstance();
        System.out.println(ValidationMethods.isDate("04/30/2400"));
        System.out.println(ValidationMethods.isDate("04/31/2400"));
        System.out.println(ValidationMethods.isDate("06/30/3333"));
        System.out.println(ValidationMethods.isDate("06/31/3333"));
        System.out.println(ValidationMethods.isDate("09/30/3333"));
        System.out.println(ValidationMethods.isDate("09/31/3333"));
        System.out.println(ValidationMethods.isDate("11/30/3333"));
        System.out.println(ValidationMethods.isDate("11/31/3333"));
        System.out.println(ValidationMethods.isDate("12/30/3333"));
        System.out.println(ValidationMethods.isDate("12/31/2400"));
        System.out.println(ValidationMethods.isDate("12/32/2400"));
        System.out.println(ValidationMethods.isDate("01/30/2400"));
        System.out.println(ValidationMethods.isDate("01/31/2400"));
        System.out.println(ValidationMethods.isDate("01/32/3333"));
    }
}
