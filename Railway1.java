import java.util.*;

class Train {
    String name;
    String route;
    boolean[] seats; 
    List<String> waitingList; // To store waiting list

    public Train(String name, String route) {
        this.name = name;
        this.route = route;
        this.seats = new boolean[100]; // 100 seats available initially
        Arrays.fill(this.seats, true); // All seats are initially available
        this.waitingList = new ArrayList<>();
    }

    // Display available seats for a given train
    public void displayAvailableSeats() {
        System.out.println("\nAvailable seats in " + this.name + " (" + this.route + "):");
        List<Integer> availableSeats = new ArrayList<>();
        
        // Collect the available seats
        for (int i = 0; i < seats.length; i++) {
            if (seats[i]) {
                availableSeats.add(i + 1); // Seat numbers are 1-based
            }
        }
        
        if (availableSeats.isEmpty()) {
            System.out.println("No available seats.");
            return;
        }

        // Display available seats in a readable format (10 per row)
        for (int i = 0; i < availableSeats.size(); i++) {
            System.out.print(availableSeats.get(i) + "\t");
            if ((i + 1) % 10 == 0) {
                System.out.println();
            }
        }
        System.out.println();
    }
}

class User {
    String username;
    String password;
    String email;
    Train bookedTrain;
    int seatNumber;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.bookedTrain = null;
        this.seatNumber = -1; // No seat booked initially
    }
}

public class Railway1{
    static List<Train> trains = new ArrayList<>();
    static Map<String, User> users = new HashMap<>();

    public static void main(String[] args) {
        initializeTrains();
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("***Welcome to Railway Reservation System***");
            System.out.println("\n1. Register\n2. Login\n3. Forgot Password\n4. Exit");
            System.out.print("Select your option:");
            int choice = sc.nextInt();
            sc.nextLine(); 
            switch (choice) {
                case 1:
                    register(sc);
                    break;
                case 2:
                    login(sc);
                    break;
                case 3:
                    forgotPassword(sc);
                    break;
                case 4:
                    System.out.println("Exiting...");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    public static void initializeTrains() {
        trains.add(new Train("Vande Bharat", "Secunderabad to Tirupathi"));
        trains.add(new Train("Krishna express", "Secunderabad to Tirupathi"));
        trains.add(new Train("Sabari Express", "Secunderabad to Tirupathi"));
        trains.add(new Train("Rayalaseema Express", "Secunderabad to Tirupathi"));
        trains.add(new Train("Venkatadri Express", "Secunderabad to Tirupathi"));
    }

    public static void register(Scanner sc) {
        System.out.print("Enter username: ");
        String username = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();
        System.out.print("Enter email: ");
        String email = sc.nextLine();
        if (users.containsKey(username)) {
            System.out.println("Username already exists.");
        } else {
            users.put(username, new User(username, password, email));
            System.out.println("Registration successful.");
        }
    }

    public static void login(Scanner sc) {
        System.out.print("Enter username: ");
        String username = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();
        if (users.containsKey(username) && users.get(username).password.equals(password)) {
            System.out.println("Login successful.");
            User user = users.get(username);
            userMenu(sc, user);
        } else {
            System.out.println("Invalid credentials.");
        }
    }

    public static void forgotPassword(Scanner sc) {
        System.out.print("Enter username: ");
        String username = sc.nextLine();
        System.out.print("Enter email: ");
        String email = sc.nextLine();
        if (users.containsKey(username) && users.get(username).email.equals(email)) {
            System.out.println("Your password is: " + users.get(username).password);
        } else {
            System.out.println("Invalid username or email.");
        }
    }

    public static void userMenu(Scanner sc, User user) {
        while (true) {
            System.out.println("\n1. Book Seat\n2. Cancel Booking\n3. Check Seat Availability\n4. Logout");
            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1) {
                bookSeat(sc, user);
            } else if (choice == 2) {
                cancelBooking(sc, user);
            } else if (choice == 3) {
                checkSeatAvailability();
            } else if (choice == 4) {
                break;
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }

    public static void bookSeat(Scanner sc, User user) {
        if (user.bookedTrain != null) {
            System.out.println("You have already booked a seat in " + user.bookedTrain.name + ".");
            return;
        }

        System.out.println("Available trains:");
        List<Train> availableTrains = new ArrayList<>();
        for (Train train : trains) {
            boolean hasAvailableSeat = false;
            for (boolean seat : train.seats) {
                if (seat) {
                    hasAvailableSeat = true;
                    break;
                }
            }
            if (hasAvailableSeat) {
                availableTrains.add(train);
            }
        }

        if (availableTrains.isEmpty()) {
            System.out.println("No available seats.");
            return;
        }

        for (int i = 0; i < availableTrains.size(); i++) {
            System.out.println((i + 1) + ". " + availableTrains.get(i).name);
        }

        System.out.print("Select train (1-" + availableTrains.size() + "): ");
        int choice = sc.nextInt();
        sc.nextLine();

        if (choice >= 1 && choice <= availableTrains.size()) {
            Train selectedTrain = availableTrains.get(choice - 1);

            selectedTrain.displayAvailableSeats(); // Show available seats

            System.out.print("Select seat (Enter seat number): ");
            int seatChoice = sc.nextInt();
            sc.nextLine();

            if (seatChoice >= 1 && seatChoice <= 100 && selectedTrain.seats[seatChoice - 1]) {
                selectedTrain.seats[seatChoice - 1] = false; // Seat booked
                user.bookedTrain = selectedTrain;
                user.seatNumber = seatChoice; // Assign the seat number (1-based)
                System.out.println("Seat booked successfully in " + selectedTrain.name + ". Seat Number: " + user.seatNumber);
            } else {
                System.out.println("Invalid or already booked seat.");
            }
        } else {
            System.out.println("Invalid train selection.");
        }
    }

    public static void cancelBooking(Scanner sc, User user) {
        if (user.bookedTrain == null) {
            System.out.println("You have no booking to cancel.");
            return;
        }

        Train bookedTrain = user.bookedTrain;
        bookedTrain.seats[user.seatNumber - 1] = true; // Free up the seat
        System.out.println("Booking canceled for " + bookedTrain.name + ". Seat Number: " + user.seatNumber);

        // Check if anyone is on the waiting list
        if (!bookedTrain.waitingList.isEmpty()) {
            String nextUserInWaitingList = bookedTrain.waitingList.remove(0);
            User waitingUser = users.get(nextUserInWaitingList);
            waitingUser.bookedTrain = bookedTrain;
            waitingUser.seatNumber = user.seatNumber;
            System.out.println("Seat allocated to waiting user: " + nextUserInWaitingList + ". Seat Number: " + user.seatNumber);
        }

        // Reset user booking information
        user.bookedTrain = null;
        user.seatNumber = -1;
    }

    public static void checkSeatAvailability() {
        System.out.println("Train Availability:");
        for (Train train : trains) {
            int availableSeats = 0;
            for (boolean seat : train.seats) {
                if (seat) {
                    availableSeats++;
                }
            }
            System.out.println(train.name + " - Available Seats: " + availableSeats);
        }
    }
}