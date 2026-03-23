import java.util.*;

class Reservation {
    private String reservationId;
    private String roomType;
    private String roomId;
    private boolean active;

    public Reservation(String reservationId, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.roomType = roomType;
        this.roomId = roomId;
        this.active = true;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }

    public boolean isActive() {
        return active;
    }

    public void cancel() {
        active = false;
    }

    public String toString() {
        return reservationId + " | " + roomType + " | " + roomId + " | " + (active ? "ACTIVE" : "CANCELLED");
    }
}

class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single", 2);
        inventory.put("Double", 2);
    }

    public void increment(String type) {
        inventory.put(type, inventory.getOrDefault(type, 0) + 1);
    }

    public void decrement(String type) {
        inventory.put(type, inventory.get(type) - 1);
    }

    public void display() {
        System.out.println("Inventory:");
        for (String key : inventory.keySet()) {
            System.out.println(key + ": " + inventory.get(key));
        }
    }
}

class BookingStore {
    private Map<String, Reservation> reservations = new HashMap<>();

    public void add(Reservation r) {
        reservations.put(r.getReservationId(), r);
    }

    public Reservation get(String id) {
        return reservations.get(id);
    }

    public Collection<Reservation> getAll() {
        return reservations.values();
    }
}

class CancellationService {
    private Stack<String> rollbackStack = new Stack<>();

    public void cancel(String reservationId, BookingStore store, RoomInventory inventory) {
        Reservation r = store.get(reservationId);

        if (r == null) {
            System.out.println("Cancellation Failed: Reservation does not exist");
            return;
        }

        if (!r.isActive()) {
            System.out.println("Cancellation Failed: Already cancelled");
            return;
        }

        rollbackStack.push(r.getRoomId());
        inventory.increment(r.getRoomType());
        r.cancel();

        System.out.println("Cancellation Successful for ID: " + reservationId);
    }

    public void showRollbackStack() {
        System.out.println("Rollback Stack (Recent Releases): " + rollbackStack);
    }
}

public class UC10 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        RoomInventory inventory = new RoomInventory();
        BookingStore store = new BookingStore();
        CancellationService service = new CancellationService();

        store.add(new Reservation("R1", "Single", "S101"));
        store.add(new Reservation("R2", "Double", "D201"));

        System.out.print("Enter Reservation ID to cancel: ");
        String id = sc.nextLine();

        service.cancel(id, store, inventory);

        System.out.println();
        inventory.display();

        System.out.println("\nAll Reservations:");
        for (Reservation r : store.getAll()) {
            System.out.println(r);
        }

        System.out.println();
        service.showRollbackStack();
        sc.close();
    }
}