package com.pos.app.controller;

import com.pos.app.model.InventoryModel;
import com.pos.app.model.Receivings;
import com.pos.app.util.ItemManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.util.converter.DoubleStringConverter;
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
    private InventoryModel inventoryModel;

    private final ItemManager itemManager = new ItemManager();

    private static final DecimalFormat numberFormat = new DecimalFormat("#,###.###");

    @FXML
    public void initialize() {
        inventoryModel = InventoryModel.getInstance();

        availableItems.addAll(
                new Receivings(1, "123456789", "Bim Bim", "Snack", "Supplier A", 4000.0, 5000.0, 1, "avatar1.png"),
                new Receivings(2, "987654321", "Cà phê", "Beverage", "Supplier B", 35000.0, 40000.0, 1, "avatar2.png"),
                new Receivings(3, "456789123", "Iphone 16 Pro Max", "Electronics", "Supplier C", 28000000.0, 30000000.0, 1, "avatar3.png"),
                new Receivings(4, "321654987", "Cao su", "Stationery", "Supplier D", 800.0, 1000.0, 100, "avatar4.png")
        );

        TableColumn<Receivings, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Receivings, String> barcodeCol = new TableColumn<>("Barcode");
        barcodeCol.setCellValueFactory(new PropertyValueFactory<>("barcode"));

        TableColumn<Receivings, String> nameCol = new TableColumn<>("Item Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Receivings, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));

        TableColumn<Receivings, String> supplierCol = new TableColumn<>("Supplier");
        supplierCol.setCellValueFactory(new PropertyValueFactory<>("supplier"));

        TableColumn<Receivings, Double> wholesalePriceCol = new TableColumn<>("Wholesale Price");
        wholesalePriceCol.setCellValueFactory(new PropertyValueFactory<>("wholesalePrice"));
        wholesalePriceCol.setCellFactory(column -> new TableCell<Receivings, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : numberFormat.format(item));
            }
        });

        TableColumn<Receivings, Double> retailPriceCol = new TableColumn<>("Retail Price");
        retailPriceCol.setCellValueFactory(new PropertyValueFactory<>("retailPrice"));
        retailPriceCol.setCellFactory(column -> new TableCell<Receivings, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : numberFormat.format(item));
            }
        });

        TableColumn<Receivings, Integer> quantityCol = new TableColumn<>("Quantity");
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        quantityCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        quantityCol.setOnEditCommit(event -> {
            Receivings receivings = event.getRowValue();
            receivings.setQuantity(event.getNewValue());
        });

        TableColumn<Receivings, String> avatarCol = new TableColumn<>("Avatar");
        avatarCol.setCellValueFactory(new PropertyValueFactory<>("avatar"));

        TableColumn<Receivings, Double> totalCol = new TableColumn<>("Total");
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

        TableColumn<Receivings, String> barcodeColRight = new TableColumn<>("Barcode");
        barcodeColRight.setCellValueFactory(new PropertyValueFactory<>("barcode"));

        TableColumn<Receivings, String> nameColRight = new TableColumn<>("Item Name");
        nameColRight.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Receivings, String> categoryColRight = new TableColumn<>("Category");
        categoryColRight.setCellValueFactory(new PropertyValueFactory<>("category"));

        TableColumn<Receivings, String> supplierColRight = new TableColumn<>("Supplier");
        supplierColRight.setCellValueFactory(new PropertyValueFactory<>("supplier"));

        TableColumn<Receivings, Double> wholesalePriceColRight = new TableColumn<>("Wholesale Price");
        wholesalePriceColRight.setCellValueFactory(new PropertyValueFactory<>("wholesalePrice"));
        wholesalePriceColRight.setCellFactory(column -> new TableCell<Receivings, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : numberFormat.format(item));
            }
        });

        TableColumn<Receivings, Double> retailPriceColRight = new TableColumn<>("Retail Price");
        retailPriceColRight.setCellValueFactory(new PropertyValueFactory<>("retailPrice"));
        retailPriceColRight.setCellFactory(column -> new TableCell<Receivings, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : numberFormat.format(item));
            }
        });

        TableColumn<Receivings, Integer> quantityColRight = new TableColumn<>("Quantity");
        quantityColRight.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn<Receivings, String> avatarColRight = new TableColumn<>("Avatar");
        avatarColRight.setCellValueFactory(new PropertyValueFactory<>("avatar"));

        TableColumn<Receivings, Double> totalColRight = new TableColumn<>("Total");
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
                for (Receivings item : availableItems) {
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
                        selectedItem.getRetailPrice(), selectedItem.getQuantity(), selectedItem.getAvatar()
                ));
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
        double totalAmount = rightTableItems.stream()
                .mapToDouble(Receivings::getTotal)
                .sum();
        totalPriceLabel.setText("Total Price: $" + numberFormat.format(totalAmount));
    }

    @FXML
    public void handleCancel() {
        rightTableItems.clear();
        totalPriceLabel.setText("Total Price: $0.00");
        tableView1.setVisible(false);
        confirmBtnTable2.setVisible(false);
        cancelBtn.setVisible(false);
        totalPriceLabel.setVisible(false);
    }
}