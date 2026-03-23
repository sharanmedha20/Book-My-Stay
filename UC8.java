import java.util.*;

class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public String toString() {
        return reservationId + " | " + guestName + " | " + roomType;
    }
}

class BookingHistory {
    private List<Reservation> history = new ArrayList<>();

    public void addReservation(Reservation reservation) {
        history.add(reservation);
    }

    public List<Reservation> getAllReservations() {
        return new ArrayList<>(history);
    }
}

class BookingReportService {
    public void printAllBookings(List<Reservation> reservations) {
        for (Reservation r : reservations) {
            System.out.println(r);
        }
    }

    public void printSummary(List<Reservation> reservations) {
        System.out.println("Total Bookings: " + reservations.size());
        Map<String, Integer> roomCount = new HashMap<>();
        for (Reservation r : reservations) {
            roomCount.put(r.getRoomType(), roomCount.getOrDefault(r.getRoomType(), 0) + 1);
        }
        for (String type : roomCount.keySet()) {
            System.out.println(type + ": " + roomCount.get(type));
        }
    }
}

public class UC8 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        System.out.print("Enter number of confirmed bookings: ");
        int n = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < n; i++) {
            System.out.print("Enter Reservation ID: ");
            String id = sc.nextLine();
            System.out.print("Enter Guest Name: ");
            String name = sc.nextLine();
            System.out.print("Enter Room Type: ");
            String room = sc.nextLine();

            Reservation reservation = new Reservation(id, name, room);
            history.addReservation(reservation);
        }

        System.out.println("\nAll Bookings:");
        reportService.printAllBookings(history.getAllReservations());

        System.out.println("\nBooking Summary:");
        reportService.printSummary(history.getAllReservations());
        sc.close();
    }
}