package BookMyStay;

import java.util.*;

class AddOnService {
    private String name;
    private double cost;

    public AddOnService(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public double getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }
}

class AddOnServiceManager {
    private Map<String, List<AddOnService>> reservationServices = new HashMap<>();

    public void addService(String reservationId, AddOnService service) {
        reservationServices.computeIfAbsent(reservationId, k -> new ArrayList<>()).add(service);
    }

    public List<AddOnService> getServices(String reservationId) {
        return reservationServices.getOrDefault(reservationId, new ArrayList<>());
    }

    public double calculateTotalCost(String reservationId) {
        double total = 0;
        for (AddOnService service : getServices(reservationId)) {
            total += service.getCost();
        }
        return total;
    }
}

public class UC7 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AddOnServiceManager manager = new AddOnServiceManager();

        System.out.print("Enter Reservation ID: ");
        String reservationId = sc.nextLine();

        System.out.print("Enter number of services: ");
        int n = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < n; i++) {
            System.out.print("Enter service name: ");
            String name = sc.nextLine();
            System.out.print("Enter service cost: ");
            double cost = sc.nextDouble();
            sc.nextLine();
            manager.addService(reservationId, new AddOnService(name, cost));
        }

        System.out.println("Selected Services:");
        for (AddOnService s : manager.getServices(reservationId)) {
            System.out.println(s.getName() + " - " + s.getCost());
        }

        System.out.println("Total Additional Cost: " + manager.calculateTotalCost(reservationId));
        sc.close();
    }
}