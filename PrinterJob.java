package task2;

class Printer {
    private int availablePages = 10;

    // Method to add pages to the printer tray
    public synchronized void addPages(int pages) {
        availablePages += pages;
        System.out.println("Added " + pages + " pages to the tray. Available pages: " + availablePages);
        notify(); // Notify waiting thread
    }

    // Method to print pages
    public synchronized void printPages(int pages) {
        while (availablePages < pages) {
            System.out.println("Not enough pages to print. Waiting for pages to be added...");
            try {
                wait(); // Wait until pages are available
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Thread interrupted");
            }
        }
        availablePages -= pages;
        System.out.println("Printed " + pages + " pages. Remaining pages: " + availablePages);
    }
}

public class PrinterJob {
    public static void main(String[] args) {
        Printer printer = new Printer();

        // Thread for adding pages to the printer tray
        Thread pageAdder = new Thread(() -> {
            try {
                Thread.sleep(2000); // Simulate delay
                printer.addPages(10); // Add 10 pages
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Thread for printing pages
        Thread printJob = new Thread(() -> {
            printer.printPages(15); // Request to print 15 pages
        });

        // Start threads
        printJob.start();
        pageAdder.start();
    }
}
