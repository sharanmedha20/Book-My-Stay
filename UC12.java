import java.io.*;
import java.util.*;

class Booking implements Serializable {
    int id;
    String name;

    Booking(int id, String name) {
        this.id = id;
        this.name = name;
    }
}

class Inventory implements Serializable {
    int items;

    Inventory(int items) {
        this.items = items;
    }
}

class DataStore implements Serializable {
    List<Booking> bookings;
    Inventory inventory;

    DataStore(List<Booking> bookings, Inventory inventory) {
        this.bookings = bookings;
        this.inventory = inventory;
    }
}

public class Main {
    static final String FILE = "data.ser";

    public static void save(DataStore data) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE));
            oos.writeObject(data);
            oos.close();
        } catch (Exception e) {
        }
    }

    public static DataStore load() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE));
            DataStore data = (DataStore) ois.readObject();
            ois.close();
            return data;
        } catch (Exception e) {
            return new DataStore(new ArrayList<>(), new Inventory(0));
        }
    }

    public static void main(String[] args) {
        DataStore data = load();

        data.bookings.add(new Booking(1, "John"));
        data.inventory.items += 10;

        save(data);

        System.out.println("Bookings: " + data.bookings.size());
        System.out.println("Inventory: " + data.inventory.items);
    }
}
