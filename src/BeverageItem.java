public class BeverageItem extends Product {

    private int volumeMl;

    public BeverageItem(String id, String name, double unitPrice, int quantity, int reorderLevel, int volumeMl) {
        super(id, name, unitPrice, quantity, reorderLevel);
        setVolumeMl(volumeMl);
    }

    public int getVolumeMl() {
        return volumeMl;
    }

    public void setVolumeMl(int volumeMl) {
        if (volumeMl <= 0) {
            throw new IllegalArgumentException("Volume must be greater than 0.");
        }
        this.volumeMl = volumeMl;
    }

    @Override
    public String getCategory() {
        return "Beverage";
    }

    @Override
    public String getCategoryDetails() {
        return "Volume: " + volumeMl + " ml";
    }

    @Override
    public String getRestockAdvice() {
        if (isLowStock()) {
            return "Restock soon. Beverages sell fast, order in cases.";
        }
        return "Stock is fine. Keep cold drinks near the front.";
    }
}