package com.pos.app.util;

import com.dlsc.formsfx.model.structure.Field;
import com.dlsc.formsfx.model.structure.Form;
import com.dlsc.formsfx.model.structure.Group;
import com.dlsc.formsfx.model.validators.IntegerRangeValidator;
import com.dlsc.formsfx.view.controls.SimpleRadioButtonControl;
import com.dlsc.formsfx.view.renderer.FormRenderer;
import com.fasterxml.jackson.core.filter.TokenFilter;
import com.pos.app.component.CurrencyInput;
import com.pos.app.component.ImageUpload;
import com.pos.app.model.*;
import com.pos.app.store.ItemStore;
import com.sun.jna.platform.win32.*;
import com.sun.jna.ptr.PointerByReference;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


public class ItemManager {
    private Dialog<Item> itemDialog;

    private VBox container;

    private Form newItemForm;

    private ScrollPane scrollPane;

    private BindingNewItem newItemModel;

    final List<Item> items = ImportExportFile.getItems();

    public ItemManager() {
        newItemModel = new BindingNewItem();   // Khởi tạo model
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
        scrollPane = new ScrollPane(container);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        // Khởi tạo dialog
        itemDialog = new Dialog<>();
        itemDialog.getDialogPane().setContent(scrollPane);     // Thêm form vào Dialog
        itemDialog.setWidth(650);
        itemDialog.setHeight(600);
    }

    // Xử lý khi người dùng chọn tạo item mới
    @FXML
    public void createItem() {
        newItemModel.clear(); // Clear dữ liêu cũ
        itemDialog.setTitle("Create new item");  //Set title phù hợp cho dialog

        HBox createButtonBox = configDialogButtonNewItem(newItemForm, newItemModel, itemDialog);

        container.getChildren().add(createButtonBox);    // Thêm nút tạo mới vào form

        // Xử lý dữ liệu khi bấm
        Optional<Item> result = itemDialog.showAndWait();
        result.ifPresent(item -> {
            ItemStore.items.add(item);
            ItemStore.visibleItems.add(item);
        });
    }

    @FXML
    public void updateItemInfo(Item item) {
        container = new VBox(10);
        newItemModel.mapFromItem(item);    // Map dữ liệu từ item vào model để hiển thị lên form
        itemDialog.setTitle("Update Item");   // Set title phù hợp cho dialog

        HBox updateButtonBox = configDialogButtonUpdateItemInfo(newItemForm, newItemModel, itemDialog);

        container.getChildren().add(updateButtonBox);    // Thêm nút cập nhật vào form

        Optional<Item> result = itemDialog.showAndWait();
        result.ifPresent(updateItem -> {
            ItemStore.items.add(updateItem);
            ItemStore.visibleItems.add(updateItem);
        });
    }

    // Xử lý khi người dùng muốn cập nhật số lượng item
    @FXML
    public void updateInventory(Item item) {
        // Object để lưu dữ liệu nhập vào form
        BindingUpdateInventory newUpdateInventoryModel = new BindingUpdateInventory();

        // Tạo form để nhập dữ liệu
        Form newUpdateInventoryForm = Form.of(
                Group.of(
                        Field.ofStringType(item.getBarcode())
                                .label("Barcode")
                                .editable(false),

                        Field.ofStringType(item.getItemName())
                                .label("Item Name")
                                .editable(false),

                        Field.ofStringType(item.getCategory())
                                .label("Category")
                                .editable(false),

                        Field.ofSingleSelectionType(item.getQuantityPerLocation().stream().map(ItemQuantity::getLocationName).collect(Collectors.toList()))
                                .label("Stock location"),

                        Field.ofIntegerType(item.getQuantityAtCurrentLocation().getQuantity())
                                .label("Current Quantity")
                                .editable(false),

                        Field.ofIntegerType(newUpdateInventoryModel.getInventoryToAddOrSubtract())
                                .label("Inventory to add or subtract")
                                .required("Quantity is a required field")
                                .validate(IntegerRangeValidator.atLeast(-newUpdateInventoryModel.getCurrentQuantity().get()
                                        , "Can't add value smaller than current inventory")),

                        Field.ofStringType(newUpdateInventoryModel.getComment())
                                .label("Comment")
                )

        );
        Dialog dialogInventory = new Dialog<>();
        dialogInventory.setTitle("Update Inventory");

        HBox buttonBox = configDialogButtonUpdateInventory(newUpdateInventoryForm, newUpdateInventoryModel, item, dialogInventory);

        FormRenderer formRenderer = new FormRenderer(newUpdateInventoryForm);
        formRenderer.setPrefSize(600, 600);
        try {
            formRenderer.getStylesheets().add(Objects.requireNonNull(getClass().getClassLoader().getResource("css/styles.css")).toExternalForm());
        } catch (Exception e) {
            e.printStackTrace();
        }

        container = new VBox(10);

        container.getChildren().add(formRenderer);
        container.getChildren().add(buttonBox);

        scrollPane = new ScrollPane(container);

        dialogInventory.getDialogPane().setContent(scrollPane);
        dialogInventory.showAndWait();
    }

