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

//Count the number of vowels, consonants, digits, and special characters in the string. 
import java.util.Scanner;
public class String_Analyzer{
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        int vowel = 0;
        int consonant = 0;
        int digits = 0;
        int sp_char = 0;
        System.out.print("Enter a string: ");
        String st = sc.nextLine();
        for (int i = 0; i < st.length(); i++){
            if (st.charAt(i) == 'a' || st.charAt(i) == 'e' || st.charAt(i) == 'i' || st.charAt(i) == 'o' || st.charAt(i) == 'u'){
                vowel++;
            }
            else if (st.charAt(i) >= 'a' && st.charAt(i) <= 'z'){
                consonant++;
            }
            else if(st.charAt(i) == 'A' || st.charAt(i) == 'E' || st.charAt(i) == 'I' || st.charAt(i) == 'O' || st.charAt(i) == 'U'){
                vowel++;
            }
            else if (st.charAt(i) >= 'A' && st.charAt(i) <= 'Z'){
                consonant++;
            }
            else if (st.charAt(i) >= '0' && st.charAt(i) <= '9'){
                digits++;
            }
            else{
                sp_char++;
            }
        }
        System.out.println("Total number of Vowel: "+ vowel);
        System.out.println("Total number of Consonant: "+ consonant);
        System.out.println("Total number of digits: "+ digits);
        System.out.println("Total number of sp_char: "+ sp_char);
        sc.close();
    }
}
