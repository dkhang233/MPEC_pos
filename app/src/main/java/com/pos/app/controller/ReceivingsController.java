package com.pos.app.controller;

import com.pos.app.api.ItemsApi;
import com.pos.app.dto.ItemQuantityDto;
import com.pos.app.model.Item;
import com.pos.app.model.Receivings;
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

public class ReceivingsController {

    @FXML
    private TableView<Receivings> tableView;
    @FXML
    private TableView<Receivings> tableView1;
    @FXML
    private TextField searchField;
    @FXML
    private ListView<Receivings> suggestionsList;
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

    private ObservableList<Receivings> leftTableItems = FXCollections.observableArrayList();
    private ObservableList<Receivings> rightTableItems = FXCollections.observableArrayList();
    private ObservableList<Receivings> availableItems = FXCollections.observableArrayList();
    private ObservableList<Receivings> suggestions = FXCollections.observableArrayList();
    private boolean isTableHidden = false;

    private final ItemManager itemManager = new ItemManager();

    private final ItemsApi itemsApi = new ItemsApi();

    private static final DecimalFormat numberFormat = new DecimalFormat("#,###.###");

    @FXML
    public void initialize() {

        TableColumn<Receivings, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Receivings, String> barcodeCol = new TableColumn<>("Mã vạch");
        barcodeCol.setCellValueFactory(new PropertyValueFactory<>("barcode"));

        TableColumn<Receivings, String> nameCol = new TableColumn<>("Tên sản phẩm");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Receivings, String> categoryCol = new TableColumn<>("Phân loại");
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));

        TableColumn<Receivings, String> supplierCol = new TableColumn<>("Nhà cung cấp");
        supplierCol.setCellValueFactory(new PropertyValueFactory<>("supplier"));

        TableColumn<Receivings, Double> wholesalePriceCol = new TableColumn<>("Giá nhập");
        wholesalePriceCol.setCellValueFactory(new PropertyValueFactory<>("wholesalePrice"));
        wholesalePriceCol.setCellFactory(column -> new TableCell<Receivings, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : numberFormat.format(item));
            }
        });

        TableColumn<Receivings, Double> retailPriceCol = new TableColumn<>("Giá bán");
        retailPriceCol.setCellValueFactory(new PropertyValueFactory<>("retailPrice"));
        retailPriceCol.setCellFactory(column -> new TableCell<Receivings, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : numberFormat.format(item));
            }
        });

        TableColumn<Receivings, Integer> quantityCol = new TableColumn<>("Số lượng");
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        quantityCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        quantityCol.setOnEditCommit(event -> {
            Receivings receivings = event.getRowValue();
            receivings.setQuantity(event.getNewValue());
        });

        TableColumn<Receivings, String> avatarCol = new TableColumn<>("Avatar");
        avatarCol.setCellValueFactory(new PropertyValueFactory<>("avatar"));

        TableColumn<Receivings, Double> totalCol = new TableColumn<>("Tổng giá");
        totalCol.setCellValueFactory(new PropertyValueFactory<>("total"));
        totalCol.setCellFactory(column -> new TableCell<Receivings, Double>() {
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

        TableColumn<Receivings, Integer> idColRight = new TableColumn<>("ID");
        idColRight.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Receivings, String> barcodeColRight = new TableColumn<>("Mã vạch");
        barcodeColRight.setCellValueFactory(new PropertyValueFactory<>("barcode"));

        TableColumn<Receivings, String> nameColRight = new TableColumn<>("Tên sản phẩm");
        nameColRight.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Receivings, String> categoryColRight = new TableColumn<>("Phân loại");
        categoryColRight.setCellValueFactory(new PropertyValueFactory<>("category"));

        TableColumn<Receivings, String> supplierColRight = new TableColumn<>("Nhà cung cấp");
        supplierColRight.setCellValueFactory(new PropertyValueFactory<>("supplier"));

        TableColumn<Receivings, Double> wholesalePriceColRight = new TableColumn<>("Giá nhập");
        wholesalePriceColRight.setCellValueFactory(new PropertyValueFactory<>("wholesalePrice"));
        wholesalePriceColRight.setCellFactory(column -> new TableCell<Receivings, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : numberFormat.format(item));
            }
        });

        TableColumn<Receivings, Double> retailPriceColRight = new TableColumn<>("Giá bán");
        retailPriceColRight.setCellValueFactory(new PropertyValueFactory<>("retailPrice"));
        retailPriceColRight.setCellFactory(column -> new TableCell<Receivings, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : numberFormat.format(item));
            }
        });

        TableColumn<Receivings, Integer> quantityColRight = new TableColumn<>("Số lượng");
        quantityColRight.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn<Receivings, String> avatarColRight = new TableColumn<>("Avatar");
        avatarColRight.setCellValueFactory(new PropertyValueFactory<>("avatar"));

        TableColumn<Receivings, Double> totalColRight = new TableColumn<>("Tổng");
        totalColRight.setCellValueFactory(new PropertyValueFactory<>("total"));
        totalColRight.setCellFactory(column -> new TableCell<Receivings, Double>() {
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

        suggestionsList.setCellFactory(lv -> new ListCell<Receivings>() {
            @Override
            protected void updateItem(Receivings item, boolean empty) {
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
                for (Receivings item : ItemStore.items.stream().filter(i -> i.getDeleted().get() == false)
                        .map(Item::mapToReceivings).toList()) {
                    if (item.getName().toLowerCase().contains(newVal.toLowerCase())) {
                        suggestions.add(item);
                    }
                }
                suggestionsList.setItems(suggestions);
                suggestionsList.setVisible(!suggestions.isEmpty());
            }
        });

        suggestionsList.setOnMouseClicked(event -> {
            Receivings selectedItem = suggestionsList.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                leftTableItems.add(new Receivings(
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
        Receivings selectedItem = tableView.getSelectionModel().getSelectedItem();
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
            alert.setTitle("No Items");
            alert.setHeaderText(null);
            alert.setContentText("There are no items in Table 1 to transfer.");
            alert.showAndWait();
        }
    }

    @FXML
    public void handleConfirmTable2() {
        if (!rightTableItems.isEmpty()) {
            for (Receivings item : rightTableItems) {
                ItemQuantityDto itemQuantityDto = new ItemQuantityDto(item.getId(), item.getQuantity(),
                        "Receiving item");
                itemsApi.updateItemQuantity(itemQuantityDto);
                ItemStore.items.stream().filter(i -> i.getId().get() == item.getId()).findFirst()
                        .ifPresent(i -> i.getQuantity().set(i.getQuantity().get() + item.getQuantity()));
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Items");
            alert.setHeaderText(null);
            alert.setContentText("There are no items in Table 2 to confirm.");
            alert.showAndWait();
        }
        double totalAmount = rightTableItems.stream()
                .mapToDouble(Receivings::getTotal)
                .sum();
        totalPriceLabel.setText("Total Price: $" + numberFormat.format(totalAmount));
        confirmBtnTable2.disableProperty().set(true);

    }

    @FXML
    public void handleCancel() {
        rightTableItems.clear();
        totalPriceLabel.setText("Total Price: $0.00");
        tableView1.setVisible(false);
        confirmBtnTable2.setVisible(false);
        cancelBtn.setVisible(false);
        totalPriceLabel.setVisible(false);
        confirmBtnTable2.disableProperty().set(false);
        tableView.setVisible(isTableHidden);

    }
}