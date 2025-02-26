package com.pos.app.controller;

import com.pos.app.model.*;
import com.pos.app.store.ItemStore;
import com.pos.app.util.FormatHelper;
import com.pos.app.util.ItemManager;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.ScrollPane;

import java.util.*;


// Controller dung cho items view
public class ItemsController {
    // Đường dẫn mặc định của avatar
    private final String defaultAvatar = Objects
            .requireNonNull(getClass().getClassLoader().getResource("static/default-item.png")).toExternalForm();

    @FXML
    private TableView<Item> tableView;

    @FXML
    private Button deleteItemBtn;

    @FXML
    private Button importItemBtn;

    @FXML
    private Pagination itemsPagination;

    @FXML
    private VBox  columnsVisible;

    @FXML
    private ScrollPane  columnsVisibleContainer;

    @FXML
    private Button newItem;

    @FXML
    private ChoiceBox<String> locationChoices;

    @FXML
    private ComboBox<String> exportFileBtn;

    // Khởi tạo ItemManager để xử lý các sự kiện liên quan đến item
    private final ItemManager itemManager = new ItemManager();

    // Hàm khởi tạo, chạy khi view được load
    @FXML
    public void initialize() {
        setupLocationChoices(); // Khởi tạo danh sách các cửa hàng
        setupItemsTable(); // Khởi tạo bảng items
        setupItemsPagination(); // Khởi tạo phân trang
        itemManager.exportFileForm(exportFileBtn);
    }


    // --------------------------------Phần liên quan đến bảng items--------------------------------//
    
