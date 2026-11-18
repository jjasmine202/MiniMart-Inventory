public class ToiletryItem extends Product {

    private String brand;

    public ToiletryItem(String id, String name, double unitPrice, int quantity, int reorderLevel, String brand) {
        super(id, name, unitPrice, quantity, reorderLevel);
        setBrand(brand);
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        if (brand == null || brand.trim().isEmpty()) {
          throw new IllegalArgumentException("Brand cannot be blank.");
        } this.brand = brand.trim();
    }

    @Override
    public String getCategory() {
        return "Toiletries";
    }

    @Override
    public String getCategoryDetails() {
        return "Brand: " + brand;
    }

    @Override
    public String getRestockAdvice() {
        if (isLowStock()) {
          return "Restock soon. Reorder from the same brand supplier.";
        } return "Stock is fine. Toiletries do not expire quickly.";
    }
}