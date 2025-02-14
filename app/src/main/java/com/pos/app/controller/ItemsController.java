package com.pos.app.controller;

import com.dlsc.formsfx.model.structure.*;
import com.dlsc.formsfx.view.controls.SimpleRadioButtonControl;
import com.dlsc.formsfx.view.renderer.FormRenderer;
import com.pos.app.component.CurrencyInput;
import com.pos.app.component.ImageUpload;
import com.pos.app.model.Item;
import com.pos.app.model.BindingNewItem;
import com.pos.app.util.FormatHelper;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.*;

// Controller dung cho items view
public class ItemsController {
    // Danh sách các item
    private final List<Item> items = new ArrayList<>();

    // Danh sách các item hiển thị trên bảng
    private final ObservableList<Item> visibleItems = FXCollections.observableArrayList(items);

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
    private TableColumn<Item, String> nameCol;

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

    // Hàm khởi tạo, chạy khi view được load
    @FXML
    public void initialize() {
        columnsVisible.setSpacing(10);
        setupItemsTable(); // Khởi tạo bảng items
        setupItemsPagination(); // Khởi tạo phân trang
        this.deleteItemBtn.disableProperty().bind(this.itemsTable.getSelectionModel().selectedItemProperty().isNull()); // Disable button xóa khi không có dòng nào được chọn

//        this.itemsTable.getColumns().
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

        // Thêm dữ liệu vào bảng
        items.addAll(Arrays.asList(
                // Mẫu 1: Điện thoại thông minh
                Item.builder()
                        .id(2)
                        .name("Smartphone ABC")
                        .barcode("9876543210987")
                        .category("Electronics")
                        .supplier("XYZ Supplier")
                        .wholesalePrice(300.00)
                        .retailPrice(450.00)
                        .tax1Name("VAT")
                        .tax1(8.0)
                        .tax2Name("Luxury Tax")
                        .tax2(2.0)
                        .hsnCode("HSN5678")
                        .stockQuantity(100)
                        .receivingQuantity(20)
                        .reorderLevel(10)
                        .description("Latest model smartphone with advanced features")
                        .avatar("smartphone_abc.png")
                        .allowAlternateDescription(false)
                        .hasSerialNumber(true)
                        .deleted(false)
                        .build(),

// Mẫu 2: Máy giặt
        Item.builder()
                .id(3)
                .name("Washing Machine 123")
                .barcode("1928374650912")
                .category("Home Appliances")
                .supplier("HomeTech Supplier")
                .wholesalePrice(200.00)
                .retailPrice(350.00)
                .tax1Name("VAT")
                .tax1(12.0)
                .tax2Name("Eco Tax")
                .tax2(3.0)
                .hsnCode("HSN9101")
                .stockQuantity(30)
                .receivingQuantity(5)
                .reorderLevel(3)
                .description("Energy-efficient washing machine with multiple modes")
                .avatar("washing_machine_123.png")
                .allowAlternateDescription(true)
                .hasSerialNumber(true)
                .deleted(false)
                .build(),

// Mẫu 3: Sách
        Item.builder()
                .id(4)
                .name("Programming in Java")
                .barcode("5647382910123")
                .category("Books")
                .supplier("BookWorld")
                .wholesalePrice(20.00)
                .retailPrice(30.00)
                .tax1Name("GST")
                .tax1(5.0)
                .tax2Name("Education Cess")
                .tax2(1.0)
                .hsnCode("HSN1122")
                .stockQuantity(200)
                .receivingQuantity(50)
                .reorderLevel(20)
                .description("Comprehensive guide to Java programming")
                .avatar("programming_in_java.png")
                .allowAlternateDescription(false)
                .hasSerialNumber(false)
                .deleted(false)
                .build(),

// Mẫu 4: Bàn làm việc
         Item.builder()
                .id(5)
                .name("Ergonomic Office Desk")
                .barcode("3216549870123")
                .category("Furniture")
                .supplier("FurniCo")
                .wholesalePrice(150.00)
                .retailPrice(250.00)
                .tax1Name("VAT")
                .tax1(10.0)
                .tax2Name("Luxury Tax")
                .tax2(2.0)
                .hsnCode("HSN3344")
                .stockQuantity(20)
                .receivingQuantity(5)
                .reorderLevel(2)
                .description("Height-adjustable ergonomic office desk")
                .avatar("ergonomic_office_desk.png")
                .allowAlternateDescription(true)
                .hasSerialNumber(false)
                .deleted(false)
                .build(),

// Mẫu 5: Tai nghe
        Item.builder()
                .id(6)
                .name("Wireless Headphones")
                .barcode("4567891234567")
                .category("Accessories")
                .supplier("SoundTech")
                .wholesalePrice(50.00)
                .retailPrice(80.00)
                .tax1Name("VAT")
                .tax1(8.0)
                .tax2Name("Import Duty")
                .tax2(5.0)
                .hsnCode("HSN7788")
                .stockQuantity(150)
                .receivingQuantity(30)
                .reorderLevel(15)
                .description("Noise-cancelling wireless headphones with long battery life")
                .avatar("wireless_headphones.png")
                .allowAlternateDescription(false)
                .hasSerialNumber(true)
                .deleted(false)
                .build()
        ));
        visibleItems.addAll(items);

        setupColVisible(); // Khởi tạo các checkbox để chọn cột hiển thị
        itemsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); // Cho phép chọn nhiều dòng
        int cols = itemsTable.getColumns().size(); // Số cột của bảng
        itemsTable.getColumns().forEach(col -> col.prefWidthProperty().bind(itemsTable.widthProperty().divide(cols).subtract(0.65))); // Tự động thay đổi kích thước cột khi thay đổi kích thước bảng
        itemsTable.setItems(visibleItems);  // Thêm dữ liệu vào bảng
    }

    // Xử lý khi người dùng chọn tạo item mới
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
                                .render(new CurrencyInput(2)),

                        Field.ofDoubleType(newItemModel.getRetailPrice())
                                .label("Retail Price")
                                .required("Retail Price is required")
                                .render(new CurrencyInput(2)),

                        Field.ofStringType(newItemModel.getTax1Name())
                                .label("Tax 1 name"),

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

                        // Trường avatar cần tùy chỉnh để cho phép chọn và hiển thị hình ảnh
                        Field.ofStringType(newItemModel.getAvatar())
                                .label("Avatar")
                                .render(new ImageUpload()),

                        Field.ofBooleanType(newItemModel.getDeleted())
                                .label("Deleted")

                )
        );

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
        result.ifPresent(item -> {
            items.add(item);
            visibleItems.add(item);
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
 