package com.pos.app.model;

import javafx.beans.property.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class Location {
    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty name = new SimpleStringProperty();
    private BooleanProperty deleted = new SimpleBooleanProperty();

    public Location(int id, String name, boolean deleted) {
        this.id.set(id);
        this.name.set(name);
        this.deleted.set(deleted);
    }

}

