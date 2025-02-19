package com.pos.app.util;

import com.dlsc.formsfx.model.structure.Field;
import com.dlsc.formsfx.model.structure.Form;
import com.dlsc.formsfx.model.structure.Group;
import com.dlsc.formsfx.view.controls.SimpleRadioButtonControl;
import com.dlsc.formsfx.view.renderer.FormRenderer;
import com.pos.app.component.CurrencyInput;
import com.pos.app.component.ImageUpload;
import com.pos.app.model.BindingNewItem;
import com.pos.app.model.Item;
import com.pos.app.store.ItemStore;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Objects;
import java.util.Optional;

public class ItemManager {
    private final Dialog<Item> itemDialog;

    private final VBox container;

    private final HBox createButtonBox;

    private final HBox updateButtonBox;

    private final Form newItemForm;

    private BindingNewItem newItemModel;

    public ItemManager() {
        newItemModel =  new BindingNewItem();   // Khởi tạo model
        newItemForm = createForm();      // Tạo form

        // Tạo renderer cho form
        FormRenderer formRenderer = new FormRenderer(newItemForm);
        formRenderer.setPrefSize(600, 600);
        try {
            formRenderer.getStylesheets().add(Objects.requireNonNull(getClass().getClassLoader().getResource("css/styles.css")).toExternalForm());
        } catch (Exception e) {
            e.printStackTrace();
        }


        
        // Tạo container chứa form
        container = new VBox(10);
        container.getChildren().add(formRenderer);

        // Đưa VBox vào ScrollPane
        ScrollPane scrollPane = new ScrollPane(container);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        
        // Khởi tạo dialog
        itemDialog = new Dialog<>();
        itemDialog.getDialogPane().setContent(scrollPane);     // Thêm form vào Dialog
        itemDialog.setWidth(650);
        itemDialog.setHeight(600);

        createButtonBox =  configDialogButtonNewItem(newItemForm, newItemModel);
        updateButtonBox = configDialogButtonUpdateItemInfo(newItemForm, newItemModel);
    }



    // Xử lý khi người dùng chọn tạo item mới
    @FXML
    public void createItem() {
        newItemModel.clear(); // Clear dữ liêu cũ
        itemDialog.setTitle("Create new item");  //Set title phù hợp cho dialog

        container.getChildren().remove(updateButtonBox); // Xóa nút cập nhật nếu có
        container.getChildren().add(createButtonBox);    // Thêm nút tạo mới vào form

        // Xử lý dữ liệu khi bấm
        Optional<Item> result = itemDialog.showAndWait();
        result.ifPresent(item -> {
            ItemStore.items.add(item);
            ItemStore.visibleItems.add(item);
        });
    }

    @FXML
    public void updateItemInfo(Item item){
        newItemModel.mapFromItem(item);    // Map dữ liệu từ item vào model để hiển thị lên form
        itemDialog.setTitle("Update Item");   // Set title phù hợp cho dialog

        container.getChildren().remove(createButtonBox); // Xóa nút tạo mới nếu có
        container.getChildren().add(updateButtonBox);    // Thêm nút cập nhật vào form

        Optional<Item> result = itemDialog.showAndWait();
        result.ifPresent(updateItem -> {
            ItemStore.items.add(updateItem);
            ItemStore.visibleItems.add(updateItem);
        });
    }


    // Tạo form để thêm mới và cập nhật item
    private Form createForm() {
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

                        Field.ofIntegerType(newItemModel.getStockQuantity())
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

    private HBox configDialogButtonNewItem(Form newItemForm, BindingNewItem newItemModel) {
        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType newButtonType = new ButtonType("New", ButtonBar.ButtonData.FINISH);
        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        itemDialog.getDialogPane().getButtonTypes().addAll(okButtonType, cancelButtonType, newButtonType);

        // Tham chiếu buttons
        Button okButton = (Button) itemDialog.getDialogPane().lookupButton(okButtonType);
        Button newButton = (Button) itemDialog.getDialogPane().lookupButton(newButtonType);
        Button cancelButton = (Button) itemDialog.getDialogPane().lookupButton(cancelButtonType);

        // Thêm sự kiện nút bấm
        okButton.addEventFilter(ActionEvent.ACTION, event -> handleFormSubmission(event, newItemForm, newItemModel));
        newButton.addEventFilter(ActionEvent.ACTION, event -> handleNewItemSubmission(event, newItemForm, newItemModel));

        // Tạo HBox chứa các nút
        HBox buttonBox = new HBox(10.0, (Node) okButton, (Node) newButton, (Node) cancelButton);
        buttonBox.setAlignment(Pos.CENTER);
        return buttonBox;
    }


    private HBox configDialogButtonUpdateItemInfo(Form newItemForm, BindingNewItem newItemModel) {
        ButtonType submitButtonType = new ButtonType("Submit", ButtonBar.ButtonData.APPLY);
        itemDialog.getDialogPane().getButtonTypes().addAll(submitButtonType);

        Button submitButton = (Button) itemDialog.getDialogPane().lookupButton(submitButtonType);

        submitButton.addEventFilter(ActionEvent.ACTION, event -> handleUpdateItemInfo(event, newItemForm, newItemModel));

        HBox buttonBox = new HBox(10.0, (Node) submitButton);
        buttonBox.setAlignment(Pos.CENTER);
        return buttonBox;
    }

    // Xử lý sự kiện bấm nút Ok
    private void handleFormSubmission(ActionEvent event, Form form, BindingNewItem model) {
        if (!form.isValid()) {
            showAlert("Error", "Invalid Data", "Please check your input data.");
            event.consume();
        } else {

            newItemForm.persist();
            ItemStore.items.add(newItemModel.mapToItem());
            ItemStore.visibleItems.add(newItemModel.mapToItem());
        }
    }

    // Xử lý sự kiện bấm nút New (tạo item mới nhưng không đóng dialog)
    private void handleNewItemSubmission(ActionEvent event, Form form, BindingNewItem model) {
        if (!form.isValid()) {
            showAlert("Error", "Invalid Data", "Please check your input data.");
        } else {
            form.persist();
            ItemStore.items.add(model.mapToItem());
            ItemStore.visibleItems.add(model.mapToItem());
        }
        event.consume();
    }

    // Xử lý sự kiện bấm nút Submit
    private void handleUpdateItemInfo(ActionEvent event, Form form,  BindingNewItem model) {
        if (!form.isValid()) {
            showAlert("Error", "Invalid Data", "Please check your input data.");
            event.consume();
        } else {
            form.persist();
        }
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
