package com.pos.app.model;

import javafx.beans.property.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Inventory {
    private IntegerProperty id = new SimpleIntegerProperty(0);
    private IntegerProperty item = new SimpleIntegerProperty(0);
    private ObjectProperty<LocalDateTime> timestamp = new SimpleObjectProperty<>(LocalDateTime.now());
    private StringProperty comment = new SimpleStringProperty("");
    private IntegerProperty changedQuantity = new SimpleIntegerProperty(0); // Số lượng cập nhật
    private IntegerProperty afterQuantity = new SimpleIntegerProperty(0); // Số lượng tồn sau khi cập nhật

    public Inventory(int id, int item, LocalDateTime timestamp, String location, int change,
            int afterQuantity, String comment) {
        this.id.set(id);
        this.item.set(item);
        this.timestamp.set(timestamp);
        this.comment.set(comment);
        this.changedQuantity.set(change);
        this.afterQuantity.set(afterQuantity);
    }
}
