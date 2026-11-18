public abstract class Product {
    private final String id;
    private String name;
    private double unitPrice;
    private int quantity;
    private int reorderLevel;

    public Product(String id, String name, double unitPrice, int quantity, int reorderLevel) {
        if (id == null || id.trim().isEmpty()) {
          throw new IllegalArgumentException("Product ID cannot be blank.");
        } this.id = id.trim();
        
        setName(name);
        setUnitPrice(unitPrice);
        setQuantity(quantity);
        setReorderLevel(reorderLevel);
    }

    public String getId() { 
        return id; 
    }

    public String getName() { 
        return name;
    }

    public double getUnitPrice() { 
        return unitPrice; 
    }

    public int getQuantity() { 
        return quantity; 
    }

    public int getReorderLevel() { 
        return reorderLevel; 
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
          throw new IllegalArgumentException("Name cannot be blank.");
        } this.name = name.trim();
    }

    public void setUnitPrice(double unitPrice) {
        if (unitPrice <= 0) {
          throw new IllegalArgumentException("Price must be greater than 0.");
        } this.unitPrice = unitPrice;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
          throw new IllegalArgumentException("Quantity cannot be negative.");
        } this.quantity = quantity;
    }

    public void setReorderLevel(int reorderLevel) {
        if (reorderLevel < 0) {
          throw new IllegalArgumentException("Reorder level cannot be negative.");
        } this.reorderLevel = reorderLevel;
    }

    public void addStock(int amount) {
        if (amount < 1) {
          throw new IllegalArgumentException("Amount must be at least 1.");
        } this.quantity += amount;
    }

    public void removeStock(int amount) {
        if (amount < 1) {
          throw new IllegalArgumentException("Amount must be at least 1.");
        } if (amount > quantity) {
          throw new IllegalArgumentException("Not enough stock for " + name + ".");
        } this.quantity -= amount;
    }

    public boolean isLowStock() {
        return quantity <= reorderLevel;
    }

    public double getTotalValue() {
        return unitPrice * quantity;
    }

    public abstract String getCategory();
    public abstract String getCategoryDetails();
    public abstract String getRestockAdvice();
}
