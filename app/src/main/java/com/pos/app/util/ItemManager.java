package com.pos.app.util;

import com.dlsc.formsfx.model.structure.*;
import com.dlsc.formsfx.view.controls.SimpleTextControl;
import com.pos.app.model.*;

import com.dlsc.formsfx.model.validators.IntegerRangeValidator;
import com.dlsc.formsfx.view.controls.SimpleRadioButtonControl;
import com.dlsc.formsfx.view.renderer.FormRenderer;
import com.pos.app.component.CurrencyInput;
import com.pos.app.component.ImageUpload;
import com.pos.app.store.ItemStore;
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ItemManager {
    // Xử lý khi người dùng chọn tạo item mới
    @FXML
    public void createItem() {
        initItemForm(1, new Item());
    }

    // Xử lý khi người dùng muốn cập nhật thông tin item
    @FXML
    public void updateItemInfo(Item item){
       initItemForm(2, item);
    }


    // Xử lý khi người dùng muốn cập nhật số lượng item
    @FXML
    public void updateInventory(Item item) {
        IntegerProperty currentQuantity = new SimpleIntegerProperty(item.getQuantityAtCurrentLocation().getValue());
        IntegerProperty inOutQuantity = new SimpleIntegerProperty(0);
        StringProperty comment = new SimpleStringProperty("");

        // Tạo danh sách lịch sử thay đổi số lượng item theo từng vị trí
        Map<String, List<Inventory>> inventoriesForThisItem = new HashMap<>();
        ItemStore.inventories.get(item.getId().getValue()).forEach( inventory -> {
            if(inventoriesForThisItem.containsKey(inventory.getLocation().getValue())){
                inventoriesForThisItem.get(inventory.getLocation().getValue()).add(inventory);
            }else{
                List<Inventory> list = new ArrayList<>();
                list.add(inventory);
                inventoriesForThisItem.put(inventory.getLocation().getValue(), list);
            }
        });


        // Tạo TableView để hiển thị lịch sử số lượng item
        TableView<Inventory> stockHistoryTable = new TableView<>();
        stockHistoryTable.setPrefSize(650, 500);
        TableColumn<Inventory, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getTimestamp().getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))));
        TableColumn<Inventory, String> userCol = new TableColumn<>("User");
        userCol.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getUsername().getValue()));
        TableColumn<Inventory, String> changeCol = new TableColumn<>("In/Out");
        changeCol.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getInventory().getValue() + ""));
        TableColumn<Inventory, String> quantityCol = new TableColumn<>("After quantity");
        quantityCol.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getAfterInventory().getValue() + ""));
        TableColumn<Inventory, String> commentCol = new TableColumn<>("Comment");
        commentCol.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getComment().getValue()));
        stockHistoryTable.getColumns().addAll(dateCol, userCol, changeCol ,  quantityCol, commentCol);
        int cols = stockHistoryTable.getColumns().size(); // Số cột của bảng
        stockHistoryTable.getColumns().forEach(col -> col.prefWidthProperty().bind(stockHistoryTable.widthProperty().divide(cols).subtract(0.65))); // Tự động thay đổi kích thước cột khi thay đổi kích thước bảng

        // Tạo trường nhập số lượng để thêm vào hoặc trừ ra khỏi số lượng item
        IntegerField inOutQuantityField = Field.ofIntegerType(inOutQuantity)
                .label("In/Out")
                .tooltip("Input positive value to add to inventory, negative value to subtract")
                .required("Quantity is a required field")
                .validate(IntegerRangeValidator.atLeast(-currentQuantity.get()
                        ,"Can't add value smaller than current inventory"));

        // Tạo trường hiển thị số lượng hiện tại của item
        IntegerField currentQuantityField = Field.ofIntegerType(currentQuantity)
                .label("Quantity")
                .editable(false);

        // Thay đổi điều kiện của trường nhập số lượng (thêm vào hoặc trừ ra khỏi số lượng item)  khi số lượng hiện tại thay đổi
        currentQuantityField.valueProperty().addListener((observable, oldValue, newValue) -> {
            inOutQuantityField.validate(IntegerRangeValidator.atLeast(-newValue.intValue()
                    ,"Can't add value smaller than current inventory"));
        });

        // Tạo trường chọn vị trí để xem lịch sử, và hiển thị số lượng hiện tại của item tương ứng với vị trí
        int selectedLocationIndex = 0;
        for (int i = 0; i < ItemStore.locations.size(); i++) {
            if(ItemStore.locations.get(i).getName().getValue().equals(
                    ItemStore.currentLocation.getName().toString()
            )){
                selectedLocationIndex = i;
                break;
            }
        }
        SingleSelectionField<String> stockLocationField =  Field.ofSingleSelectionType(ItemStore.locations.stream().map(location -> location.getName().getValue()).toList(),selectedLocationIndex)
        .label("Location: ");
        stockLocationField.selectionProperty().addListener((observable, oldValue, newValue) -> {
            stockHistoryTable.getItems().clear();
            if (inventoriesForThisItem.containsKey(newValue))
                stockHistoryTable.getItems().addAll(inventoriesForThisItem.get(newValue));
            currentQuantity.set(item.getQuantityPerLocation().stream()
                    .filter(quantity -> quantity.getLocationName().getValue().equals(newValue))
                    .findFirst()
                    .orElse(new ItemQuantity(0,"",0)).getQuantity().getValue());
        });


         // Thêm dữ liệu vào TableView
        if(inventoriesForThisItem.containsKey(stockLocationField.getSelection())){
            stockHistoryTable.getItems().addAll(inventoriesForThisItem.get(stockLocationField.getSelection()));
        }
         
        // Tạo form để hiển thị chi tiết số lượng item
        Form stockHistoryForm = Form.of(
                Group.of(
                        Field.ofStringType(item.getBarcode())
                                .label("Barcode")
                                .editable(false),

                        Field.ofStringType(item.getItemName())
                                .label("Item Name: ")
                                .editable(false),

                        Field.ofStringType(item.getCategory())
                                .label("Category: ")
                                .editable(false),
                        stockLocationField,  // thêm trường tạo ở trên vào form
                        currentQuantityField,
                        inOutQuantityField,

                        Field.ofStringType(comment)
                                .label("Comment")
                                .multiline(true)
                )
        );



        Dialog<Object> stockDialog = new Dialog<>();
        stockDialog.setTitle("Inventory Count Detail");

        VBox stockHistoryContainer = new VBox(10);
        HBox body = new HBox(10);

        FormRenderer formRenderer = new FormRenderer(stockHistoryForm);
        formRenderer.setPrefSize(500, 280);
        try {
            formRenderer.getStylesheets().add(Objects.requireNonNull(getClass().getClassLoader().getResource("css/styles.css")).toExternalForm());
        } catch (Exception e) {
            e.printStackTrace();
        }

        configDialogButtonUpdateInventory(stockDialog, stockHistoryForm ,item, currentQuantity ,stockLocationField, inOutQuantity, comment, stockHistoryTable);

        body.getChildren().add(formRenderer);
        body.getChildren().add(stockHistoryTable);
        stockHistoryContainer.getChildren().addAll(body);
        stockDialog.getDialogPane().setContent(stockHistoryContainer);
        stockDialog.showAndWait();
    }




    // Xử lý khi người dùng muốn xem lịch sử thay đổi số lượng item
    @FXML
    public void stockHistory(Item item){

    }

    // Khởi tạo các thành phần cần thiết để tạo mới hoặc cập nhật item
    private void initItemForm(int type, Item item) {
        BindingNewItem newItemModel =  item.mapToBindingNewItem();   // Khởi tạo model
        Form newItemForm = createItemForm(newItemModel);      // Tạo form

        // Tạo renderer cho form
        FormRenderer formRenderer = new FormRenderer(newItemForm);
        formRenderer.setPrefSize(600, 600);
        try {
            formRenderer.getStylesheets().add(Objects.requireNonNull(getClass().getClassLoader().getResource("css/styles.css")).toExternalForm());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Tạo container chứa form
        VBox container = new VBox(10);
        container.getChildren().add(formRenderer);

        // Đưa VBox vào ScrollPane
        ScrollPane scrollPane = new ScrollPane(container);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        // Khởi tạo dialog
        Dialog<Item> itemDialog = new Dialog<>();
        itemDialog.getDialogPane().setContent(scrollPane);     // Thêm form vào Dialog
        itemDialog.setWidth(650);
        itemDialog.setHeight(600);
        HBox buttonBox;
        if (type == 1) {
            itemDialog.setTitle("Create new item");
            buttonBox = configDialogButtonNewItem(itemDialog,newItemForm, newItemModel);

        } else {
            itemDialog.setTitle("Update Item");
            buttonBox = configDialogButtonUpdateItemInfo(itemDialog,newItemForm, newItemModel);
        }
        container.getChildren().add(buttonBox);    // Thêm nút tạo mới vào form
        itemDialog.showAndWait();
    }


    // Tạo form để thêm mới và cập nhật item
    private Form createItemForm(BindingNewItem newItemModel) {
        return Form.of(
                Group.of(
                        Field.ofStringType(newItemModel.getBarcode())
                                .label("Barcode"),

                        Field.ofStringType(newItemModel.getItemName())
                                .label("Item Name")
                                .required("Item Name is required"),

                        Field.ofStringType(newItemModel.getCategory())
                                .label("Category")
                                .required("Category is required"),

                        Field.ofStringType(newItemModel.getSupplier())
                                .label("Supplier")
                                .tooltip("Supplier"),

                        Field.ofSingleSelectionType(newItemModel.getStockTypes(), newItemModel.getSelectedStockType())
                                .label("Stock Item")
                                .render(new SimpleRadioButtonControl<>()),

                        Field.ofSingleSelectionType(newItemModel.getItemTypes(), newItemModel.getSelectedItemType())
                                .label("Item type")
                                .render(new SimpleRadioButtonControl<>()),

                        Field.ofDoubleType(newItemModel.getWholesalePrice())
                                .label("Wholesale Price")
                                .required("Wholesale Price is required")
                                .render(new CurrencyInput(1, "$")),

                        Field.ofDoubleType(newItemModel.getRetailPrice())
                                .label("Retail Price")
                                .required("Retail Price is required")
                                .render(new CurrencyInput(1, "$")),

                        Field.ofStringType(newItemModel.getTax1Name())
                                .label("Tax1")
                                .placeholder("Name of Tax 1:"),
                        Field.ofStringType(newItemModel.getTax2Name())
                                .label("Tax2")
                                .placeholder("Name of Tax 2:"),

                        Field.ofDoubleType(newItemModel.getTax1())
                                .label("")
                                .render(new CurrencyInput(2, "%")),
                        Field.ofDoubleType(newItemModel.getTax2())
                                .label("")
                                .render(new CurrencyInput(2, "%")),

                        Field.ofIntegerType(1)
                                .label("Quantity Stock")
                                .required("Quantity Stock is required"),

                        Field.ofIntegerType(newItemModel.getReceivingQuantity())
                                .label("Receiving Quantity")
                                .required("Receiving Quantity is required"),

                        Field.ofIntegerType(newItemModel.getReorderLevel())
                                .label("Reorder Level")
                                .required("Reorder Level is required"),

                        Field.ofStringType(newItemModel.getDescription())
                                .label("Description"),

                        // Trường avatar cần tùy chỉnh để cho phép chọn và hiển thị hình ảnh
                        Field.ofStringType(newItemModel.getAvatar())
                                .label("Avatar")
                                .render(new ImageUpload()),

                        Field.ofBooleanType(newItemModel.getDeleted())
                                .label("Deleted")
                )
        );
    }
    
    private HBox configDialogButtonNewItem(Dialog<?> dialog,Form form, BindingNewItem model) {
        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType newButtonType = new ButtonType("New", ButtonBar.ButtonData.FINISH);
        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, cancelButtonType, newButtonType);

        // Tham chiếu buttons
        Button okButton = (Button) dialog.getDialogPane().lookupButton(okButtonType);
        Button newButton = (Button) dialog.getDialogPane().lookupButton(newButtonType);
        Button cancelButton = (Button) dialog.getDialogPane().lookupButton(cancelButtonType);

        // Thêm sự kiện nút bấm
        okButton.addEventFilter(ActionEvent.ACTION, (event) -> {
            if (!form.isValid()) {
                showAlert("Error", "Invalid Data", "Please check your input data.");
                event.consume();
            } else {
                form.persist();
                ItemStore.itemsPerLocation.get(ItemStore.currentLocation.getName().getValue()).add(model.mapToItem());
                ItemStore.visibleItems.setAll(ItemStore.itemsPerLocation.get(ItemStore.currentLocation.getName().getValue()));
            }
        });
        newButton.addEventFilter(ActionEvent.ACTION, (event) -> {
            if (!form.isValid()) {
                showAlert("Error", "Invalid Data", "Please check your input data.");
            } else {
                form.persist();
                ItemStore.itemsPerLocation.get(ItemStore.currentLocation.getName().getValue()).add(model.mapToItem());
                ItemStore.visibleItems.setAll(ItemStore.itemsPerLocation.get(ItemStore.currentLocation.getName().getValue()));
            }
            event.consume();
        });

        // Tạo HBox chứa các nút
        HBox buttonBox = new HBox(10.0, okButton, newButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER);
        return buttonBox;
    }


    private HBox configDialogButtonUpdateItemInfo(Dialog<?> dialog,Form form, BindingNewItem model) {
        ButtonType submitButtonType = new ButtonType("Submit", ButtonBar.ButtonData.APPLY);
        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(submitButtonType, cancelButtonType);

        Button submitButton = (Button) dialog.getDialogPane().lookupButton(submitButtonType);
        Button cancelButton = (Button) dialog.getDialogPane().lookupButton(cancelButtonType);

        submitButton.addEventFilter(ActionEvent.ACTION, (event) -> {
            if (!form.isValid()) {
                showAlert("Error", "Invalid Data", "Please check your input data.");
                event.consume();
            } else {
                form.persist();
                Item item = model.mapToItem();
                ItemStore.itemsPerLocation.get(ItemStore.currentLocation.getName().getValue()).forEach((i) -> {
                    if (i.getId().getValue().equals(item.getId().getValue())) {
                        i.copyFromOtherItem(item);
                    }
                });
                ItemStore.visibleItems.setAll(ItemStore.itemsPerLocation.get(ItemStore.currentLocation.getName().getValue()));
            }
        });

        HBox buttonBox = new HBox(10.0, submitButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER);
        return buttonBox;
    }


    private void configDialogButtonUpdateInventory(Dialog<?> stockDialog, Form form ,Item item, IntegerProperty currentQuantity, SingleSelectionField<String> stockLocationField, IntegerProperty inOutQuantity, StringProperty comment, TableView<Inventory> stockHistoryTable) {
        ButtonType applyButtonType = new ButtonType("Apply", ButtonBar.ButtonData.APPLY);
        stockDialog.getDialogPane().getButtonTypes().addAll(applyButtonType, ButtonType.CLOSE);
        Button applyButton = (Button) stockDialog.getDialogPane().lookupButton(applyButtonType);
        applyButton.addEventFilter(ActionEvent.ACTION, event -> {
            if(!form.isValid()){
                showAlert("Error", "Invalid Data", "Please check your input data.");
                event.consume();
                return;
            }
            form.persist();
            currentQuantity.set(currentQuantity.get() + inOutQuantity.get());
            Inventory inventory = new Inventory(1,item.getId().getValue(),"abc", LocalDateTime.now(), stockLocationField.getSelection(),inOutQuantity.get(), currentQuantity.get(),comment.get());
            ItemStore.inventories.get(item.getId().getValue()).add(inventory);
            stockHistoryTable.getItems().add(inventory);
            
            ItemQuantity itemQuantity = ItemStore.itemsPerLocation.get(stockLocationField.getSelection()).stream();
            itemQuantity.getQuantity().set(currentQuantity.get());
            item.getQuantityPerLocation().removeIf(quantity -> quantity.getLocationName().getValue().equals(stockLocationField.getSelection()));
            item.getQuantityPerLocation().add(itemQuantity);
            if(stockLocationField.getSelection().equals(ItemStore.currentLocation.getName().getValue()))
                item.getQuantityAtCurrentLocation().set(currentQuantity.get());
            event.consume();
        });
    }


    // Hiển thị thông báo lỗi
    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
