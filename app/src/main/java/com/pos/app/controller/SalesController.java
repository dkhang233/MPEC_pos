package com.pos.app.controller;

import com.pos.app.api.ItemsApi;
import com.pos.app.dto.ItemQuantityDto;
import com.pos.app.model.Item;
import com.pos.app.model.Sales;
import com.pos.app.store.ItemStore;
import com.pos.app.util.ItemManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;

import java.text.DecimalFormat;
import java.util.Optional;

public class SalesController {

    @FXML
    private TableView<Sales> tableView;
    @FXML
    private TableView<Sales> tableView1;
    @FXML
    private TextField searchField;
    @FXML
    private ListView<Sales> suggestionsList;
    @FXML
    private Button newItem;
    @FXML
    private Button deleteItemBtn;
    @FXML
    private Pagination itemsPagination;
    @FXML
    private Button confirmBtnTable1;
    @FXML
    private Button confirmBtnTable2;
    @FXML
    private Button cancelBtn;
    @FXML
    private Label totalPriceLabel;

    private ObservableList<Sales> leftTableItems = FXCollections.observableArrayList();
    private ObservableList<Sales> rightTableItems = FXCollections.observableArrayList();
    private ObservableList<Sales> availableItems = FXCollections.observableArrayList();
    private ObservableList<Sales> suggestions = FXCollections.observableArrayList();
    private boolean isTableHidden = false;

    private final ItemManager itemManager = new ItemManager();
    private final ItemsApi itemsApi = new ItemsApi();

    private static final DecimalFormat numberFormat = new DecimalFormat("#,###.###");

