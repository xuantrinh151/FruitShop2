package manage;

import entity.Fruit;
import entity.Order;
import java.util.ArrayList;
import validation.Validation;
import java.util.Hashtable;
import java.time.LocalDateTime;

public class ManagerFruit {

    private ArrayList<Fruit> fruits = new ArrayList();
    private Hashtable<String, ArrayList<Order>> ht = new Hashtable<>();

    public void menu() {
        System.out.println("FRUIT SHOP SYSTEM");
        System.out.println(" 1.Create Fruit");
        System.out.println(" 2.Update Fruit");
        System.out.println(" 3.View orders");
        System.out.println(" 4.Shopping (for buyer)");
        System.out.println(" 5.Exit");
        System.out.print(" Enter your choice: ");
    }

    public void createFruit() {
        
        while (true) {
            System.out.print("Enter fruitID: ");
            int fruitID = Validation.checkInputIntLimit(0, Integer.MAX_VALUE);
            int pos = searchFruitById(fruitID);
            if(pos != -1){
                System.out.println("The FruitID already exists. "
                        + "Input another one!");
                continue;
            }

            String fruitName = Validation.checkInputString("Enter Fruit Name: ",
                    "Invalid input! The fruit name is required!", "[a-zA-Z ]+");
            System.out.print("Enter Price: ");
            double fruitPrice = Validation.checkInputDoubleLimit(0, Double.MAX_VALUE);
            System.out.print("Enter Quantity: ");
            int fruitQuantity = Validation.checkInputIntLimit(0, Integer.MAX_VALUE);
            String fruitOrigin = Validation.checkInputString("Enter Origin: ",
                    "Invalid input! The fruit origin is required!", "[a-zA-Z0-9 ]+");
            fruits.add(new Fruit(fruitID,fruitName, fruitPrice, fruitQuantity, fruitOrigin));
            System.out.println("Create success");
            System.out.print("Do you want to continue (Y/N): ");
            if (!Validation.checkInputYN()) {
                return;
            }
        }
    }

    public void updateFruit() {
        System.out.print("Enter fruitID:");
        int fruitId = Validation.checkInputIntLimit(0, Integer.MAX_VALUE);
        Fruit x = searchById(fruitId);
        if (x != null) {
            System.out.print("Enter Quantity: ");
            int quantity = Validation.checkInputIntLimit(0, Integer.MAX_VALUE);
            x.setQuantity(quantity);
            System.out.println("Update Success");
        } else {
            System.out.print("Do you want to create a new fruit Yes or No :");
            if (!Validation.checkInputYN()) {
                return;
            }
            String fruitName = Validation.checkInputString("Enter Fruit Name: ",
                    "Invalid input! The fruit name is required!", "[a-zA-Z ]+");
            System.out.print("Enter Price: ");
            double fruitPrice = Validation.checkInputDoubleLimit(0, Double.MAX_VALUE);
            System.out.print("Enter Quantity: ");
            int fruitQuantity = Validation.checkInputIntLimit(0, Integer.MAX_VALUE);
            String fruitOrigin = Validation.checkInputString("Enter Origin: ",
                    "Invalid input! The fruit origin is required!", "[a-zA-Z0-9 ]+");
            fruits.add(new Fruit(fruitId,fruitName, fruitPrice, fruitQuantity, fruitOrigin));
            System.out.println("Create success");
        }
    }