    @FXML
    public void stockHistory(Item item) {
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

                        Field.ofSingleSelectionType(item.getQuantityPerLocation().stream().map(ItemQuantity::getLocationName).collect(Collectors.toList()))
                                .label("Stock location: "),

                        Field.ofIntegerType(item.getQuantityAtCurrentLocation().getQuantity())
                                .label("Current Quantity: ")
                                .editable(false)
                )
        );

        Dialog dialogStock = new Dialog<>();
        dialogStock.setTitle("Inventory Count Detail");

        container = new VBox(10);

        FormRenderer formRenderer = new FormRenderer(stockHistoryForm);
        formRenderer.setPrefSize(600, 600);
        try {
            formRenderer.getStylesheets().add(Objects.requireNonNull(getClass().getClassLoader().getResource("css/styles.css")).toExternalForm());
        } catch (Exception e) {
            e.printStackTrace();
        }

        container.getChildren().add(formRenderer);


        scrollPane = new ScrollPane(container);

        dialogStock.getDialogPane().setContent(scrollPane);
        dialogStock.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

        dialogStock.showAndWait();
    }

    @FXML
    public void openImportForm(Button importItemBtn) {
        // Tạo Dialog cho form import file
        Dialog<Void> importDialog = new Dialog<>();
        importDialog.setTitle("Import File Form");

        // Lấy cửa sổ chủ từ nút openFormButton
        Stage owner = (Stage) importItemBtn.getScene().getWindow();
        importDialog.initOwner(owner);

        // Tạo nút import file trong form
        Button importFileBtn = new Button("Import File");
        importFileBtn.addEventFilter(ActionEvent.ACTION, event -> ImportExportFile.handleImportFile(importDialog));

        // Sắp xếp các thành phần trong form
        VBox layout = new VBox(10, importFileBtn);
        layout.setAlignment(Pos.CENTER);
        importDialog.getDialogPane().setContent(layout);

        // Thêm nút Close để người dùng thoát Dialog
        importDialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        importDialog.showAndWait();
    }

    @FXML
    public void exportFileForm(ComboBox<String> exportFileBtn) {
        // Thêm các lựa chọn nếu chưa có
        if (exportFileBtn.getItems().isEmpty()) {
            exportFileBtn.getItems().addAll("JSON", "Excel", "CSV");
        }

        // Sự kiện khi chọn
        exportFileBtn.setOnAction(event -> {
            String selectedOption = exportFileBtn.getValue();
            if (selectedOption != null) {
                switch (selectedOption) {
                    case "JSON":
                       try {
                           File fileJSON = fileFormat("item.json");
                           ImportExportFile.handleJsonOption(items, fileJSON.getAbsolutePath());
                       } catch (IOException e) {
                           throw new RuntimeException(e);
                       }
                        break;
                    case "Excel":
                        try {
                            File fileExcel = fileFormat("item.xlsx");
                            ImportExportFile.handleExcelOption(items, fileExcel.getAbsolutePath());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case "CSV":
                        try {
                            File fileCsv = fileFormat("item.csv");
                            ImportExportFile.handleCsvOption(items, fileCsv.getAbsolutePath());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    default:
                        System.out.println("Không có lựa chọn này");
                }
            }
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
                                .label("Supplier ID")
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

    private HBox configDialogButtonNewItem(Form newItemForm, BindingNewItem newItemModel, Dialog dialog) {
        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType newButtonType = new ButtonType("New", ButtonBar.ButtonData.FINISH);
        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, cancelButtonType, newButtonType);

        // Tham chiếu buttons
        Button okButton = (Button) dialog.getDialogPane().lookupButton(okButtonType);
        Button newButton = (Button) dialog.getDialogPane().lookupButton(newButtonType);
        Button cancelButton = (Button) dialog.getDialogPane().lookupButton(cancelButtonType);

        // Thêm sự kiện nút bấm
        okButton.addEventFilter(ActionEvent.ACTION, event -> handleFormSubmission(event, newItemForm, newItemModel));
        newButton.addEventFilter(ActionEvent.ACTION, event -> handleNewItemSubmission(event, newItemForm, newItemModel));
        cancelButton.addEventFilter(ActionEvent.ACTION, event -> closeForm(dialog));

        // Tạo HBox chứa các nút
        HBox buttonBox = new HBox(10.0, (Node) okButton, (Node) newButton, (Node) cancelButton);
        buttonBox.setAlignment(Pos.CENTER);
        return buttonBox;
    }


    private HBox configDialogButtonUpdateItemInfo(Form newItemForm, BindingNewItem newItemModel, Dialog dialog) {
        ButtonType submitButtonType = new ButtonType("Submit", ButtonBar.ButtonData.APPLY);
        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(submitButtonType, cancelButtonType);

        Button submitButton = (Button) dialog.getDialogPane().lookupButton(submitButtonType);
        Button cancelButton = (Button) dialog.getDialogPane().lookupButton(cancelButtonType);

        submitButton.addEventFilter(ActionEvent.ACTION, event -> handleUpdateItemInfo(event, newItemForm, newItemModel));
        cancelButton.addEventFilter(ActionEvent.ACTION, event -> closeForm(dialog));

        HBox buttonBox = new HBox(10.0, (Node) submitButton, (Node) cancelButton);
        buttonBox.setAlignment(Pos.BOTTOM_RIGHT);
        return buttonBox;
    }

    private HBox configDialogButtonUpdateInventory(Form newIventoryForm, BindingUpdateInventory updateInventory, Item item, Dialog dialog) {

        ButtonType submitButtonType = new ButtonType("Submit", ButtonBar.ButtonData.APPLY);
        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(submitButtonType, cancelButtonType);

        Button submitButton = (Button) dialog.getDialogPane().lookupButton(submitButtonType);
        Button cancelButton = (Button) dialog.getDialogPane().lookupButton(cancelButtonType);

        submitButton.addEventFilter(ActionEvent.ACTION, event -> handleUpdateInventory(event, newIventoryForm, updateInventory, item));
        System.out.println("Goi den su kien submit");
        cancelButton.addEventFilter(ActionEvent.ACTION, event -> closeForm(dialog));


        HBox buttonBox = new HBox(10.0, (Node) submitButton, (Node) cancelButton);
        buttonBox.setAlignment(Pos.BOTTOM_RIGHT);
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

    // Xử lý sự kiện bấm nút Submit của Update Item
    private void handleUpdateItemInfo(ActionEvent event, Form form, BindingNewItem model) {
        if (!form.isValid()) {
            showAlert("Error", "Invalid Data", "Please check your input data.");
            event.consume();
        } else {
            form.persist();
            ItemStore.items.add(model.mapToItem());
            ItemStore.visibleItems.add(model.mapToItem());
        }
    }

    // Xử lý sự kiện bấm nút Submit của Update Inventory
    private void handleUpdateInventory(ActionEvent event, Form form, BindingUpdateInventory updateInventory, Item item) {
        if (!form.isValid()) {
            showAlert("Error", "Invalid Data", "Please check your input data");
            event.consume();
        } else {
            form.persist();
            ItemStore.inventoryList.add(updateInventory.mapToUpdateInventory(item));
            ItemStore.visibleItems.add(newItemModel.mapToItem());
        }
    }

    // Xử lý sự kiện bấm nút Cancel
    private void closeForm(Dialog<Void> dialog) {
        if (dialog != null && dialog.isShowing()) {
            dialog.close();
        }
    }



    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private File fileFormat(String format) {
        // Lấy thư mục download của người dùng
        String downloadsPath = DownloadsFolderUtil.getDownloadsFolder();

        // Tạo đối tượng File cho thư mục Downloads
        File downloadsFolder = new File(downloadsPath);

        // Kiểm tra và tạo thư mục nếu chưa tồn tại
        if (!downloadsFolder.exists()) {
            downloadsFolder.mkdirs();
        }

        // Tạo file items.xlsx trong thư mục Downloads
        File file = new File(downloadsFolder, format);
        if (file.exists()) {
            // Sử dụng "\\." để escape dấu chấm trong regex
            String[] parts = format.split("\\.");

            String baseName = parts[0];
            String extension = parts[1];
            int i = 1;
            while(file.exists()){
                    // Ghép lại tên file với số thứ tự và dấu chấm giữa tên và phần mở rộng
                    file = new File(downloadsFolder, baseName + "(" + i + ")." + extension);
                    i++;
                }
            }
        return file;
    }
}
