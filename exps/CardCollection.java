import java.util.*;

class Card {
    String symbol;
    int number;

    Card(String symbol, int number) {
        this.symbol = symbol;
        this.number = number;
    }

    void display() {
        System.out.println(symbol + " - " + number);
    }
}

public class CardCollection {

    public static void main(String[] args) {

        Collection<Card> cards = new ArrayList<>();

        // Hearts
        for (int i = 1; i <= 13; i++) {
            cards.add(new Card("Hearts", i));
        }

        // Diamonds
        for (int i = 1; i <= 13; i++) {
            cards.add(new Card("Diamonds", i));
        }

        // Clubs
        for (int i = 1; i <= 13; i++) {
            cards.add(new Card("Clubs", i));
        }

        // Spades
        for (int i = 1; i <= 13; i++) {
            cards.add(new Card("Spades", i));
        }

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter symbol to search: ");
        String symbol = sc.nextLine();

        boolean found = false;

        System.out.println("\nCards with symbol: " + symbol);

        for (Card card : cards) {

            if (card.symbol.equalsIgnoreCase(symbol)) {
                card.display();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No cards found for this symbol.");
        }

        sc.close();
    }
}