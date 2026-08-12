class Product {
    private int id;
    private String name;
    private double price;

    public Product() {
        this(0, "Unknown", 0.0);
    }

    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public void displayDetails() {
        System.out.println("Product ID: " + id);
        System.out.println("Product Name: " + name);
        System.out.println("Product Price: $" + price);
    }

    public static void main(String[] args) {
        Product product1 = new Product();
        Product product2 = new Product(101, "Laptop", 799.99);

        System.out.println("Default Constructor:");
        product1.displayDetails();

        System.out.println("\nParameterized Constructor:");
        product2.displayDetails();
    }
}
