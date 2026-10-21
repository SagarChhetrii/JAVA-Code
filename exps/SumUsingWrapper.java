import java.util.*;

public class SumUsingWrapper {

    public static Integer parseInteger(String str) {
        return Integer.parseInt(str);
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter numbers separated by spaces: ");
        String input = sc.nextLine();

        String[] numbers = input.split(" ");

        ArrayList<Integer> list = new ArrayList<>();


        for (String num : numbers) {
            Integer value = parseInteger(num);
            list.add(value);  
        }

        int sum = 0;

        for (Integer value : list) {
            sum += value;    
        }

        System.out.println("List: " + list);
        System.out.println("Sum: " + sum);

        sc.close();
    }
}