package com.pos.app.controller;

import com.dlsc.formsfx.model.structure.*;
import com.dlsc.formsfx.model.validators.CustomValidator;
import com.dlsc.formsfx.view.controls.SimpleRadioButtonControl;
import com.dlsc.formsfx.view.renderer.FormRenderer;
import com.pos.app.component.CurrencyInput;
import com.pos.app.component.ImageUpload;
import com.pos.app.model.Item;
import com.pos.app.model.BindingNewItem;
import com.pos.app.store.ItemStore;
import com.pos.app.util.FormatHelper;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.event.ActionEvent;
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
    // Nơi lưu trữ dữ liệu cho items
    private final ItemStore itemStore = new ItemStore();

    // Đường dẫn mặc định của avatar
    private final String defaultAvatar = Objects
            .requireNonNull(getClass().getClassLoader().getResource("static/default-item.png")).toExternalForm();
    
    @FXML
    private TableView<Item> itemsTable;

    // Danh sách các cột trong bảng
    private Map<Integer,TableColumn<Item, ?>> columns = new HashMap<>();

    @FXML
    private TableColumn<Item, Double> wholeSalePriceCol;

    @FXML
    private TableColumn<Item, String> quantityCol;

    @FXML
    private TableColumn<Item, String> avatarCol;

    @FXML
    private TableColumn<Item, String> updateStockCol;

    @FXML
    private TableColumn<Item, String> stockHistoryCol;

    @FXML
    private TableColumn<Item, String> updateItemCol;

    @FXML
    private Button deleteItemBtn;

    @FXML
    private Pagination itemsPagination;

    @FXML
    private VBox  columnsVisible;

    @FXML
    private ScrollPane  columnsVisibleContainer;

    private Dialog<Item> dialog;

    private Dialog<Item> dialog2;
    
    // Hàm khởi tạo, chạy khi view được load
    @FXML
    public void initialize() {
        columnsVisible.setSpacing(10);
        setupItemsTable(); // Khởi tạo bảng items
        setupItemsPagination(); // Khởi tạo phân trang
        setupForm(); // Khởi tạo form
        this.deleteItemBtn.disableProperty().bind(this.itemsTable.getSelectionModel().selectedItemProperty().isNull()); // Disable button xóa khi không có dòng nào được chọn
    }

    // Khi người dùng ấn nút "Show/hide" thì hiển thị bảng checkbox để người dùng chọn cột
    @FXML
    private void showColVisible(){
        columnsVisibleContainer.setVisible(!columnsVisibleContainer.isVisible());
    }

    

    // Khởi tạo bảng items
    private void setupItemsTable(){
        // Tùy chỉnh cột Wholesale Price để hiển thị giá tiền
        wholeSalePriceCol.setCellFactory(col -> new TableCell<Item, Double>() {
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

        // Tùy chỉnh cột avatar để hiển thị hỉnh ảnh thay vì text
        avatarCol.setCellFactory(col -> new TableCell<Item, String>() {
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
        updateStockCol.setCellFactory(col -> new TableCell<Item, String>() {
            final Button updateStockColBtn = new Button("Update stock");

            {
                updateStockColBtn.getStyleClass().addAll("btn-custom");
                updateStockColBtn.setOnAction(event -> {
                    Item item = getTableView().getItems().get(getIndex());
                    System.out.println("Update stock for item has id: " + item.getId());
                });
            }

            @Override
            protected void updateItem(String cell, boolean empty) {
                super.updateItem(cell, empty);
                if(super.isEmpty())
                    setGraphic(null);
                else
                    setGraphic(updateStockColBtn);
            }
        });

        // Tùy chỉnh cột stock history để hiển thị button thay vì text
        stockHistoryCol.setCellFactory(col -> new TableCell<Item, String>() {
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
        updateItemCol.setCellFactory(col -> new TableCell<Item, String>() {
            final Button updateItemBtn = new Button("Update item ");

            {
                updateItemBtn.getStyleClass().addAll("btn-custom");
                updateItemBtn.setOnAction(event -> {
                    Item item = getTableView().getItems().get(getIndex());
                    System.out.println("Update item for id: " + item.getId());
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

        quantityCol.setCellValueFactory(cellData -> {
           int itemQuantity = 0;
           for(var quantity : cellData.getValue().getQuantityPerLocation()){
               if (quantity.getLocationName().equals(itemStore.getCurrentLocation())){
                   itemQuantity = quantity.getQuantity();
                   break;
               }
           }
           return new SimpleStringProperty(String.valueOf(itemQuantity));
        });

        setupColVisible(); // Khởi tạo các checkbox để chọn cột hiển thị
        itemsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); // Cho phép chọn nhiều dòng
        int cols = itemsTable.getColumns().size(); // Số cột của bảng
        itemsTable.getColumns().forEach(col -> col.prefWidthProperty().bind(itemsTable.widthProperty().divide(cols).subtract(0.65))); // Tự động thay đổi kích thước cột khi thay đổi kích thước bảng
        itemsTable.setItems(itemStore.getVisibleItems());  // Thêm dữ liệu vào bảng
    }



    // Xử lý khi người dùng chọn tạo item mới
    @FXML
    private void createItem() {
        //Lấy kết quả
        Optional<Item> result = dialog.showAndWait();
        result.ifPresent(item -> {
            itemStore.getItems().add(item);
            itemStore.getVisibleItems().add(item);
        });
    }

    private void setupForm(){
        //Tạo và hiển thị form để nhập thông tin item mới
        BindingNewItem newItemModel = new BindingNewItem();
        Form newItemForm  = Form.of(
                Group.of(
                        Field.ofStringType(newItemModel.getBarcode())
                                .label("Barcode"),

                        Field.ofStringType(newItemModel.getName())
                                .label("Name")
                                .required("Name is required"),

                        Field.ofStringType(newItemModel.getCategory())
                                .label("Category")
                                .required("Category is required"),

                        Field.ofStringType(newItemModel.getSupplier())
                                .label("Supplier")
                                .tooltip("Supplier"),

                        Field.ofSingleSelectionType( newItemModel.getStockTypes(), newItemModel.getSelectedStockType())
                                .label("Stock Item")
                                .render(new SimpleRadioButtonControl<>()),

                        Field.ofSingleSelectionType( newItemModel.getItemTypes(), newItemModel.getSelectedItemType())
                                .label("Item type")
                                .render(new SimpleRadioButtonControl<>()),

                        Field.ofDoubleType(newItemModel.getWholesalePrice())
                                .label("Wholesale Price")
                                .required("Wholesale Price is required")
                                .render(new CurrencyInput(1,"$")),

                        Field.ofDoubleType(newItemModel.getRetailPrice())
                                .label("Retail Price")
                                .required("Retail Price is required")
                                .render(new CurrencyInput(1,"$")),

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
                                .render(new CurrencyInput(2,"%")),

                        Field.ofIntegerType(newItemModel.getReceivingQuantity())
                                .label("Receiving Quantity")
                                .required("Receiving Quantity is required")
                                .validate(CustomValidator.forPredicate(
                                        value -> String.valueOf(value).matches("") && (value > -5),
                                        "Receiving Quantity must be greater than 0 ")),

                        Field.ofIntegerType(newItemModel.getReorderLevel())
                                .label("Reorder Level")
                                .required("Reorder Level is required"),

                        Field.ofStringType(newItemModel.getDescription())
                                .label("Description"),

                        // Trường avatar cần tùy chỉnh để cho phép chọn và hiển thị hình ảnh
                        Field.ofStringType(newItemModel.getAvatar())
                                .label("Avatar")
                                .render(new ImageUpload()),

                        Field.ofBooleanType(newItemModel.getAllowAlternateDescription())
                                .label("Allow Alternate Description"),

                        Field.ofBooleanType(newItemModel.getHasSerialNumber())
                                .label("Has SerialNumber"),

                        Field.ofBooleanType(newItemModel.getDeleted())
                                .label("Deleted")

                )
        );

        itemStore.getLocationNames().forEach((location) -> {
            IntegerProperty quantity = new SimpleIntegerProperty(0);
            newItemModel.getQuantitiesPerLocation().put(location,quantity);
            newItemForm.getGroups().get(0).getElements().add(Field.ofIntegerType(quantity).label(location));
        });

        // Set size và tạo scroll pane để làm container cho form (giữ cho form có kích thước phù hợp)
        FormRenderer formRenderer = new FormRenderer(newItemForm);
        formRenderer.setPrefSize(600, 600);
        try {
            formRenderer.getStylesheets().add(Objects.requireNonNull(getClass().getClassLoader().getResource("css/styles.css")).toExternalForm());
        } catch (Exception e) {
            e.printStackTrace();
        }
        ScrollPane scrollPane = new ScrollPane(formRenderer);

        // Tạo Dialog
        dialog = new Dialog<>();
        dialog.setTitle("Create new item");
        dialog.setWidth(650);
        dialog.setHeight(600);

        // Thêm form vào Dialog
        dialog.getDialogPane().setContent(scrollPane);

        // Thêm các button vào Dialog
        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);
        Button okButton = (Button) dialog.getDialogPane().lookupButton(okButtonType);
        okButton.addEventFilter(ActionEvent.ACTION, event -> {
            if (!newItemForm.isValid()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid data");
                alert.setContentText("Please check your input data");
                alert.showAndWait();
                event.consume(); // Ngăn không cho đóng Dialog khi dữ liệu không hợp lệ
            }
        });

        // Xử lý kết quả khi nhấn OK
        dialog.setResultConverter(button -> {
            // Kiểm tra dữ liệu nhập vào form nếu hợp lệ thì lưu vào model
            if (button == okButtonType && newItemForm.isValid()) {
                newItemForm.persist(); // Lưu dữ liệu từ form vào model
                return newItemModel.mapToItem();  // Chuyển dữ liệu từ model sang Item
            }
            return null;
        });
    }


    // Xử lý khi người dùng chọn xóa item
    @FXML
    private void deleteItem() {
        itemsTable.getSelectionModel().clearSelection(); // Bỏ chọn các dòng
    }



    // Khởi tạo các checkbox để người dùng cột hiển thị
    private void setupColVisible() {
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



    private void addColumn(){
        
    }



    // Khởi tạo phân trang
    private void setupItemsPagination(){
        itemsPagination.setPageCount(10); // Số trang
    }
}
 