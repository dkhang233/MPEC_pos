package com.pos.app.model;

import javafx.beans.property.*;
import lombok.Data;

@Data
public class Sales {
    private final IntegerProperty id;
    private final StringProperty barcode;
    private final StringProperty name;
    private final StringProperty category;
    private final StringProperty supplier;
    private final DoubleProperty wholesalePrice;
    private final DoubleProperty retailPrice;
    private final IntegerProperty quantity;
    private final DoubleProperty total;
    private final StringProperty avatar;

    public Sales(int id, String barcode, String name, String category, String supplier,
            double wholesalePrice, double retailPrice, int quantity, String avatar) {
        this.id = new SimpleIntegerProperty(id);
        this.barcode = new SimpleStringProperty(barcode);
        this.name = new SimpleStringProperty(name);
        this.category = new SimpleStringProperty(category);
        this.supplier = new SimpleStringProperty(supplier);
        this.wholesalePrice = new SimpleDoubleProperty(wholesalePrice);
        this.retailPrice = new SimpleDoubleProperty(retailPrice);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.avatar = new SimpleStringProperty(avatar);
        this.total = new SimpleDoubleProperty(quantity * retailPrice);

        // Update total
        this.quantity
                .addListener((obs, oldVal, newVal) -> this.total.set(newVal.intValue() * this.retailPrice.get()));
        this.retailPrice
                .addListener((obs, oldVal, newVal) -> this.total.set(this.quantity.get() * newVal.doubleValue()));
    }

    // Getters các thuộc tính
    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty barcodeProperty() {
        return barcode;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty categoryProperty() {
        return category;
    }

    public StringProperty supplierProperty() {
        return supplier;
    }

    public DoubleProperty wholesalePriceProperty() {
        return wholesalePrice;
    }

    public DoubleProperty retailPriceProperty() {
        return retailPrice;
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }

    public DoubleProperty totalProperty() {
        return total;
    }

    public StringProperty avatarProperty() {
        return avatar;
    }

    // Getters các giá trị
    public int getId() {
        return id.get();
    }

    public String getBarcode() {
        return barcode.get();
    }

    public String getName() {
        return name.get();
    }

    public String getCategory() {
        return category.get();
    }

    public String getSupplier() {
        return supplier.get();
    }

    public double getWholesalePrice() {
        return wholesalePrice.get();
    }

    public double getRetailPrice() {
        return retailPrice.get();
    }

    public int getQuantity() {
        return quantity.get();
    }

    public double getTotal() {
        return total.get();
    }

    public String getAvatar() {
        return avatar.get();
    }

    // Setters
    public void setId(int id) {
        this.id.set(id);
    }

    public void setBarcode(String barcode) {
        this.barcode.set(barcode);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setCategory(String category) {
        this.category.set(category);
    }

    public void setSupplier(String supplier) {
        this.supplier.set(supplier);
    }

    public void setWholesalePrice(double wholesalePrice) {
        this.wholesalePrice.set(wholesalePrice);
    }

    public void setRetailPrice(double retailPrice) {
        this.retailPrice.set(retailPrice);
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public void setTotal(double total) {
        this.total.set(total);
    }

    public void setAvatar(String avatar) {
        this.avatar.set(avatar);
    }

}