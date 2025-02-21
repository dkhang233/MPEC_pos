package com.pos.app.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ItemQuantity {
    private IntegerProperty itemId = new SimpleIntegerProperty();

    private StringProperty locationName  = new SimpleStringProperty();

    private IntegerProperty quantity =  new SimpleIntegerProperty();

    public ItemQuantity(int itemId, String locationName, int quantity) {
        this.itemId.set(itemId);
        this.locationName.set(locationName);
        this.quantity.set(quantity);
    }
}
