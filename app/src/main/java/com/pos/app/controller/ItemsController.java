package com.pos.app.controller;

import com.pos.app.model.Item;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Pair;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

// Controller dung cho items view
public class ItemsController {
    @FXML
    private TableView<Item> itemsTable;

    @FXML
    private TableColumn<Item, String> avatarCol;

    @FXML
    private TableColumn<Item, String> updateStockCol;

    @FXML
    private TableColumn<Item, String> stockHistoryCol;

    @FXML
    private TableColumn<Item, String> updateItemCol;

    private final String defaultAvatar = Objects.requireNonNull(getClass().getClassLoader().getResource("static/default-item.png")).toExternalForm(); // Avatar mặc định

    @FXML
    private Button deleteItemBtn;

    @FXML
    private Pagination itemsPagination;

    @FXML
    private void createItem(){
        // Tạo Dialog
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Nhập Tên");
        dialog.setHeaderText("Vui lòng nhập tên của bạn:");

// Tạo ô nhập dữ liệu
        TextField textField = new TextField();
        dialog.getDialogPane().setContent(textField);

// Thêm nút OK và CANCEL
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

// Xử lý kết quả khi nhấn OK
        dialog.setResultConverter(button -> {
            if (button == ButtonType.OK) {
                return textField.getText(); // Trả về giá trị nhập vào
            }
            return null; // Nếu bấm CANCEL hoặc đóng Dialog
        });

// Hiển thị Dialog và lấy kết quả
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> System.out.println("Tên đã nhập: " + name));
    }

    // Xử lý khi người dùng chọn xóa item
    @FXML
    private void deleteItem(){
        itemsTable.getSelectionModel().getSelectedItems().forEach(item -> System.out.println("Delete item with id: " + item.getId())); // In ra id của các dòng được chọn
        itemsTable.getSelectionModel().clearSelection(); // Bỏ chọn các dòng
    }


    // Hàm khởi tạo, chạy khi view được load
    @FXML
    public void initialize() {
        setupItemsTable();
        setupItemsPagination();
        deleteItemBtn.disableProperty().bind(itemsTable.getSelectionModel().selectedItemProperty().isNull()); // Disable button delete nếu không có dòng nào được chọn
    }


    // Khởi tạo bảng items
    private void setupItemsTable(){
        // Tùy chỉnh cột avatar để hiển thị hỉnh ảnh thay vì text
        avatarCol.setCellFactory(col -> new TableCell<Item, String>() {
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
        updateStockCol.setCellFactory(col -> new TableCell<Item, String>() {
            final Button updateStockColBtn = new Button("Update stock");

            {
                updateStockColBtn.getStyleClass().addAll("btn-custom");
                updateStockColBtn.setOnAction(event -> {
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
                    setGraphic(updateStockColBtn);
            }
        });

        // Tùy chỉnh cột stock history để hiển thị button thay vì text
        stockHistoryCol.setCellFactory(col -> new TableCell<Item, String>() {
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
        updateItemCol.setCellFactory(col -> new TableCell<Item, String>() {
            final Button updateItemBtn = new Button("Update item ");

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

        // Tạo dữ liệu cho bảng
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
                        .avatar("a-woman-working-on-a-laptop-6uAssP0vuPs")
                        .build(),
                Item.builder()
                        .id(2)
                        .itemNumber("8982323213")
                        .name("bim bim")
                        .category("Thực phẩm siêu sạch đem từ Mỹ về, không chứa chất bảo quản, không chất tạo màu")
                        .supplier("Coca Cola")
                        .wholesalePrice(5000)
                        .retailPrice(5000)
                        .quantity(5)
                        .taxPercent(0.1)
                        .avatar("a-woman-working-on-a-laptop-6uAssP0vuPs")
                        .build() ,
                Item.builder()
                        .id(3)
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

        itemsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); // Cho phép chọn nhiều dòng
        int cols = itemsTable.getColumns().size(); // Số cột của bảng
        itemsTable.getColumns().forEach(col -> col.prefWidthProperty().bind(itemsTable.widthProperty().divide(cols).subtract(0.65))); // Tự động thay đổi kích thước cột khi thay đổi kích thước bảng
        itemsTable.setItems(people);  // Thêm dữ liệu vào bảng
    }

    private void setupItemsPagination(){
        itemsPagination.setPageCount(10); // Số trang
    }
}
