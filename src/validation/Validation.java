
package validation;
import entity.Fruit;
import entity.Order;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;
/**         
 *
 * @author Admin
 */
public class Validation {
    private static final Scanner sc = new Scanner(System.in);

    // nên sử dụng function static để gọi khi cần thiết, ko cần khởi tạo đối tượng.
   
    public static String checkInputString(String inputMsg, String errorMsg, String regex) {
        String input;
        boolean match;
        while (true) {
            System.out.print(inputMsg);
            input = sc.nextLine().trim();
            match = input.matches(regex);
            if (match == false)
                System.out.println(errorMsg);
            else
                return input;            
        }
    }
    
   
    public static int checkInputIntLimit(int min, int max) {
        while (true) {
            try {
                int result = Integer.parseInt(sc.nextLine());
                if (result < min || result > max) {
                    throw new NumberFormatException();
                }
                return result;
            } catch (NumberFormatException e) {
                System.err.println("Please input integer number in rage [" + min + ", " + max + "]");
                System.out.print("Enter again: ");
            }
        }
    }
    
    public static double checkInputDoubleLimit(double min, double max) {
        while (true) {
            try {
                double result = Double.parseDouble(sc.nextLine());
                if (result < min || result > max) {
                    throw new NumberFormatException();
                }
                return result;
            } catch (NumberFormatException e) {
                System.err.println("Please input double number in rage [" + min + ", " + max + "]");
                System.out.print("Enter again: ");
            }
        }
    }
    
    public static boolean checkInputYN() {
        while (true) {
            String result = sc.nextLine().trim();
            if (result.equalsIgnoreCase("Y")) {
                return true;
            } else if (result.equalsIgnoreCase("N")) {
                return false;
            }
            System.err.println("Please input y/Y or n/N");
            System.out.print("Enter again: ");
        }
    }
}
