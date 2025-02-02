package com.pos.app.controller;

import com.pos.app.model.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

// Controller dung cho items view
public class ItemsController {
    @FXML
    public TableView<Item> itemsTable;

    public void initialize() {
        // Thêm dữ liệu vào bảng
        ObservableList<Item> people = FXCollections.observableArrayList(
                Item.builder()
                        .id(1)
                        .itemNumber("8982323213")
                        .name("bim bim")
                        .category("Thực phẩm")
                        .supplier("Coca Cola")
                        .wholesalePrice(5000)
                        .retailPrice(5000)
                        .quantity(5)
                        .taxPercent(0.1)
                        .avatar("abc.jpg")
                        .build(),
                Item.builder().id(2).name("kẹo").build()
        );
        itemsTable.setItems(people);
    }
}