package com.pos.app.model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Data;

@Data
public class BindingUpdateInventory {
    private final StringProperty barcode = new SimpleStringProperty();
    private final StringProperty itemName = new SimpleStringProperty();
    private final StringProperty category = new SimpleStringProperty();
    private final ListProperty<String> stockLocation = new SimpleListProperty<>(FXCollections.observableArrayList());
    private final ObjectProperty<String> selectedStockLocation = new SimpleObjectProperty<>();
    private final IntegerProperty currentQuantity = new SimpleIntegerProperty();
    private final IntegerProperty inventoryToAddOrSubtract = new SimpleIntegerProperty();
    private final StringProperty comment = new SimpleStringProperty("");
    private final ObservableList<Location> locations = FXCollections.observableArrayList();

public Inventory mapToUpdateInventory(Item item) {
    String selectedLocation = selectedStockLocation.get();

    // Tìm ItemQuantity theo location, nếu không tìm thấy thì khởi tạo với location đã chọn
    ItemQuantity itemQuantity = item.getQuantityPerLocation().stream()
            .filter(quantity -> quantity.getLocationName().equals(selectedLocation))
            .findFirst()
            .orElse(new ItemQuantity(0, selectedLocation, 0));

    // Cập nhật số lượng
    itemQuantity.setQuantity(itemQuantity.getQuantity() + inventoryToAddOrSubtract.get());

    // Loại bỏ các ItemQuantity cũ theo location
    item.getQuantityPerLocation().removeIf(quantity -> quantity.getLocationName().equals(selectedLocation));

    // Thêm đối tượng đã cập nhật vào danh sách
    item.getQuantityPerLocation().add(itemQuantity);

    // Xây dựng đối tượng Inventory từ location và các thông tin khác
    Location location = locations.stream()
            .filter(loc -> loc.getName().equals(selectedLocation))
            .findFirst()
            .orElse(new Location(0, "", false));

    return Inventory.builder()
            .location(location)
            .inventory(inventoryToAddOrSubtract.get())
            .comment(comment.get())
            .build();
}

}
