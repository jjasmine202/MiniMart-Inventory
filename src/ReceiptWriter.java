import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReceiptWriter {

    private static final int WIDTH = 44;
    private static final String FOLDER = "receipts";
    private static final DateTimeFormatter FILE_TIME = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
    private static final DateTimeFormatter SHOWN_TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final String storeName;

    public ReceiptWriter() {
        this("Mini Mart");
    }

    public ReceiptWriter(String storeName) {
        if (storeName == null || storeName.trim().isEmpty()) {
          throw new IllegalArgumentException("Store name cannot be blank.");
        } this.storeName = storeName.trim();
    }

    public String printAndSave(Cart cart, double discountAmount, double cash) {
        LocalDateTime now = LocalDateTime.now(); //computer's clock
        String receipt = buildReceipt(cart, discountAmount, cash, now);
        System.out.println(receipt);
        try {
            return saveToFile(receipt, now);
        } catch (IOException e) {
            System.out.println("Receipt could not be saved to a file: " + e.getMessage());
            return null;
        }
    }

    public String buildReceipt(Cart cart, double discountAmount, double cash, LocalDateTime when) {
        if (cart == null || cart.isEmpty()) {
          throw new IllegalArgumentException("Your cart is empty.");
        } 
        
        if (Double.isNaN(cash) || Double.isInfinite(cash) || cash < 0) {
            throw new IllegalArgumentException("Invalid input. Try again.");
        }

        double subtotal = round2(cart.getSubtotal());
        double discount = round2(discountAmount);
        if (discount < 0 || discount > subtotal) {
          throw new IllegalArgumentException("Invalid discount.");
        }

        double total = round2(subtotal - discount);
        double cashGiven = round2(cash);
        if (cashGiven < total) {
          throw new IllegalArgumentException("Not enough cash. Try again.");
        }

        double change = round2(cashGiven - total);

        StringBuilder sb = new StringBuilder();
        sb.append(repeat('=', WIDTH)).append(System.lineSeparator());
        sb.append(center(storeName)).append(System.lineSeparator());
        sb.append(repeat('=', WIDTH)).append(System.lineSeparator());
        sb.append("Date: ").append(when.format(SHOWN_TIME)).append(System.lineSeparator());
        sb.append(repeat('-', WIDTH)).append(System.lineSeparator());
        sb.append(String.format("%-18s %4s %9s %10s%n", "Item", "Qty", "Price", "Subtotal"));

        for (Cart.CartLine line : cart.getLines()) {
          sb.append(String.format("%-18s %4d %9.2f %10.2f%n", fit(line.getProduct().getName(), 18), line.getQuantity(), line.getProduct().getUnitPrice(), line.getLineTotal()));
        }

        sb.append(repeat('-', WIDTH)).append(System.lineSeparator());
        sb.append(summaryLine("Subtotal:", money(subtotal)));
        sb.append(summaryLine("Loyalty discount:", discount > 0 ? "-" + money(discount) : money(0)));
        sb.append(summaryLine("TOTAL:", money(total)));
        sb.append(summaryLine("Cash:", money(cashGiven)));
        sb.append(summaryLine("Change:", money(change)));
        sb.append(repeat('=', WIDTH)).append(System.lineSeparator());
        sb.append(center("Thank you! Come again!")).append(System.lineSeparator());
        return sb.toString();
    }

    //Saving
    private String saveToFile(String text, LocalDateTime when) throws IOException {
        File folder = new File(FOLDER);
        if (!folder.exists() && !folder.mkdirs()) { //create receipts/ if missing
          throw new IOException("Could not create the " + FOLDER + " folder.");
        }

        String baseName = "receipt_" + when.format(FILE_TIME);
        File file = new File(folder, baseName + ".txt");


        int copy = 1;
        while (file.exists()) {
            file = new File(folder, baseName + "_" + copy + ".txt");
            copy++;
        }

        try (FileWriter writer = new FileWriter(file)) { //automatically closes
          writer.write(text);
        } return file.getPath();
    }

    //Small helpers
    private String summaryLine(String label, String amount) {
        return String.format("%-33s %10s%n", label, amount);
    }

    private String money(double value) {
        return String.format("%.2f", value);
    }

    private double round2(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    private String fit(String text, int max) {
        return text.length() <= max ? text : text.substring(0, max - 1) + ".";
    }

    private String center(String text) {
        int padding = Math.max(0, (WIDTH - text.length()) / 2);
        return repeat(' ', padding) + text;
    }

    private String repeat(char c, int times) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < times; i++) {
          sb.append(c);
        } return sb.toString();
    }
}