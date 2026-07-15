import java.util.Scanner;

public class string {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a string: ");
        String input = scanner.nextLine().toLowerCase();
        
        int aCount = 0, eCount = 0, iCount = 0, oCount = 0, uCount = 0;
        int totalVowels = 0;
        
        for (char c : input.toCharArray()) {
            if (c == 'a') aCount++;
            else if (c == 'e') eCount++;
            else if (c == 'i') iCount++;
            else if (c == 'o') oCount++;
            else if (c == 'u') uCount++;
        }
        
        totalVowels = aCount + eCount + iCount + oCount + uCount;
        
        System.out.println("\n -Vowel Analysis-");
        System.out.println("Total vowels found: " + totalVowels);
        
        if (totalVowels > 0) {
            System.out.println("Vowel Frequencies:");
            if (aCount > 0) System.out.println("  a: " + aCount);
            if (eCount > 0) System.out.println("  e: " + eCount);
            if (iCount > 0) System.out.println("  i: " + iCount);
            if (oCount > 0) System.out.println("  o: " + oCount);
            if (uCount > 0) System.out.println("  u: " + uCount);
        } else {
            System.out.println("No vowels found in the string.");
        }
        
        scanner.close();
    }
}