    public void shoppingFruit() {
        if (fruits.isEmpty()) {
            System.err.println("No have item");
            return;
        }
        ArrayList<Order> orders = new ArrayList<>();
        while (true) {
            displayListOfFruit();
            if (checkOutOfStock()) {
                System.err.println("Currently out of stock, please come back later");
                return;
            }
            int count = 0;
            for (Fruit fruit : fruits) {
                if (fruit.getQuantity() != 0) {
                    count++;
                }
            }
            System.out.print("Please choose one item: ");
            int item = Validation.checkInputIntLimit(1, count);
            Fruit fruit = getFruitByItem(item);
            System.out.println("You select:" + fruit.getFruitName());
            System.out.print("Please input quantity: ");
            int quantity = Validation.checkInputIntLimit(1, fruit.getQuantity());
            fruit.setQuantity(fruit.getQuantity() - quantity);

            if (checkItemExist(orders, fruit.getFruitID())) {
                updateOrder(orders, fruit.getFruitID(), quantity);
            } else {
                orders.add(new Order(fruit.getFruitID(), fruit.getFruitName(), quantity, fruit.getPrice()));
            }
            System.out.print("Do you want to order now(Y/N): ");
            if (Validation.checkInputYN()) {
                displayListOrder(orders);
                while (true) {
                    String name = Validation.checkInputString("Input Your Name: ",
                            "Invalid input! The your name is required!", "[a-zA-Z ]+");
                    ht.put(name + " " + LocalDateTime.now(), orders);
                    System.out.println("Order success.");
                    return;
                }
            }
        }
    }

    public void viewOrder() {
        for (String name : ht.keySet()) {
            System.out.println("Customer: " + name);
            ArrayList<Order> orders = ht.get(name);
            displayListOrder(orders);
        }
    }

    public void displayAllFruit() {
        System.out.printf("%-10s|%-25s|%-15s|%-15s|%-15s\n", "FruitID", "Fruit Name", "Price", "Quantity", "Origin");
        for (Fruit fruit : fruits) {
            System.out.printf("%-10s|%-25s|%-15s|%-15s|%-15s\n",
                    fruit.getFruitID(), fruit.getFruitName(), fruit.getPrice(), fruit.getQuantity(), fruit.getOrigin());
        }
    }

    public void displayListOfFruit() {
        int count = 1;
        System.out.printf("%-10s|%-25s|%-15s|%-15s|%-15s\n", "++Item++", "++Fruit Name++", "++Price++", "++Quantity++", "++Origin++");
        for (Fruit fruit : fruits) {
            if (fruit.getQuantity() != 0) {
                System.out.printf("%-10s|%-25s|%-15s|%-15s|%-15s\n",
                        count++, fruit.getFruitName(), fruit.getPrice(), fruit.getQuantity(), fruit.getOrigin());
            }
        }
    }

    public void displayListOrder(ArrayList<Order> orders) {
        double total = 0;
        System.out.printf("%-15s|%-15s|%-15s|%-15s\n", "Product", "Quantity", "Price", "Amount");
        for (Order order : orders) {
            System.out.printf("%-15s|%-15s|%-14.0f$|%-14.0f$\n", order.getFruitName(),
                    order.getQuantity(), order.getPrice(),
                    order.getPrice() * order.getQuantity());
            total += order.getPrice() * order.getQuantity();
        }
        System.out.println("Total: " + total);
    }

    public void updateOrder(ArrayList<Order> orders, int id, int quantity) {
        for (Order order : orders) {
            if (id == order.getFruitId()) {
                order.setQuantity(order.getQuantity() + quantity);
                return;
            }
        }
    }

    public Fruit getFruitByItem(int item) {
        int countItem = 1;
        for (Fruit fruit : fruits) {
            if (fruit.getQuantity() != 0) {
                countItem++;
            }
            if (countItem - 1 == item) {
                return fruit;
            }
        }
        return null;
    }

    public Fruit searchById(int id) {
        for (Fruit fruit : fruits) {
            if (fruit.getFruitID() == id) {
                return fruit;
            }
        }
        return null;
    }
    
    public int searchFruitById(int id){
        if(fruits.isEmpty()) return -1;
        for (int i = 0; i < fruits.size(); i++) {
            if(fruits.get(i).getFruitID() == id){
                return i;
            }
        }
        return -1;
    }

    public boolean checkItemExist(ArrayList<Order> orders, int item) {
        for (Order order : orders) {
            if (item == order.getFruitId()) {
                return true;
            }
        }
        return false;
    }

    public boolean checkOutOfStock() {
        for (Fruit fruit : fruits) {
            if (fruit.getQuantity() != 0) {
                return false;
            }
        }
        return true;
    }
}
