import java.util.ArrayList;

public class Library {
    private ArrayList<Book> books = new ArrayList<>();

    public static void main(String[] args) {
        Library library = new Library();

        library.addBook("F101", "The Hobbit", "J.R.R. Tolkien");
        library.addBook("F102", "1984", "George Orwell");

        System.out.println("All books:");
        library.showBooks();

        library.issueBook("F101");
        library.returnBook("F101");

        System.out.println("\nUpdated books:");
        library.showBooks();
    }

    public void addBook(String id, String title, String author) {
        Book newBook = new Book(id, title, author);
        books.add(newBook);
    }

    public void showBooks() {
        if (books.isEmpty()) {
            System.out.println("No books in the library.");
            return;
        }

        for (Book book : books) {
            System.out.println(book.getDetails());
        }
    }

    public void issueBook(String id) {
        for (Book book : books) {
            if (book.id.equals(id)) {
                book.issue();
                return;
            }
        }
        System.out.println("Book not found.");
    }

    public void returnBook(String id) {
        for (Book book : books) {
            if (book.id.equals(id)) {
                book.returnBook();
                return;
            }
        }
        System.out.println("Book not found.");
    }

    static class Book {
        String id;
        String title;
        String author;
        boolean issued;

        Book(String id, String title, String author) {
            this.id = id;
            this.title = title;
            this.author = author;
            this.issued = false;
        }

        void issue() {
            if (!issued) {
                issued = true;
                System.out.println(title + " issued successfully.");
            } else {
                System.out.println(title + " is already issued.");
            }
        }

        void returnBook() {
            if (issued) {
                issued = false;
                System.out.println(title + " returned successfully.");
            } else {
                System.out.println(title + " was not issued.");
            }
        }

        String getDetails() {
            return "ID: " + id + ", Title: " + title + ", Author: " + author + ", Issued: " + issued;
        }
    }
}
