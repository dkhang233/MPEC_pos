package com.pos.app.controller;

import com.dlsc.formsfx.model.structure.*;
import com.dlsc.formsfx.model.validators.*;
import com.dlsc.formsfx.view.renderer.FormRenderer;

import com.pos.app.model.*;
import com.pos.app.store.ItemStore;
import com.pos.app.util.FormatHelper;
import com.pos.app.util.ItemManager;

import javafx.beans.property.SimpleStringProperty;
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





    // --------------------------------Phần liên quan đến bảng items--------------------------------//
    
    // Khởi tạo bảng items
    private void setupItemsTable(){
        // Khởi tạo ItemManager để xử lý các sự kiện liên quan đến item
        ItemManager itemManager = new ItemManager();

        // Xử lý sự kiện khi người dùng ấn nút "New Item"
        newItem.setOnAction(event -> itemManager.createItem());

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
                    itemManager.updateItemInfo(item);
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


    // Xử lý khi người dùng muốn cập nhật số lượng item
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
 