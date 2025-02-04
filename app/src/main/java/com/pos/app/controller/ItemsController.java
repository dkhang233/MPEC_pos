package com.pos.app.controller;

import com.pos.app.model.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

// Controller dung cho items view
public class ItemsController {
    @FXML
    public TableView<Item> itemsTable;

    @FXML
    public TableColumn<Item, String> avatarColumn;

    @FXML
    public TableColumn<Item, String> updateStock;

    @FXML
    public TableColumn<Item, String> stockHistory;

    @FXML
    public TableColumn<Item, String> updateItem;
    
    private final String defaultAvatar = Objects.requireNonNull(getClass().getClassLoader().getResource("static/default-item.png")).toExternalForm(); // Avatar mặc định
    
    public void initialize() {
        // Tùy chỉnh cột avatar để hiển thị hỉnh ảnh thay vì text
        avatarColumn.setCellFactory(col -> new TableCell<Item, String>() {
            private final ImageView imageView = new ImageView();
            @Override
            protected void updateItem(String avatar, boolean empty) {
                super.updateItem(avatar, empty);
                if (empty || avatar == null) {
                    setGraphic(null);
                } else {
                    try {
                        imageView.setImage(new Image(avatar, true));
                    } catch (Exception e) {
                        imageView.setImage(new Image(defaultAvatar));
                    }
                    imageView.setFitWidth(50);
                    imageView.setFitHeight(50);
                    setGraphic(imageView);
                }
            }
        });

        // Tùy chỉnh cột update stock để hiển thị button thay vì text
        updateStock.setCellFactory(col -> new TableCell<Item, String>() {
            final Button updateStockBtn = new Button("Update stock");

            {
                updateStockBtn.getStyleClass().addAll("btn-custom");
                updateStockBtn.setOnAction(event -> {
                    Item item = getTableView().getItems().get(getIndex());
                    System.out.println("Update stock for item has id: " + item.getId());
                });
            }

            @Override
            protected void updateItem(String cell, boolean empty) {
                super.updateItem(cell, empty);
                if(super.isEmpty())
                    setGraphic(null);
                else
                    setGraphic(updateStockBtn);
            }
        });

        // Tùy chỉnh cột stock history để hiển thị button thay vì text
        stockHistory.setCellFactory(col -> new TableCell<Item, String>() {
            final Button stockHistoryBtn = new Button("Stock history");

            {
                stockHistoryBtn.getStyleClass().addAll("btn-custom");
                stockHistoryBtn.setOnAction(event -> {
                    Item item = getTableView().getItems().get(getIndex());
                    System.out.println("Display stock history for id: " + item.getId());
                });
            }
            
            @Override
            protected void updateItem(String cell, boolean empty) {
                super.updateItem(cell, empty);
                if(super.isEmpty())
                    setGraphic(null);
                else
                    setGraphic(stockHistoryBtn);
            }
        });

        // Tùy chỉnh cột update item để hiển thị button thay vì text
        updateItem.setCellFactory(col -> new TableCell<Item, String>() {
            final Button updateItemBtn = new Button("Update item");

            {
                updateItemBtn.getStyleClass().addAll("btn-custom");
                updateItemBtn.setOnAction(event -> {
                    Item item = getTableView().getItems().get(getIndex());
                    System.out.println("Update item for id: " + item.getId());
                });
            }

            @Override
            protected void updateItem(String cell, boolean empty) {
                super.updateItem(cell, empty);
                if(super.isEmpty())
                    setGraphic(null);
                else
                    setGraphic(updateItemBtn);
            }
        });
        
        // Thêm dữ liệu vào bảng
        ObservableList<Item> items = FXCollections.observableArrayList(
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
                        .avatar("a-woman-working-on-a-laptop-6uAssP0vuPs")
                        .build(),
                Item.builder()
                        .id(1)
                        .itemNumber("8982323213")
                        .name("bim bim")
                        .category("Thực phẩm siêu sạch đem từ Mỹ về, không chứa chất bảo quản, không chất tạo màu")
                        .supplier("Coca Cola")
                        .wholesalePrice(5000)
                        .retailPrice(5000)
                        .quantity(5)
                        .taxPercent(0.1)
                        .avatar("a-woman-working-on-a-laptop-6uAssP0vuPs")
                        .build()
        );
        itemsTable.setItems(items);
    }
}