    // Khởi tạo bảng items
    private void setupItemsTable(){


        // Xử lý sự kiện khi người dùng ấn nút "New Item"
        newItem.setOnAction(event -> itemManager.createItem());

        TableColumn<Item, Number> idCol = new TableColumn<>("ID");
        TableColumn<Item, String> barcodeCol = new TableColumn<>("Barcode");
        TableColumn<Item, String> itemNameCol = new TableColumn<>("Item Name");
        TableColumn<Item, String> categoryCol = new TableColumn<>("Category");
        TableColumn<Item, String> supplierCol = new TableColumn<>("Supplier");
        TableColumn<Item, Number> wholeSalePriceCol = new TableColumn<>("Wholesale Price");
        TableColumn<Item, Number> retailPriceCol = new TableColumn<>("Retail Price");
        TableColumn<Item, Number> quantityAtCurrentLocationCol = new TableColumn<>("Quantity");
        TableColumn<Item, String> avatarCol = new TableColumn<>("Avatar");
        TableColumn<Item, String> updateInventoryCol = new TableColumn<>("");
//        TableColumn<Item, String> stockHistoryCol = new TableColumn<>("");
        TableColumn<Item, String> updateItemCol = new TableColumn<>("");


        idCol.setCellValueFactory(cellData -> cellData.getValue().getId());
        barcodeCol.setCellValueFactory(cellData -> cellData.getValue().getBarcode());
        itemNameCol.setCellValueFactory(cellData -> cellData.getValue().getItemName());
        categoryCol.setCellValueFactory(cellData -> cellData.getValue().getCategory());
        supplierCol.setCellValueFactory(cellData -> cellData.getValue().getSupplier());
        wholeSalePriceCol.setCellValueFactory(cellData -> cellData.getValue().getWholesalePrice());
        retailPriceCol.setCellValueFactory(cellData -> cellData.getValue().getRetailPrice());
        quantityAtCurrentLocationCol.setCellValueFactory(cellData -> cellData.getValue().getQuantityAtCurrentLocation());
        avatarCol.setCellValueFactory(cellData -> cellData.getValue().getAvatar());

        // Tùy chỉnh cột Wholesale Price để hiển thị giá tiền
        wholeSalePriceCol.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Number price, boolean empty) {
                super.updateItem(price, empty);
                if (empty || price == null) {
                    setText(null);
                } else {
                    setText(FormatHelper.formatDecimalNumber(price.doubleValue()));
                }
            }
        });
        
        // Tùy chỉnh cột avatar để hiển thị hỉnh ảnh thay vì text
        avatarCol.setCellFactory(col -> new TableCell<>() {
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
        updateInventoryCol.setCellFactory(col -> new TableCell<>() {
            final Button updateInventoryColBtn = new Button("Update inventory");

            {
                updateInventoryColBtn.getStyleClass().addAll("btn-custom");
                updateInventoryColBtn.setOnAction(event -> {
                    Item item = getTableView().getItems().get(getIndex());
                    System.out.println("Update inventory for item has id: " + item.getId());
                    itemManager.updateInventory(item);
                });
            }

            @Override
            protected void updateItem(String cell, boolean empty) {
                super.updateItem(cell, empty);
                if(empty)
                    setGraphic(null);
                else
                    setGraphic(updateInventoryColBtn);
            }
        });

        // Tùy chỉnh cột stock history để hiển thị button thay vì text
//        stockHistoryCol.setCellFactory(col -> new TableCell<>() {
//            final Button stockHistoryBtn = new Button("Stock history");
//
//            {
//                stockHistoryBtn.getStyleClass().addAll("btn-custom");
//                stockHistoryBtn.setOnAction(event -> {
//                    Item item = getTableView().getItems().get(getIndex());
//                    System.out.println("Display stock history for id: " + item.getId());
//                    itemManager.stockHistory(item);
//                });
//            }
//
//            @Override
//            protected void updateItem(String cell, boolean empty) {
//                super.updateItem(cell, empty);
//                if(super.isEmpty())
//                    setGraphic(null);
//                else
//                    setGraphic(stockHistoryBtn);
//            }
//        });

        // Tùy chỉnh cột update item để hiển thị button thay vì text
        updateItemCol.setCellFactory(col -> new TableCell<>() {
            final Button updateItemBtn = new Button("Update item ");

            {
                updateItemBtn.getStyleClass().addAll("btn-custom");
                updateItemBtn.setOnAction(event -> {
                    Item item = getTableView().getItems().get(getIndex());
                    System.out.println("Update item for id: " + item.getId().getValue());
                    itemManager.updateItemInfo(item);
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
        tableView.getColumns().addAll(idCol, barcodeCol, itemNameCol, categoryCol, supplierCol, wholeSalePriceCol, retailPriceCol, quantityAtCurrentLocationCol, avatarCol, updateInventoryCol, updateItemCol);


        this.deleteItemBtn.disableProperty().bind(this.tableView.getSelectionModel().selectedItemProperty().isNull()); // Disable button xóa khi không có dòng nào được chọn
        setupColVisible(); // Khởi tạo các checkbox để chọn cột hiển thị
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); // Cho phép chọn nhiều dòng
        int cols = tableView.getColumns().size(); // Số cột của bảng
        tableView.getColumns().forEach((col) -> {
            col.prefWidthProperty().bind(tableView.widthProperty().divide(cols).subtract(0.65));
            col.getStyleClass().add("col");
        });
        tableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY); // Không cho phép thay đổi kích thước cột
        tableView.setItems(ItemStore.visibleItems);  // Thêm dữ liệu vào bảng
    }

    // Xử lý khi người dùng chọn xóa item
    @FXML
    private void deleteItem() {
        ObservableList<Item> selectedItem = tableView.getSelectionModel().getSelectedItems();
        if (!selectedItem.isEmpty()) {
            // Tạo bản sao của danh sách để tránh thay đổi đồng thời
            List<Item> itemsToRemove = new ArrayList<>(selectedItem);
            // Xóa các mục đã chọn khỏi danh sách dữ liệu gốc
            tableView.getItems().removeAll(itemsToRemove);
        }
    }

    
    // Khởi tạo danh sách các vị trí
    private void setupLocationChoices(){
        // Thêm các vi trí vào danh sách
        ItemStore.locations.forEach(location -> locationChoices.getItems().add(location.getName().getValue()));
        // Khi người dùng chọn một vị trí thì cập nhật vị trí hiện tại = vị trí được chọn
        locationChoices.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            ItemStore.currentLocation.getName().set(newValue);
        });
        // Khởi tạo
        locationChoices.getSelectionModel().selectFirst();
        
    }


    //--------------------------------Phần liên quan đến ẩn/hiện cột--------------------------------//
    // Khởi tạo các checkbox để người dùng cột hiển thị
    private void setupColVisible() {
        columnsVisible.setSpacing(10);  // Khoảng cách giữa các checkbox
        
        // Tạo hiệu ứng đổ bóng
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(10);  // Độ mờ của bóng
        dropShadow.setOffsetX(5);  // Độ lệch theo X
        dropShadow.setOffsetY(5);  // Độ lệch theo Y
        dropShadow.setColor(Color.GRAY); // Màu bóng

        // Gán hiệu ứng vào Pane
        columnsVisibleContainer.setEffect(dropShadow);
        
        // Tạo các checkbox để chọn cột hiển thị
        for (TableColumn<Item, ?> col : tableView.getColumns()) {
            CheckBox checkBox = new CheckBox(col.getText());
            checkBox.selectedProperty().bindBidirectional(col.visibleProperty());
            columnsVisible.getChildren().add(checkBox);
        }

        // Ẩn container chứa các checkbox
        columnsVisibleContainer.setVisible(false);
    }

    // Khi người dùng ấn nút "Show/hide" thì hiển thị bảng checkbox để người dùng chọn cột
    @FXML
    private void showColVisible(){
        columnsVisibleContainer.setVisible(!columnsVisibleContainer.isVisible());
    }

    //--------------------------------Phần liên quan đến phân trang--------------------------------//
    // Khởi tạo phân trang
    private void setupItemsPagination(){
        itemsPagination.pageCountProperty().bind(ItemStore.pageCount);     // Khi pageCount thay đồi thì pageCount của pagination cũng thay đổi
        // Khi người dùng chuyển trang thì cập nhật dữ liệu hiển thị trên bảng
        itemsPagination.setPageFactory(pageIndex -> {
            int fromIndex = Math.min(pageIndex * ItemStore.pageSize.getValue(), ItemStore.visibleItems.size());
            int toIndex = Math.min(fromIndex + ItemStore.pageSize.getValue(), ItemStore.visibleItems.size());
            if ((fromIndex + toIndex) != 0)
                tableView.setItems(FXCollections.observableList(ItemStore.visibleItems.subList(fromIndex, toIndex)));
            else
                tableView.setItems(FXCollections.observableList(new ArrayList<>()));
            return new VBox();
        });
        
    }

    // Xử lý khi người dùng chọn import item
    @FXML
    private void importCSVFile() {
        itemManager.openImportForm(importItemBtn);
    }


}
 