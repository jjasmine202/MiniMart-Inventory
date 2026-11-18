import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class FoodItem extends Product {

    private String expiryDate; // stored as text in YYYY-MM-DD format

    public FoodItem(String id, String name, double unitPrice, int quantity, int reorderLevel, String expiryDate) {
        super(id, name, unitPrice, quantity, reorderLevel); // runs Product's constructor
        setExpiryDate(expiryDate);
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        if (expiryDate == null) {
          throw new IllegalArgumentException("Expiry date cannot be blank.");
        } try {
            LocalDate.parse(expiryDate.trim()); // checks the YYYY-MM-DD format
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Expiry date must be in YYYY-MM-DD format.");
        } this.expiryDate = expiryDate.trim();
    }

    @Override
    public String getCategory() {
        return "Food";
    }

    @Override
    public String getCategoryDetails() {
        return "Expiry: " + expiryDate;
    }

    @Override
    public String getRestockAdvice() {
        if (LocalDate.parse(expiryDate).isBefore(LocalDate.now())) {
            return "Expired! Remove from shelf.";
        } if (isLowStock()) {
            return "Restock soon. Order small batches and check expiry dates.";
        } return "Stock is fine. Check expiry dates regularly.";
    }
}