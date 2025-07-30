// Phone.java
class Phone {
    private int id;
    private String brand;
    private String model;
    private double price;
    private int quantity;

    // Constructor for new phones (no ID yet)
    public Phone(String brand, String model, double price, int quantity) {
        this.brand = brand;
        this.model = model;
        this.price = price;
        this.quantity = quantity;
    }

    // Constructor from DB (with ID)
    public Phone(int id, String brand, String model, double price, int quantity) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.price = price;
        this.quantity = quantity;
    }

    public int getId() { return id; }
    public String getBrand() { return brand; }
    public String getModel() { return model; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }

    @Override
    public String toString() {
        return "[" + id + "] " + brand + " " + model + " - $" + price + " (" + quantity + " in stock)";
    }
}
