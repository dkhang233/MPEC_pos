package com.pos.app.controller;

import com.dlsc.formsfx.model.structure.*;
import com.dlsc.formsfx.view.controls.SimpleControl;
import com.dlsc.formsfx.view.controls.SimpleRadioButtonControl;
import com.dlsc.formsfx.view.renderer.FormRenderer;
import com.pos.app.component.CurrencyInput;
import com.pos.app.model.Item;
import com.pos.app.model.BindingNewItem;
import com.pos.app.util.FormatHelper;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
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

    @FXML
    private TableColumn<Item, Double> wholeSalePriceCol;

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
                                .label("Supplier")
                                .tooltip("Supplier"),

                        Field.ofDoubleType(newItemModel.getWholesalePrice())
                                .label("Wholesale Price")
                                .required("Wholesale Price is required")
//                                .tooltip("Wholesale Price")
                                .render(new CurrencyInput(2)),

                        Field.ofDoubleType(newItemModel.getRetailPrice())
                                .label("Retail Price")
                                .required("Retail Price is required")
                                .render(new CurrencyInput(2)),

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

                        // Trường avatar cần tùy chỉnh để cho phép chọn và hiển thị hình ảnh
                        Field.ofStringType(newItemModel.getAvatar())
                                .label("Avatar")
                                .render(new SimpleControl<StringField>() {
                                    private Label label;

                                    private Button selectImageBtn;

                                    private Button removeImageBtn;
                                    
                                    private ImageView image;

                                    private FileChooser fileChooser;

                                    // Khởi tạo các thành phần
                                    @Override
                                    public void initializeParts() {
                                        super.initializeParts();
                                        setVgap(6);
                                        label = new Label("Avatar");
                                        selectImageBtn = new Button("Select");
                                        selectImageBtn.getStyleClass().addAll("upload-btn");
                                        selectImageBtn.setOnAction(event -> {
                                            File file = fileChooser.showOpenDialog(getScene().getWindow());
                                            if (file != null) {
                                                newItemModel.getAvatar().set(file.toURI().toString());
                                                image.setImage(new Image(file.toURI().toString()));
                                                getChildren().add(image);
                                                setRowIndex(selectImageBtn, 5);
                                                getChildren().add(removeImageBtn);
                                            }
                                        });

                                        removeImageBtn = new Button("Remove");
                                        removeImageBtn.getStyleClass().addAll("remove-btn");
                                        removeImageBtn.setOnAction(event -> {
                                            newItemModel.getAvatar().set("");
                                            getChildren().remove(image);
                                            getChildren().remove(removeImageBtn);
                                            setRowIndex(selectImageBtn, 0);
                                        });

                                        image = new ImageView();
                                        image.setFitWidth(100);
                                        image.setFitHeight(100);

                                        fileChooser = new FileChooser();
                                        fileChooser.getExtensionFilters().addAll(
                                                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
                                        );
                                    }

                                    // Tùy chỉnh layout
                                    @Override
                                    public void layoutParts() {
                                        super.layoutParts();
                                        setColumnIndex(label, 0);
                                        
                                        setColumnIndex(selectImageBtn, 2);
                                        setColumnSpan(selectImageBtn, 2);

                                        setColumnIndex(removeImageBtn, 4);
                                        setColumnSpan(removeImageBtn, 2);
                                        setRowIndex(removeImageBtn, 5);
                                        
                                        setColumnIndex(image, 2);
                                        setColumnSpan(image, 4);
                                        setRowSpan(image, 4);
                                        getChildren().addAll(label, selectImageBtn);
                                    }
                                }),

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

    // Hàm khởi tạo, chạy khi view được load
    @FXML
    public void initialize() {
        setupItemsTable(); // Khởi tạo bảng items
        setupItemsPagination(); // Khởi tạo phân trang
        deleteItemBtn.disableProperty().bind(itemsTable.getSelectionModel().selectedItemProperty().isNull()); // Disable button xóa khi không có dòng nào được chọn
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
                Item.builder()
                        .id(1)
                        .itemNumber("893257823")
                        .name("Coca Cola")
                        .category("Đồ uống có gas")
                        .supplier("Coca Cola")
                        .wholesalePrice(90000)
                        .retailPrice(10000)
                        .quantity(20)
                        .taxPercent(0)
                        .avatar("")
                        .build(),
                Item.builder()
                        .id(2)
                        .itemNumber("8982323213")
                        .name("Snack bí đỏ")
                        .category("Đồ ăn vặt")
                        .supplier("Oishi")
                        .wholesalePrice(5000.93243432)
                        .retailPrice(5000)
                        .quantity(10)
                        .taxPercent(0)
                        .avatar("")
                        .build(),
                Item.builder()
                        .id(3)
                        .itemNumber("8982323213")
                        .name("Bánh mì")
                        .category("Thực phẩm")
                        .supplier("Kinh Đô")
                        .wholesalePrice(5000)
                        .retailPrice(5000)
                        .quantity(5)
                        .taxPercent(0)
                        .avatar("")
                        .build()
        ));
        visibleItems.addAll(items);

        itemsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); // Cho phép chọn nhiều dòng
        int cols = itemsTable.getColumns().size(); // Số cột của bảng
        itemsTable.getColumns().forEach(col -> col.prefWidthProperty().bind(itemsTable.widthProperty().divide(cols).subtract(0.65))); // Tự động thay đổi kích thước cột khi thay đổi kích thước bảng
        itemsTable.setItems(visibleItems);  // Thêm dữ liệu vào bảng
    }

    // Khởi tạo phân trang
    private void setupItemsPagination(){
        itemsPagination.setPageCount(10); // Số trang
    }
}
 