package com.pos.app.util;

import com.dlsc.formsfx.model.structure.*;
import com.pos.app.api.ItemsApi;
import com.pos.app.model.*;

import com.dlsc.formsfx.model.validators.IntegerRangeValidator;
import com.dlsc.formsfx.view.controls.SimpleRadioButtonControl;
import com.dlsc.formsfx.view.renderer.FormRenderer;
import com.pos.app.component.CurrencyInput;
import com.pos.app.component.ImageUpload;
import com.pos.app.store.ItemStore;
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.xml.bind.JAXBException;
import javax.xml.bind.PropertyException;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ItemManager {
    final List<Item> items = ImportExportFile.getItems();
    final ItemsApi itemsApi = new ItemsApi();

    public void getItemsData(){
        // Lấy dữ liệu từ API
        List<Item> items = itemsApi.getItems();
        ItemStore.items.addAll(items);
        ItemStore.visibleItems.addAll(items);
    }


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
        IntegerProperty currentQuantity = new SimpleIntegerProperty(item.getQuantity().getValue());
        IntegerProperty inOutQuantity = new SimpleIntegerProperty(0);
        StringProperty comment = new SimpleStringProperty("");

        // Tạo danh sách lịch sử thay đổi số lượng item theo từng vị trí
        List<Inventory> inventoriesForThisItem = new ArrayList<>();
        if (!ItemStore.inventories.containsKey(item.getId().getValue())) {
            ItemStore.inventories.put(item.getId().getValue(), new ArrayList<>());
        }
        inventoriesForThisItem.addAll(ItemStore.inventories.get(item.getId().getValue()));


        // Tạo TableView để hiển thị lịch sử số lượng item
        TableView<Inventory> stockHistoryTable = new TableView<>();
        stockHistoryTable.setPrefSize(650, 500);
        TableColumn<Inventory, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getTimestamp().getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))));
        TableColumn<Inventory, String> userCol = new TableColumn<>("User");
        userCol.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getUsername().getValue()));
        TableColumn<Inventory, String> changeCol = new TableColumn<>("In/Out");
        changeCol.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getInventory().getValue().toString()));
        TableColumn<Inventory, String> quantityCol = new TableColumn<>("After quantity");
        quantityCol.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getAfterInventory().getValue().toString()));
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
        currentQuantityField.valueProperty().addListener((observable, oldValue, newValue) ->
            inOutQuantityField.validate(IntegerRangeValidator.atLeast(-newValue.intValue() ,"Can't add value smaller than current inventory")));


         // Thêm dữ liệu vào TableView
        stockHistoryTable.getItems().addAll(inventoriesForThisItem);

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

        configDialogButtonUpdateInventory(stockDialog, stockHistoryForm ,item, currentQuantity , inOutQuantity, comment, inventoriesForThisItem, stockHistoryTable);

        body.getChildren().add(formRenderer);
        body.getChildren().add(stockHistoryTable);
        stockHistoryContainer.getChildren().addAll(body);
        stockDialog.getDialogPane().setContent(stockHistoryContainer);
        stockDialog.showAndWait();
    }




    // Xử lý khi người dùng muốn xem lịch sử thay đổi số lượng item
