package UI;

public class MenuUtility {
    protected static void printMenu(String introduction, String split, String[] menuOptions) {
        System.out.printf("%s\n\n%s\n", introduction, split);
        for (String eachOption : menuOptions) {
            System.out.println(eachOption);
        }
        System.out.println(split);
    }
}
