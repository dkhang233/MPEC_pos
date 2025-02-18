package com.pos.app.controller;

import com.dlsc.formsfx.model.structure.*;
import com.dlsc.formsfx.model.validators.*;
import com.dlsc.formsfx.view.controls.SimpleRadioButtonControl;
import com.dlsc.formsfx.view.renderer.FormRenderer;
import com.pos.app.component.CurrencyInput;
import com.pos.app.component.ImageUpload;
import com.pos.app.model.*;
import com.pos.app.store.ItemStore;
import com.pos.app.util.FormatHelper;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;

import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
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
    private TableView<Item> itemsTable;
    
    @FXML
    private TableColumn<Item, Double> wholeSalePriceCol;

    @FXML
    private TableColumn<Item, String> quantityCol;

    @FXML
    private TableColumn<Item, String> avatarCol;

    @FXML
    private TableColumn<Item, String> updateInventoryCol;

    @FXML
    private TableColumn<Item, String> stockHistoryCol;

    @FXML
    private TableColumn<Item, String> updateItemCol;

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

    private Dialog<Inventory> dialogInventory;
    
    @FXML
    private Button newItem;

    // Hàm khởi tạo, chạy khi view được load
    @FXML
    public void initialize() {
        setupItemsTable(); // Khởi tạo bảng items
        setupItemsPagination(); // Khởi tạo phân trang
    }

    
    // Khởi tạo bảng items
    private void setupItemsTable(){
        // Tùy chỉnh cột Wholesale Price để hiển thị giá tiền
        wholeSalePriceCol.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (empty || price == null) {
                    setText(null);
                } else {
                    setText(FormatHelper.formatDecimalNumber(price));
                }
            }
        });

        ItemManager itemManager = new ItemManager();

        newItem.setOnAction(event -> {
            itemManager.createItem();
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
                    updateInventory(item);
                });
            }

            @Override
            protected void updateItem(String cell, boolean empty) {
                super.updateItem(cell, empty);
                if(super.isEmpty())
                    setGraphic(null);
                else
                    setGraphic(updateInventoryColBtn);
            }
        });

        // Tùy chỉnh cột stock history để hiển thị button thay vì text
        stockHistoryCol.setCellFactory(col -> new TableCell<>() {
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
        updateItemCol.setCellFactory(col -> new TableCell<>() {
            final Button updateItemBtn = new Button("Update item ");

            {
                updateItemBtn.getStyleClass().addAll("btn-custom");
                updateItemBtn.setOnAction(event -> {
                    Item item = getTableView().getItems().get(getIndex());
                    System.out.println("Update item for id: " + item.getId());
                    itemManager.UpdateItem(item);
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

        
        // Tùy chỉnh cột quantity để hiển thị số lượng item tại vị trí hiện tại
        quantityCol.setCellValueFactory(cellData -> {
           int itemQuantity = 0;
           for(var quantity : cellData.getValue().getQuantityPerLocation()){
               if (quantity.getLocationName().equals(ItemStore.currentLocation)){
                   itemQuantity = quantity.getQuantity();
                   break;
               }
           }
           return new SimpleStringProperty(String.valueOf(itemQuantity));
        });


        this.deleteItemBtn.disableProperty().bind(this.itemsTable.getSelectionModel().selectedItemProperty().isNull()); // Disable button xóa khi không có dòng nào được chọn
        setupColVisible(); // Khởi tạo các checkbox để chọn cột hiển thị
        itemsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); // Cho phép chọn nhiều dòng
        int cols = itemsTable.getColumns().size(); // Số cột của bảng
        itemsTable.getColumns().forEach(col -> col.prefWidthProperty().bind(itemsTable.widthProperty().divide(cols).subtract(0.65))); // Tự động thay đổi kích thước cột khi thay đổi kích thước bảng
        itemsTable.setItems(ItemStore.visibleItems);  // Thêm dữ liệu vào bảng
    }

    public Button getImportItemBtn() {
        return importItemBtn;
    }

    public void setImportItemBtn(Button importItemBtn) {
        this.importItemBtn = importItemBtn;
    }

    public static class ItemManager {
        private Dialog<Item> dialogItem;
        private ScrollPane scrollPane;
        private List<Item> items = new ArrayList<>();
        private final List<Item> visibleItems = new ArrayList<>();
        private Form newItemForm = createForm(new Item());
        private BindingNewItem newItemModel = new BindingNewItem();


        // Xử lý khi người dùng chọn tạo item mới
        @FXML
        private void createItem() {
                newItemForm = createForm(new Item());
                items = new ArrayList<>();
                // Set size và tạo scroll pane để làm container cho form (giữ cho form có kích thước phù hợp)
                FormRenderer formRenderer = new FormRenderer(newItemForm);
                formRenderer.setPrefSize(600, 600);
                try {
                    formRenderer.getStylesheets().add(Objects.requireNonNull(getClass().getClassLoader().getResource("css/styles.css")).toExternalForm());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Tạo Dialog
                dialogItem = new Dialog<>();
                dialogItem.setTitle("Create new item");
                dialogItem.setWidth(650);
                dialogItem.setHeight(600);

                //Tạo container chứa form và các nút
                VBox container = new VBox(10);
                container.getChildren().add(formRenderer);

                //Thêm các nút vào form
                HBox buttonBox = configDialogButtonNewItem(newItemForm, newItemModel);
                container.getChildren().add(buttonBox);

                // Đưa VBox vào ScrollPane
                scrollPane = new ScrollPane(container);
                scrollPane.setFitToWidth(true);
                scrollPane.setFitToHeight(true);

                // Thêm form vào Dialog
                dialogItem.getDialogPane().setContent(scrollPane);
                
               // Xử lý dữ liệu khi bấm
               dialogItem.setResultConverter(button -> processDialogResult(button, newItemForm, newItemModel));
               Optional<Item> result = dialogItem.showAndWait();
               result.ifPresent(item -> {
                   items.add(item);
                   visibleItems.add(item);
                   System.out.println("Function xu ly du lieu da duoc goi");
            });
        }

        @FXML
        private void UpdateItem(Item item){
            Form newItemForm = createForm(item);
            FormRenderer formRenderer = new FormRenderer(newItemForm);



        // Thêm form vào Dialog
        dialogItem.getDialogPane().setContent(scrollPane);

            dialogItem = new Dialog<>();
            dialogItem.setTitle("Update Item");
            dialogItem.setHeight(600);
            dialogItem.setWidth(650);

            VBox container = new VBox();
            container.getChildren().add(formRenderer);

            HBox buttonBox = configDialogButtonUpdateItem(newItemForm, newItemModel);
            container.getChildren().add(buttonBox);

            scrollPane = new ScrollPane(container);
            scrollPane.setFitToWidth(true);
            scrollPane.setFitToHeight(true);

            dialogItem.getDialogPane().setContent(scrollPane);

            dialogItem.setResultConverter(button -> processDialogResult(button, newItemForm, newItemModel) );
        }

        private Form createForm(Item item) {
            BindingNewItem newItemModel = new BindingNewItem();
            newItemModel.mapFromItem(item);
            // Trường avatar cần tùy chỉnh để cho phép chọn và hiển thị hình ảnh
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
            dialogItem.getDialogPane().getButtonTypes().addAll(okButtonType, cancelButtonType, newButtonType);

            // Tham chiếu buttons
            Button okButton = (Button) dialogItem.getDialogPane().lookupButton(okButtonType);
            Button newButton = (Button) dialogItem.getDialogPane().lookupButton(newButtonType);
            Button cancelButton = (Button) dialogItem.getDialogPane().lookupButton(cancelButtonType);

            // Thêm sự kiện nút bấm
            okButton.addEventFilter(ActionEvent.ACTION, event -> handleFormSubmission(event, newItemForm, newItemModel));
            newButton.addEventFilter(ActionEvent.ACTION, event -> handleNewItemSubmission(event, newItemForm, newItemModel));

            String barcode = newItemModel.getBarcode().get();
            // Tạo HBox chứa các nút
            HBox buttonBox = new HBox(10.0, (Node) okButton, (Node) newButton, (Node) cancelButton);
            buttonBox.setAlignment(Pos.CENTER);
            return buttonBox;
        }


        private HBox configDialogButtonUpdateItem(Form newItemForm, BindingNewItem newItemModel) {
            ButtonType submitButtonType = new ButtonType("Submit", ButtonBar.ButtonData.APPLY);
            dialogItem.getDialogPane().getButtonTypes().addAll(submitButtonType);

            Button submitButton = (Button) dialogItem.getDialogPane().lookupButton(submitButtonType);

            submitButton.addEventFilter(ActionEvent.ACTION, event -> handleUpdateItem(event, newItemForm, newItemModel));

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

        // Xử lý sự kiện bấm nút New
        private void handleNewItemSubmission(ActionEvent event, Form form, BindingNewItem model) {
            if (!form.isValid()) {
                showAlert("Error", "Invalid Data", "Please check your input data.");
            } else {
                form.persist();
                items.add(model.mapToItem());
                visibleItems.add(model.mapToItem());
            }
            event.consume();
        }

        // Xử lý sự kiện bấm nút Submit
        private void handleUpdateItem(ActionEvent event, Form form,  BindingNewItem model) {
            if (!form.isValid()) {
                showAlert("Error", "Invalid Data", "Please check your input data.");
                event.consume();
            } else {
                form.persist();
            }
        }

        private Item processDialogResult(ButtonType button, Form form, BindingNewItem model) {
            if (button.getButtonData() == ButtonBar.ButtonData.OK_DONE && form.isValid()) {
                form.persist();
                System.out.println("Button ok work");
                return model.mapToItem();
            } else if (button.getButtonData() == ButtonBar.ButtonData.FINISH && form.isValid()) {
                form.persist();
                System.out.println("Button new work");
                return model.mapToItem();
            } else if (button.getButtonData() == ButtonBar.ButtonData.APPLY && form.isValid()) {
                form.persist();
                System.out.println("Button submit work");
            }
            return null;
//<<<<<<< HEAD
//        });
//
//        //Lấy kết quả
//        Optional<Item> result = dialogItem.showAndWait();
//        result.ifPresent(item -> {
//            ItemStore.items.add(item);
//            ItemStore.visibleItems.add(item);
//        });
//
//=======
        }

        private void showAlert(String title, String header, String content) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(header);
            alert.setContentText(content);
            alert.showAndWait();
        }
    }

    @FXML
    private void updateInventory(Item item){
        // Object để lưu dữ liệu nhập vào form
        BindingUpdateInventory newUpdateInventoryModel = new BindingUpdateInventory();
        
        // Tạo form để nhập dữ liệu
        Form newUpdateInventoryForm = Form.of(
                Group.of(
                        Field.ofStringType(item.getBarcode())
                                .label("Barcode")
                                .editable(false),

                        Field.ofStringType(item.getName())
                                .label("Item Name")
                                .editable(false),

                        Field.ofStringType(item.getCategory())
                                .label("Category")
                                .editable(false),

                        Field.ofSingleSelectionType(newUpdateInventoryModel.getStockLocation())
                                .label("Stock location"),

                        Field.ofIntegerType(item.getQuantityAtCurrentLocation().getQuantity())
                                .label("Current Quantity")
                                .editable(false),

                        Field.ofIntegerType(newUpdateInventoryModel.getInventoryToAddOrSubtract())
                                .label("Inventory to add or subtract")
                                .required("Quantity is a required field")
                                .validate(IntegerRangeValidator.atLeast(-newUpdateInventoryModel.getCurrentQuantity().get()
                                                                                  ,"Can't add value smaller than current inventory")),

                        Field.ofStringType(newUpdateInventoryModel.getComment())
                                .label("Comment")
                )

        );
        dialogInventory = new Dialog<>();

        ButtonType submitButtonType = new ButtonType("Submit", ButtonBar.ButtonData.APPLY);
        dialogInventory.getDialogPane().getButtonTypes().add(submitButtonType);

        Button submitButton = (Button)dialogInventory.getDialogPane().lookupButton(submitButtonType);
        submitButton.addEventFilter(ActionEvent.ACTION, event -> {
            if (!newUpdateInventoryForm.isValid()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid data");
                alert.setContentText("Please check your input data");
                alert.showAndWait();
                event.consume();
            }

            dialogInventory.setResultConverter(button -> {
                if(button == submitButtonType  && newUpdateInventoryForm.isValid()){
                    newUpdateInventoryForm.persist();
                    return newUpdateInventoryModel.mapToUpdateInventory(item);
                }
                return null;
            });
        });

        ScrollPane scrollPane = new ScrollPane();

        FormRenderer renderer = new FormRenderer(newUpdateInventoryForm);
        renderer.setPrefSize(600, 600);
        scrollPane.setContent(renderer);

        dialogInventory.setWidth(600);
        dialogInventory.setHeight(600);

        dialogInventory.getDialogPane().setContent(scrollPane);
        dialogInventory.showAndWait();
    }


    // Xử lý khi người dùng chọn xóa item
    @FXML
    private void deleteItem() {
        itemsTable.getSelectionModel().clearSelection(); // Bỏ chọn các dòng
    }



    private void addColumn(){

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
        for (TableColumn<Item, ?> col : itemsTable.getColumns()) {
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
        itemsPagination.setPageCount(10); // Số trang
    }
}
 