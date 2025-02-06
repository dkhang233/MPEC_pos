package com.pos.app.controller;

import com.dlsc.formsfx.model.structure.Field;
import com.dlsc.formsfx.model.structure.Form;
import com.dlsc.formsfx.model.structure.Group;
import com.dlsc.formsfx.model.structure.StringField;
import com.dlsc.formsfx.model.util.BindingMode;
import com.dlsc.formsfx.view.controls.SimpleControl;
import com.dlsc.formsfx.view.controls.SimpleRadioButtonControl;
import com.dlsc.formsfx.view.renderer.FormRenderer;
import com.pos.app.model.Item;
import com.pos.app.model.BindingNewItem;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

// Controller dung cho items view
public class ItemsController {
    @FXML
    private TableView<Item> itemsTable;

    @FXML
    private TableColumn<Item, String> avatarCol;

    @FXML
    private TableColumn<Item, String> updateStockCol;

    @FXML
    private TableColumn<Item, String> stockHistoryCol;

    @FXML
    private TableColumn<Item, String> updateItemCol;

    private final String defaultAvatar = Objects
            
                                                                                                                  // 
                                                                                                                  // 
            .requireNonNull(getClass().getClassLoader().getResource("static/default-item.png")).toExternalForm(); // Avatar
                                                                                                                  // mặc
                                                                                                                  // định

    @FXML
    private Button deleteItemBtn;

    @FXML
    private Pagination itemsPagination;

    @FXML
    private void createItem() {
        ObjectProperty<String> selectedType = new SimpleObjectProperty<>();
        ListProperty<String> types = new SimpleListProperty<>(FXCollections.observableArrayList("Standard", "Kit"));
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

                        Field.ofSingleSelectionType( newItemModel.getStockTypes(), newItemModel.getSelectedStockType())
                                .label("Stock Item")
                                .render(new SimpleRadioButtonControl<>()),

                        Field.ofSingleSelectionType( newItemModel.getItemTypes(), newItemModel.getSelectedItemType())
                                .label("Item type")
                                .render(new SimpleRadioButtonControl<>()),

                        Field.ofStringType(newItemModel.getSupplier())
                                .label("Supplier"),

                        Field.ofDoubleType(newItemModel.getWholesalePrice())
                                .label("Wholesale Price")
                                .required("Wholesale Price is required"),

                        Field.ofDoubleType(newItemModel.getRetailPrice())
                                .label("Retail Price")
                                .required("Retail Price is required"),

                        Field.ofDoubleType(newItemModel.getTax())
                                .label("Tax"),

                        Field.ofIntegerType(newItemModel.getStockQuantity())
                                .label("Stock Quantity")
                                .required("Stock Quantity is required"),

                        Field.ofIntegerType(newItemModel.getReceivingQuantity())
                                .label("Receiving Quantity")
                                .required("Receiving Quantity is required"),

                        Field.ofIntegerType(newItemModel.getReorderLevel())
                                .label("Reorder Level")
                                .required("Reorder Level is required"),

                        Field.ofStringType(newItemModel.getDescription())
                                .label("Description"),

                        Field.ofStringType(newItemModel.getAvatar())
                                .label("Avatar")
                                .render(new SimpleControl<StringField>() {
                                    private Button imageView;

                                    @Override
                                    public void initializeParts() {
                                        super.initializeParts();
                                        imageView = new Button();
                                        imageView.setPrefWidth(50);
                                        imageView.setPrefHeight(50);
                                        imageView.getStyleClass().add("upload-image");
                                    }

                                    @Override
                                    public void layoutParts() {
                                        super.layoutParts();
//                                        if (!getField().getValue().isEmpty()) {
//                                            imageView.setImage(new Image(getField().getValue()));
//                                        }
                                        getChildren().addAll( imageView, new Label("ababbc"));
                                    }
                                }),

                        Field.ofBooleanType(newItemModel.getDeleted())
                                .label("Deleted")

                )
        );
        FormRenderer formRenderer = new FormRenderer(newItemForm);
        formRenderer.setPrefSize(600, 600);
        ScrollPane scrollPane = new ScrollPane(formRenderer);

        // Tạo Dialog
        Dialog<Item> dialog = new Dialog<>();
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

        //Lấy kết quả
        Optional<Item> result = dialog.showAndWait();
        result.ifPresent(item -> System.out.println("Tên đã nhập: " + item + "- Item type: " + selectedType.get()));
    }
 
    // Xử lý khi người dùng chọn xóa item
    @FXML
    private void deleteItem() {
        itemsTable.getSelectionModel().clearSelection(); // Bỏ chọn các dòng
    }

    // Hàm khởi tạo, chạy khi view được load
    @FXML
    public void initialize() {
        setupItemsTable(); // Khởi tạo bảng items
        setupItemsPagination(); // Khởi tạo phân trang
        deleteItemBtn.disableProperty().bind(itemsTable.getSelectionModel().selectedItemProperty().isNull()); // Disable button xóa khi không có dòng nào được chọn
    }

    // Khởi tạo bảng items
    private void setupItemsTable(){
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
        
        // Thêm dữ liệu vào bảng
        ObservableList<Item> items = FXCollections.observableArrayList(
                Item.builder()
                        .id(1)
                        .itemNumber("8982323213")
                        .name("bim bim")
                        .category("Thực phẩm")
                        .supplier("Coca Cola")
                        .wholesalePrice(5000)
                        .retailPrice(5000)
                        .quantity(5)
                        .taxPercent(0.1)
                        .avatar("a-woman-working-on-a-laptop-6uAssP0vuPs")
                        .build(),
                Item.builder()
                        .id(2)
                        .itemNumber("8982323213")
                        .name("bim bim")
                        .category("Thực phẩm siêu sạch đem từ Mỹ về, không chứa chất bảo quản, không chất tạo màu")
                        .supplier("Coca Cola")
                        .wholesalePrice(5000)
                        .retailPrice(5000)
                        .quantity(5)
                        .taxPercent(0.1)
                        .avatar("a-woman-working-on-a-laptop-6uAssP0vuPs")
                        .build() ,
                Item.builder()
                        .id(3)
                        .itemNumber("8982323213")
                        .name("bim bim")
                        .category("Thực phẩm siêu sạch đem từ Mỹ về, không chứa chất bảo quản, không chất tạo màu")
                        .supplier("Coca Cola")
                        .wholesalePrice(5000)
                        .retailPrice(5000)
                        .quantity(5)
                        .taxPercent(0.1)
                        .avatar("a-woman-working-on-a-laptop-6uAssP0vuPs")
                        .build()
        );

        itemsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); // Cho phép chọn nhiều dòng
        int cols = itemsTable.getColumns().size(); // Số cột của bảng
        itemsTable.getColumns().forEach(col -> col.prefWidthProperty().bind(itemsTable.widthProperty().divide(cols).subtract(0.65))); // Tự động thay đổi kích thước cột khi thay đổi kích thước bảng
        itemsTable.setItems(items);  // Thêm dữ liệu vào bảng
    }

    // Khởi tạo phân trang
    private void setupItemsPagination(){
        itemsPagination.setPageCount(10); // Số trang
    }
}
 