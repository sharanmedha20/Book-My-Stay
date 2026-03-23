import java.util.*;

class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

class RoomInventory {
    private Map<String, Integer> rooms = new HashMap<>();

    public RoomInventory() {
        rooms.put("Single", 5);
        rooms.put("Double", 3);
        rooms.put("Suite", 2);
    }

    public void validateRoomType(String type) throws InvalidBookingException {
        if (!rooms.containsKey(type)) {
            throw new InvalidBookingException("Invalid room type: " + type);
        }
    }

    public void validateAvailability(String type) throws InvalidBookingException {
        if (rooms.get(type) <= 0) {
            throw new InvalidBookingException("No rooms available for type: " + type);
        }
    }

    public void bookRoom(String type) {
        rooms.put(type, rooms.get(type) - 1);
    }

    public void displayInventory() {
        System.out.println("Current Inventory:");
        for (String key : rooms.keySet()) {
            System.out.println(key + ": " + rooms.get(key));
        }
    }
}

class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String toString() {
        return reservationId + " | " + guestName + " | " + roomType;
    }
}

class InvalidBookingValidator {
    public void validate(String reservationId, String guestName, String roomType, RoomInventory inventory) throws InvalidBookingException {
        if (reservationId == null || reservationId.trim().isEmpty()) {
            throw new InvalidBookingException("Reservation ID cannot be empty");
        }
        if (guestName == null || guestName.trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty");
        }
        inventory.validateRoomType(roomType);
        inventory.validateAvailability(roomType);
    }
}

public class UC9 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        RoomInventory inventory = new RoomInventory();
        InvalidBookingValidator validator = new InvalidBookingValidator();

        System.out.print("Enter Reservation ID: ");
        String id = sc.nextLine();
        System.out.print("Enter Guest Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Room Type (Single/Double/Suite): ");
        String type = sc.nextLine();

        try {
            validator.validate(id, name, type, inventory);
            inventory.bookRoom(type);
            Reservation reservation = new Reservation(id, name, type);
            System.out.println("Booking Successful: " + reservation);
        } catch (InvalidBookingException e) {
            System.out.println("Booking Failed: " + e.getMessage());
        }

        System.out.println();
        inventory.displayInventory();
        sc.close();
    }
}