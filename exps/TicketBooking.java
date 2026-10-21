class TicketBooking {

    private int availableSeats;

    TicketBooking(int seats) {
        availableSeats = seats;
    }

    // synchronized method prevents double booking
    synchronized void bookTicket(String user, int seats) {

        System.out.println(user + " is trying to book "
                + seats + " seat(s).");

        if (availableSeats >= seats) {

            System.out.println(user + " booking in progress...");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }

            availableSeats -= seats;

            System.out.println(user
                    + " successfully booked "
                    + seats + " seat(s).");

            System.out.println("Seats remaining: "
                    + availableSeats);

        } else {

            System.out.println(user
                    + " booking failed. Not enough seats.");

        }

        System.out.println();
    }
}


class Customer extends Thread {

    TicketBooking booking;
    int seats;

    Customer(TicketBooking booking, String name, int seats) {
        super(name);
        this.booking = booking;
        this.seats = seats;
    }

    @Override
    public void run() {
        booking.bookTicket(getName(), seats);
    }
}


public class TicketBookingSystem {

    public static void main(String[] args) {

        TicketBooking booking = new TicketBooking(5);

        Customer normalUser =
                new Customer(booking, "Normal User", 2);

        Customer vipUser =
                new Customer(booking, "VIP User", 2);

        Customer anotherUser =
                new Customer(booking, "Another User", 2);

        // Set thread priorities
        normalUser.setPriority(Thread.NORM_PRIORITY);
        vipUser.setPriority(Thread.MAX_PRIORITY);
        anotherUser.setPriority(Thread.MIN_PRIORITY);

        // Start threads
        vipUser.start();
        normalUser.start();
        anotherUser.start();

        try {
            vipUser.join();
            normalUser.join();
            anotherUser.join();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("All booking processes completed.");
    }
}