    @FXML
    public void initialize() {

        TableColumn<Sales, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Sales, String> barcodeCol = new TableColumn<>("Mã vạch");
        barcodeCol.setCellValueFactory(new PropertyValueFactory<>("barcode"));

        TableColumn<Sales, String> nameCol = new TableColumn<>("Tên sản phẩm");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Sales, String> categoryCol = new TableColumn<>("Phân loại");
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));

        TableColumn<Sales, String> supplierCol = new TableColumn<>("Nhà cung cấp");
        supplierCol.setCellValueFactory(new PropertyValueFactory<>("supplier"));

        TableColumn<Sales, Double> wholesalePriceCol = new TableColumn<>("Giá nhập");
        wholesalePriceCol.setCellValueFactory(new PropertyValueFactory<>("wholesalePrice"));
        wholesalePriceCol.setCellFactory(column -> new TableCell<Sales, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : numberFormat.format(item));
            }
        });

        TableColumn<Sales, Double> retailPriceCol = new TableColumn<>("Giá bán");
        retailPriceCol.setCellValueFactory(new PropertyValueFactory<>("retailPrice"));
        retailPriceCol.setCellFactory(column -> new TableCell<Sales, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : numberFormat.format(item));
            }
        });

        TableColumn<Sales, Integer> quantityCol = new TableColumn<>("Số lượng");
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        quantityCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        quantityCol.setOnEditCommit(event -> {
            Sales Sales = event.getRowValue();
            Sales.setQuantity(event.getNewValue());
        });

        TableColumn<Sales, String> avatarCol = new TableColumn<>("Avatar");
        avatarCol.setCellValueFactory(new PropertyValueFactory<>("avatar"));

        TableColumn<Sales, Double> totalCol = new TableColumn<>("Tổng");
        totalCol.setCellValueFactory(new PropertyValueFactory<>("total"));
        totalCol.setCellFactory(column -> new TableCell<Sales, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : numberFormat.format(item));
            }
        });

        tableView.getColumns().addAll(idCol, barcodeCol, nameCol, categoryCol, supplierCol,
                wholesalePriceCol, retailPriceCol, quantityCol, avatarCol, totalCol);
        tableView.setItems(leftTableItems);
        tableView.setEditable(true);

        TableColumn<Sales, Integer> idColRight = new TableColumn<>("ID");
        idColRight.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Sales, String> barcodeColRight = new TableColumn<>("Mã vạch");
        barcodeColRight.setCellValueFactory(new PropertyValueFactory<>("barcode"));

        TableColumn<Sales, String> nameColRight = new TableColumn<>("Tên sản phẩm");
        nameColRight.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Sales, String> categoryColRight = new TableColumn<>("Phân loại");
        categoryColRight.setCellValueFactory(new PropertyValueFactory<>("category"));

        TableColumn<Sales, String> supplierColRight = new TableColumn<>("Nhà cung cấp");
        supplierColRight.setCellValueFactory(new PropertyValueFactory<>("supplier"));

        TableColumn<Sales, Double> wholesalePriceColRight = new TableColumn<>("Giá nhập");
        wholesalePriceColRight.setCellValueFactory(new PropertyValueFactory<>("wholesalePrice"));
        wholesalePriceColRight.setCellFactory(column -> new TableCell<Sales, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : numberFormat.format(item));
            }
        });

        TableColumn<Sales, Double> retailPriceColRight = new TableColumn<>("Giá bán");
        retailPriceColRight.setCellValueFactory(new PropertyValueFactory<>("retailPrice"));
        retailPriceColRight.setCellFactory(column -> new TableCell<Sales, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : numberFormat.format(item));
            }
        });

        TableColumn<Sales, Integer> quantityColRight = new TableColumn<>("Số lượng");
        quantityColRight.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn<Sales, String> avatarColRight = new TableColumn<>("Avatar");
        avatarColRight.setCellValueFactory(new PropertyValueFactory<>("avatar"));

        TableColumn<Sales, Double> totalColRight = new TableColumn<>("Tổng");
        totalColRight.setCellValueFactory(new PropertyValueFactory<>("total"));
        totalColRight.setCellFactory(column -> new TableCell<Sales, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : numberFormat.format(item));
            }
        });

        tableView1.getColumns().addAll(idColRight, barcodeColRight, nameColRight, categoryColRight, supplierColRight,
                wholesalePriceColRight, retailPriceColRight, quantityColRight, avatarColRight, totalColRight);
        tableView1.setItems(rightTableItems);
        tableView1.getStyleClass().add("no-border-table");

        suggestionsList.setCellFactory(lv -> new ListCell<Sales>() {
            @Override
            protected void updateItem(Sales item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getName());
            }
        });

        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == null || newVal.isEmpty()) {
                suggestionsList.setVisible(false);
                suggestions.clear();
            } else {
                suggestions.clear();
                for (Sales item : ItemStore.items.stream().filter(i -> i.getDeleted().get() == false)
                        .map(Item::mapToSales).toList()) {
                    if (item.getName().toLowerCase().contains(newVal.toLowerCase())) {
                        suggestions.add(item);
                    }
                }
                suggestionsList.setItems(suggestions);
                suggestionsList.setVisible(!suggestions.isEmpty());
            }
        });

        suggestionsList.setOnMouseClicked(event -> {
            Sales selectedItem = suggestionsList.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                leftTableItems.add(new Sales(
                        selectedItem.getId(), selectedItem.getBarcode(), selectedItem.getName(),
                        selectedItem.getCategory(), selectedItem.getSupplier(), selectedItem.getWholesalePrice(),
                        selectedItem.getRetailPrice(), selectedItem.getQuantity(), selectedItem.getAvatar()));
                suggestionsList.setVisible(false);
                searchField.clear();
            }
        });

        itemsPagination.setPageCount(10);
        itemsPagination.setCurrentPageIndex(0);

        totalPriceLabel.setText("Total Price: $0.00");
    }

    @FXML
    public void handleNewItem() {
        itemManager.createItem();
    }

    @FXML
    public void deleteItem() {
        Sales selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            leftTableItems.remove(selectedItem);
        }
    }

    @FXML
    public void handleHideShow() {
        isTableHidden = !isTableHidden;
        tableView.setVisible(!isTableHidden);
    }

    @FXML
    public void handleConfirmTable1() {
        if (!leftTableItems.isEmpty()) {
            Boolean valid = true;
            for (Sales item : leftTableItems) {
                if (item.getQuantity() <= 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Số lượng không hợp lệ");
                    alert.setHeaderText(null);
                    alert.setContentText("Số lượng sản phẩm không hợp lệ: " + item.getName());
                    alert.showAndWait();
                    valid = false;
                    break;
                }

                Optional<Item> item2 = ItemStore.items.stream().filter(i -> i.getId().get() == item.getId())
                        .findFirst();
                if (item2.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Không tìm thấy sản phẩm");
                    alert.setHeaderText(null);
                    alert.setContentText("Sản phẩm không tìm thấy: " + item.getName());
                    alert.showAndWait();
                    valid = false;
                    break;
                }

                if (item2.get().getQuantity().get() < item.getQuantity()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Số lượng không đủ");
                    alert.setHeaderText(null);
                    alert.setContentText("Không đủ số lượng cho sản phẩm: " + item.getName());
                    alert.showAndWait();
                    valid = false;
                    break;
                }
            }
            if (!valid) {
                return;
            }
            rightTableItems.addAll(leftTableItems);
            leftTableItems.clear();
            tableView1.setVisible(true);
            confirmBtnTable2.setVisible(true);
            cancelBtn.setVisible(true);
            totalPriceLabel.setVisible(true);
            isTableHidden = !isTableHidden;
            tableView.setVisible(!isTableHidden);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Không có sản phẩm");
            alert.setHeaderText(null);
            alert.setContentText("Không có sản phẩm nào để bán.");
            alert.showAndWait();
        }
    }

    @FXML
    public void handleConfirmTable2() {
        if (!rightTableItems.isEmpty()) {
            for (Sales item : rightTableItems) {
                ItemQuantityDto itemQuantityDto = new ItemQuantityDto(item.getId(), -item.getQuantity(),
                        "Selling item");
                itemsApi.updateItemQuantity(itemQuantityDto);
                ItemStore.items.stream().filter(i -> i.getId().get() == item.getId()).findFirst()
                        .ifPresent(i -> i.getQuantity().set(i.getQuantity().get() - item.getQuantity()));
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Items");
            alert.setHeaderText(null);
            alert.setContentText("There are no items in Table 2 to confirm.");
            alert.showAndWait();
        }
        double totalAmount = rightTableItems.stream()
                .mapToDouble(Sales::getTotal)
                .sum();
        totalPriceLabel.setText("Total Price: $" + numberFormat.format(totalAmount));
        confirmBtnTable2.disableProperty().set(true);
    }

    @FXML
    public void handleCancel() {
        rightTableItems.clear();
        totalPriceLabel.setText("Total Price: $0.00");
        tableView1.setVisible(false);
        confirmBtnTable2.disableProperty().set(false);
        confirmBtnTable2.setVisible(false);
        cancelBtn.setVisible(false);
        totalPriceLabel.setVisible(false);

        tableView.setVisible(isTableHidden);
    }
}