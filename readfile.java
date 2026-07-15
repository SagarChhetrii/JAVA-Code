import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
		int digits = 0;
		int whitespace = 0;
		int specialCharacters = 0;
		int vowels = 0;
		int lines = content.isEmpty() ? 0 : content.split("\\R", -1).length;

		for (int i = 0; i < content.length(); i++) {
			char ch = content.charAt(i);

			if (Character.isLetter(ch)) {
				letters++;
				if (isVowel(ch)) {
					vowels++;
				}
			} else if (Character.isDigit(ch)) {
				digits++;
			} else if (Character.isWhitespace(ch)) {
				whitespace++;
			} else {
				specialCharacters++;
			}
		}

		System.out.println("File description:");
		System.out.println("Total characters: " + totalCharacters);
		System.out.println("Letters: " + letters);
		System.out.println("Digits: " + digits);
		System.out.println("Whitespace characters: " + whitespace);
		System.out.println("Special characters: " + specialCharacters);
		System.out.println("Vowels: " + vowels);
		System.out.println("Lines: " + lines);
		System.out.println();
		System.out.println("Original content:");
		System.out.println(content);
	}

	private static boolean isVowel(char ch) {
		char lower = Character.toLowerCase(ch);
		return lower == 'a' || lower == 'e' || lower == 'i' || lower == 'o' || lower == 'u';
	}
}
