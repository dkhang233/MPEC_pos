package com.pos.app.model;

import javafx.beans.property.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Inventory {
    private IntegerProperty id = new SimpleIntegerProperty(0);
    private IntegerProperty item = new SimpleIntegerProperty(0);
    private StringProperty username = new SimpleStringProperty("");
    private ObjectProperty<LocalDateTime> timestamp  = new SimpleObjectProperty<>(LocalDateTime.now());
    private StringProperty location = new SimpleStringProperty("");
    private StringProperty comment = new SimpleStringProperty("");
    private IntegerProperty inventory = new SimpleIntegerProperty(0);  // Số lượng cập nhật
    private IntegerProperty afterInventory = new SimpleIntegerProperty(0); // Số lượng tồn sau khi cập nhật

    public Inventory(int id, int item, String username, LocalDateTime timestamp, String location, int inventory,  int afterInventory, String comment) {
        this.id.set(id);
        this.item.set(item);
        this.username.set(username);
        this.timestamp.set(timestamp);
        this.comment.set(comment);
        this.location.set(location);
        this.inventory.set(inventory);
        this.afterInventory.set(afterInventory);
    }
}
