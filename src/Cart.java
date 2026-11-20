import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cart {
    public static class CartLine {
        private final Product product;
        private int quantity;

        private CartLine(Product product, int quantity) {
            this.product = product;
            this.quantity = quantity;
        }

        public Product getProduct() { 
            return product; 
        }

        public int getQuantity() { 
            return quantity; 
        }

        public double getLineTotal() {
            return product.getUnitPrice() * quantity;
        }
    }

    private final List<CartLine> lines = new ArrayList<>();

    //Adding 
    public void addItem(Product product, int quantity) {
        if (product == null) {
          throw new IllegalArgumentException("Invalid input. Try again.");
        } if (quantity < 1) {
          throw new IllegalArgumentException("Quantity must be at least 1.");
        } if (product.getQuantity() == 0) {
          throw new IllegalArgumentException("Sorry, " + product.getName() + " is out of stock.");
        }

        CartLine existing = findLine(product);
        int alreadyInCart = (existing == null) ? 0 : existing.quantity;

        //Stock check uses the cart quantity plus the new quantity together
        if (alreadyInCart + quantity > product.getQuantity()) {
          throw new IllegalArgumentException("Not enough stock for " + product.getName() + ".");
        } if (existing == null) {
          lines.add(new CartLine(product, quantity));
        } else {
            existing.quantity += quantity; //same item again: quantity adds up
        }
    }

    //Removing 
    //lineNumber is the number shown in the cart (starts at 1)
    public void removeItem(int lineNumber, int quantity) {
        if (lines.isEmpty()) {
            throw new IllegalArgumentException("Your cart is empty.");
        } if (lineNumber < 1 || lineNumber > lines.size()) {
          throw new IllegalArgumentException("Invalid input. Try again.");
        } if (quantity < 1) {
          throw new IllegalArgumentException("Quantity must be at least 1.");
        }

        CartLine line = lines.get(lineNumber - 1);
        if (quantity > line.quantity) {
          throw new IllegalArgumentException("You only have " + line.quantity + " in your cart.");
        } if (quantity == line.quantity) {
          lines.remove(lineNumber - 1); //whole quantity removed: the line disappears
        } else {
            line.quantity -= quantity;
        }
    }

    //Other operations
    public boolean isEmpty() {
        return lines.isEmpty();
    }

    public double getSubtotal() {
        double total = 0;
        for (CartLine line : lines) {
            total += line.getLineTotal();
        } return total;
    }

    public List<CartLine> getLines() {
        return Collections.unmodifiableList(lines); //read-only copy for others
    }

    public void clear() {
        lines.clear();
    }

    private CartLine findLine(Product product) {
        for (CartLine line : lines) {
          if (line.product.getId().equals(product.getId())) {
            return line;
        }
      } return null;
    }

    //Display
    @Override
    public String toString() {
        if (lines.isEmpty()) {
          return "Your cart is empty.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-4s %-20s %5s %9s %10s%n", "No.", "Item", "Qty", "Price", "Subtotal"));
        int number = 1;
        for (CartLine line : lines) {
          sb.append(String.format("%-4d %-20s %5d %9.2f %10.2f%n", number++, line.product.getName(), line.quantity, line.product.getUnitPrice(), line.getLineTotal()));
        } sb.append(String.format("Total: %.2f", getSubtotal()));
          return sb.toString();
    }
}