import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;

public class readfile {

	public static void main(String[] args) {
		Path filePath = args.length > 0 ? Paths.get(args[0]) : Paths.get("input.txt");

		try {
			String content = Files.readString(filePath);
			analyzeContent(content);
		} catch (IOException e) {
			System.out.println("Error reading file: " + e.getMessage());
		}
	}

	private static void analyzeContent(String content) {
		int totalCharacters = content.length();
		int letters = 0;
		int whitespace = 0;
		Map<Character, Integer> vowelCounts = new LinkedHashMap<>();
		Map<Character, Integer> digitCounts = new LinkedHashMap<>();
		Map<Character, Integer> specialCharacterCounts = new LinkedHashMap<>();
		int vowelTotal = 0;
		int digitTotal = 0;
		int specialCharacterTotal = 0;
		int lines = content.isEmpty() ? 0 : content.split("\\R", -1).length;

		for (int i = 0; i < content.length(); i++) {
			char ch = content.charAt(i);

			if (Character.isLetter(ch)) {
				letters++;
				if (isVowel(ch)) {
					char lower = Character.toLowerCase(ch);
					vowelCounts.put(lower, vowelCounts.getOrDefault(lower, 0) + 1);
					vowelTotal++;
				}
			} else if (Character.isDigit(ch)) {
				digitCounts.put(ch, digitCounts.getOrDefault(ch, 0) + 1);
				digitTotal++;
			} else if (Character.isWhitespace(ch)) {
				whitespace++;
			} else {
				specialCharacterCounts.put(ch, specialCharacterCounts.getOrDefault(ch, 0) + 1);
				specialCharacterTotal++;
			}
		}

		System.out.println("File description:");
		System.out.println("Total characters: " + totalCharacters);
		System.out.println("Letters: " + letters);
		System.out.println("Digits: " + digitTotal);
		System.out.println("Whitespace characters: " + whitespace);
		System.out.println("Special characters: " + specialCharacterTotal);
		System.out.println("Vowels: " + vowelTotal);
		System.out.println("Lines: " + lines);
		printCounts("Vowel frequencies", vowelCounts);
		printCounts("Digit frequencies", digitCounts);
		printCounts("Special character frequencies", specialCharacterCounts);
		System.out.println();
		System.out.println("Original content:");
		System.out.println(content);
	}

	private static void printCounts(String title, Map<Character, Integer> counts) {
		System.out.println();
		System.out.println(title + ":");
		if (counts.isEmpty()) {
			System.out.println("  none");
			return;
		}

		for (Map.Entry<Character, Integer> entry : counts.entrySet()) {
			System.out.println("  " + printableChar(entry.getKey()) + ": " + entry.getValue());
		}
	}

	private static String printableChar(char ch) {
		switch (ch) {
			case ' ':
				return "space";
			case '\t':
				return "tab";
			case '\n':
				return "newline";
			case '\r':
				return "carriage return";
			default:
				return String.valueOf(ch);
		}
	}

	private static boolean isVowel(char ch) {
		char lower = Character.toLowerCase(ch);
		return lower == 'a' || lower == 'e' || lower == 'i' || lower == 'o' || lower == 'u';
	}
}
