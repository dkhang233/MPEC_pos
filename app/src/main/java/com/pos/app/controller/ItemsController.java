package com.pos.app.controller;

import com.pos.app.model.Item;
import com.pos.app.store.ItemStore;
import com.pos.app.util.FormatHelper;
import com.pos.app.util.ItemManager;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ItemsController {
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
    private VBox columnsVisible;

    @FXML
    private ScrollPane columnsVisibleContainer;

    @FXML
    private Button newItem;

    @FXML
    private ComboBox<String> exportFileBtn;

    private final ItemManager itemManager = new ItemManager();

    @FXML
    public void initialize() {
        itemManager.getItemsData(); // Lấy dữ liệu từ backend
        setupItemsTable();
        setupItemsPagination();
        itemManager.exportFileForm(exportFileBtn);

    }

    private void setupItemsTable() {
        newItem.setOnAction(event -> itemManager.createItem());

        TableColumn<Item, Number> idCol = new TableColumn<>("ID");
        TableColumn<Item, String> barcodeCol = new TableColumn<>("Barcode");
        TableColumn<Item, String> itemNameCol = new TableColumn<>("Item Name");
        TableColumn<Item, String> categoryCol = new TableColumn<>("Category");
        TableColumn<Item, String> supplierCol = new TableColumn<>("Supplier");
        TableColumn<Item, Number> costPriceCol = new TableColumn<>("Cost Price");
        TableColumn<Item, Number> sellingPriceCol = new TableColumn<>("Selling Price");
        TableColumn<Item, Number> quantityAtCurrentLocationCol = new TableColumn<>("Quantity");
        TableColumn<Item, String> avatarCol = new TableColumn<>("Avatar");
        TableColumn<Item, String> updateInventoryCol = new TableColumn<>("");
        TableColumn<Item, String> updateItemCol = new TableColumn<>("");

        idCol.setCellValueFactory(cellData -> cellData.getValue().getId());
        barcodeCol.setCellValueFactory(cellData -> cellData.getValue().getBarcode());
        itemNameCol.setCellValueFactory(cellData -> cellData.getValue().getItemName());
        categoryCol.setCellValueFactory(cellData -> cellData.getValue().getCategory());
        supplierCol.setCellValueFactory(cellData -> cellData.getValue().getSupplier());
        costPriceCol.setCellValueFactory(cellData -> cellData.getValue().getCostPrice());
        sellingPriceCol.setCellValueFactory(cellData -> cellData.getValue().getSellingPrice());
        quantityAtCurrentLocationCol.setCellValueFactory(cellData -> cellData.getValue().getQuantity());
        avatarCol.setCellValueFactory(cellData -> cellData.getValue().getAvatar());

        costPriceCol.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Number price, boolean empty) {
                super.updateItem(price, empty);
                setText(empty || price == null ? null : FormatHelper.formatDecimalNumber(price.doubleValue()) + " đ");
            }
        });

        sellingPriceCol.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Number price, boolean empty) {
                super.updateItem(price, empty);
                setText(empty || price == null ? null : FormatHelper.formatDecimalNumber(price.doubleValue()) + " đ");
            }
        });

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
                setGraphic(empty ? null : updateInventoryColBtn);
            }
        });

        updateItemCol.setCellFactory(col -> new TableCell<>() {
            final Button updateItemBtn = new Button("Update item");
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
                setGraphic(empty ? null : updateItemBtn);
            }
        });

        tableView.getColumns().addAll(idCol, barcodeCol, itemNameCol, categoryCol, supplierCol,
                costPriceCol, sellingPriceCol, quantityAtCurrentLocationCol, avatarCol,
                updateInventoryCol, updateItemCol);

        deleteItemBtn.disableProperty().bind(tableView.getSelectionModel().selectedItemProperty().isNull());
        setupColVisible();
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        int cols = tableView.getColumns().size();
        tableView.getColumns().forEach(col -> {
            col.prefWidthProperty().bind(tableView.widthProperty().divide(cols).subtract(0.65));
            col.getStyleClass().add("col");
        });
        tableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        tableView.setItems(ItemStore.itemPage);
    }

    @FXML
    private void deleteItem() {
        ObservableList<Item> selectedItems = tableView.getSelectionModel().getSelectedItems();
        if (!selectedItems.isEmpty()) {
            List<Item> itemsToRemove = new ArrayList<>(selectedItems);
            for (Item item : itemsToRemove) {
                itemManager.deleteItem(item);
            }
        }
    }

    private void setupColVisible() {
        columnsVisible.setSpacing(10);
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(10);
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);
        dropShadow.setColor(Color.GRAY);
        columnsVisibleContainer.setEffect(dropShadow);
        for (TableColumn<Item, ?> col : tableView.getColumns()) {
            CheckBox checkBox = new CheckBox(col.getText());
            checkBox.selectedProperty().bindBidirectional(col.visibleProperty());
            columnsVisible.getChildren().add(checkBox);
        }
        columnsVisibleContainer.setVisible(false);
    }

    @FXML
    private void showColVisible() {
        columnsVisibleContainer.setVisible(!columnsVisibleContainer.isVisible());
    }

    private void setupItemsPagination() {
        itemsPagination.pageCountProperty().bind(ItemStore.pageCount);
        itemsPagination.currentPageIndexProperty().bindBidirectional(ItemStore.currentPage);
    }

    @FXML
    private void importCSVFile() {
        itemManager.openImportForm(importItemBtn);
    }
}