//    @FXML
//    public void stockHistory(Item item){
//
//    }

    // Khởi tạo các thành phần cần thiết để tạo mới hoặc cập nhật item
    private void initItemForm(int type, Item item) {
        final BindingNewItem newItemModel =  item.mapToBindingNewItem();   // Khởi tạo model

        // Kiểm tra item là mới hay đã có dựa trên tên item
        if (item.getItemName().getValue().isBlank()){
            // Nếu item mới thì số lượng tại mỗi vị trí sẽ là 0
           newItemModel.getQuantity().set(0);
        } else {
            ItemStore.items.forEach((one) -> {
                if (one.getItemName().getValue().equals(newItemModel.getItemName().getValue())) {
                    newItemModel.getQuantity().set(one.getQuantity().getValue());
                }
            });
        }
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
                                .label("Tax")
                                .placeholder("Name of Tax:"),

                        Field.ofDoubleType(newItemModel.getTax1())
                                .label("")
                                .render(new CurrencyInput(2, "%")),

                        Field.ofIntegerType(newItemModel.getReceivingQuantity())
                                .label("Quantity"),
                        
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
        okButton.addEventFilter(ActionEvent.ACTION, (event) -> saveNewItem(event, form, model, true));
        newButton.addEventFilter(ActionEvent.ACTION, (event) -> saveNewItem(event, form, model, false));

        // Tạo HBox chứa các nút
        HBox buttonBox = new HBox(10.0, okButton, newButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER);
        return buttonBox;
    }


    // Lưu item mới vào danh sách item
    private void saveNewItem(Event event, Form form, BindingNewItem model, Boolean closeForm) {
        if (!form.isValid()) {
            showAlert("Error", "Invalid Data", "Please check your input data.");
            event.consume();
        }else {
            form.persist();
            Item newItem = model.mapToItem();
            // Nếu item đã tồn tại thì không cho phép tạo mới
            ItemStore.items.forEach((one) -> {
                if (one.getItemName().getValue().equals(newItem.getItemName().getValue())) {
                    showAlert("Error", "Item already exists", "Please check your input data.");
                    event.consume();
                }
            });

            // Nếu item chưa tồn tại thì thêm mới
            ItemStore.items.add(newItem); // Thêm item mới vào danh sách item
            ItemStore.visibleItems.add(newItem); // Thêm item mới vào danh sách item hiển thị trên bảng
        }
        // Nếu closeForm = true thì đóng form, ngược lại thì không đóng
        if (!closeForm) {
            event.consume();
        }
    }


    // Cấu hình nút bấm cho việc cập nhật thông tin item
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
                Item item = model.mapToItem();   // Chuyển từ BindingNewItem sang Item
                ItemStore.items.forEach(one -> {
                    if (one.getId().getValue().equals(item.getId().getValue())) {
                        one.copyFromOtherItem(item); // Nếu đã tồn tại thì cập nhật thông tin
                    }
                });
            }
        });

        HBox buttonBox = new HBox(10.0, submitButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER);
        return buttonBox;
    }


    private void configDialogButtonUpdateInventory(Dialog<?> stockDialog, Form form ,Item item, IntegerProperty currentQuantity, IntegerProperty inOutQuantity, StringProperty comment, List<Inventory> inventoriesForThisItem, TableView<Inventory> stockHistoryTable) {
        ButtonType applyButtonType = new ButtonType("Apply", ButtonBar.ButtonData.APPLY);
        stockDialog.getDialogPane().getButtonTypes().addAll(applyButtonType, ButtonType.CLOSE);
        Button applyButton = (Button) stockDialog.getDialogPane().lookupButton(applyButtonType);


        // Xử lý khi người dùng bấm nút Apply
        applyButton.addEventFilter(ActionEvent.ACTION, event -> {
            // Kiểm tra dữ liệu nhập vào có hợp lệ không, nếu không hiển thị thông báo lỗi
            if(!form.isValid()){
                showAlert("Error", "Invalid Data", "Please check your input data.");
                event.consume();
                return;
            }
            // Nếu dữ liệu hợp lệ
            form.persist();
            currentQuantity.set(currentQuantity.get() + inOutQuantity.get());

            // Tạo inventory mới ->  thêm vào danh sách inventory trong ItemStore và bảng lịch sử
            Inventory inventory = new Inventory(1,item.getId().getValue(),"abc", LocalDateTime.now(), "",inOutQuantity.get(), currentQuantity.get(),comment.get());
            ItemStore.inventories.get(item.getId().getValue()).add(inventory);
            inventoriesForThisItem.add(inventory);
            stockHistoryTable.getItems().add(inventory);
            // Cập nhật lại số lượng item trong danh sách items
            ItemStore.items.forEach(one -> {
                if (one.getId().getValue().equals(item.getId().getValue())) {
                    one.getQuantity().set(currentQuantity.get());
                }
            });
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

    public void exportFileForm(ComboBox<String> exportFileBtn) {
        // Thêm các lựa chọn nếu chưa có
        if (exportFileBtn.getItems().isEmpty()) {
            exportFileBtn.getItems().addAll("JSON", "MS-Excel", "CSV", "PDF", "XML", "TXT", "SQL");
        }

        // Sự kiện khi chọn
        exportFileBtn.setOnAction(event -> {
            String selectedOption = exportFileBtn.getValue();
            if (selectedOption != null) {
                switch (selectedOption) {
                    case "JSON":
                        try {
                            File fileJSON = fileFormat("items.json");
                            ImportExportFile.exportJSON(items, fileJSON.getAbsolutePath());
                            System.out.println("da chon json");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        break;

                    case "MS-Excel":
                        try {
                            File fileExcel = fileFormat("items_" + ".xlsx");
                            ImportExportFile.exportExcel(items, fileExcel.getAbsolutePath());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        break;

                    case "CSV":
                        try {
                            File fileCsv = fileFormat("items.csv");
                            ImportExportFile.exportCSV(items, fileCsv.getAbsolutePath());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        break;

                    case "PDF":
                        try {
                            File filePDF = fileFormat("items.pdf");
                            ImportExportFile.exportPDF(items, filePDF.getAbsolutePath());
                            System.out.println("Da chon pdf");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        break;

                    case "XML":
                        try {
                            File fileXML = fileFormat("items.xml");
                            ImportExportFile.exportXML(items, fileXML.getAbsolutePath());
                        } catch (IOException | PropertyException e) {
                            throw new RuntimeException(e);
                        } catch (JAXBException e) {
                            throw new RuntimeException(e);
                        }
                        break;

                    case "TXT":
                        try {
                            File fileTXT = fileFormat("items.txt");
                            ImportExportFile.exportTxt(items, fileTXT.getAbsolutePath());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        break;

                    case "SQL":
                        try {
                            File fileSQL = fileFormat("items.sql");
                            ImportExportFile.exportSQL(items, fileSQL.getAbsolutePath());
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
