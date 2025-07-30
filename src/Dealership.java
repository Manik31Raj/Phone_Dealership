import java.util.*;

public class Dealership {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Inventory inventory = new Inventory();

        while (true) {
            System.out.println("\nPhone Dealership Menu:");
            System.out.println("1. Add Phone");
            System.out.println("2. View Inventory");
            System.out.println("3. Sell Phone");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Brand: ");
                    String brand = sc.nextLine();
                    System.out.print("Model: ");
                    String model = sc.nextLine();
                    System.out.print("Price: ");
                    double price = sc.nextDouble();
                    System.out.print("Quantity: ");
                    int qty = sc.nextInt();
                    inventory.addPhone(new Phone(brand, model, price, qty));
                    break;

                case 2:
                    List<Phone> allPhones = inventory.getAllPhones();
                    if (allPhones.isEmpty()) {
                        System.out.println("Inventory is empty.");
                    } else {
                        for (Phone p : allPhones) {
                            System.out.println(p);
                        }
                    }
                    break;

                case 3:
                    List<Phone> phones = inventory.getAllPhones();
                    if (phones.isEmpty()) {
                        System.out.println("Inventory is empty.");
                        break;
                    }

                    for (Phone p : phones) {
                        System.out.println(p);
                    }

                    System.out.print("Enter phone ID to sell: ");
                    int id = sc.nextInt();
                    System.out.print("Enter quantity to sell: ");
                    int sellQty = sc.nextInt();

                    inventory.sellPhone(id, sellQty);
                    break;

                case 4:
                    System.out.println("Goodbye!");
                    return;

                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}
