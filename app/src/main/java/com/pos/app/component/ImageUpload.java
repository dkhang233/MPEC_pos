package com.pos.app.component;

import com.dlsc.formsfx.model.structure.StringField;
import com.dlsc.formsfx.view.controls.SimpleControl;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;

public class ImageUpload extends SimpleControl<StringField> {

    // Thành phần giao diện
    private Label fieldLabel;
    private Button selectImageBtn;
    private Button removeImageBtn;
    private ImageView image;
    private FileChooser fileChooser;

    @Override
    public void initializeParts() {
        super.initializeParts();
        setVgap(6);

        // Khởi tạo nhãn của trường
        fieldLabel = new Label(field.getLabel());

        // Nút chọn hình ảnh
        selectImageBtn = new Button("Select Image");
        selectImageBtn.getStyleClass().addAll("upload-btn");
        selectImageBtn.setOnAction(event -> handleSelectImage());

        // Nút xóa hình ảnh
        removeImageBtn = new Button("Remove");
        removeImageBtn.getStyleClass().addAll("remove-btn");
        removeImageBtn.setOnAction(event -> handleRemoveImage());

        // Khởi tạo ImageView để hiển thị hình ảnh
        image = new ImageView();
        image.setFitWidth(100);
        image.setFitHeight(100);

        // Khởi tạo FileChooser và thiết lập bộ lọc file
        fileChooser = createFileChooser();
    }

    /**
     * Tạo đối tượng FileChooser với bộ lọc chỉ hiển thị các file hình ảnh.
     */
    private FileChooser createFileChooser() {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        return fc;
    }

    /**
     * Xử lý sự kiện khi nhấn nút chọn hình ảnh.
     */
    private void handleSelectImage() {
        File file = fileChooser.showOpenDialog(getScene().getWindow());
        if (file != null) {
            updateImage(file);
            updateUIAfterImageSelected();
        }
    }

    /**
     * Cập nhật dữ liệu và hiển thị hình ảnh từ file được chọn.
     *
     * @param file File hình ảnh được chọn.
     */
    private void updateImage(File file) {
        String fileUri = file.toURI().toString();
        field.valueProperty().set(fileUri);
        image.setImage(new Image(fileUri));
    }

    /**
     * Cập nhật giao diện sau khi hình ảnh được chọn.
     */
    private void updateUIAfterImageSelected() {
        if (!getChildren().contains(image)) {
            getChildren().add(image);
        }
        // Đưa nút chọn hình ảnh xuống hàng 5 khi có hình ảnh
        setRowIndex(selectImageBtn, 5);
        if (!getChildren().contains(removeImageBtn)) {
            getChildren().add(removeImageBtn);
        }
    }

    /**
     * Xử lý sự kiện khi nhấn nút xóa hình ảnh.
     */
    private void handleRemoveImage() {
        field.valueProperty().set("");
        getChildren().remove(image);
        getChildren().remove(removeImageBtn);
        // Đặt lại vị trí ban đầu cho nút chọn hình ảnh
        setRowIndex(selectImageBtn, 0);
    }

    @Override
    public void layoutParts() {
        super.layoutParts();
        setColumnIndex(fieldLabel, 0);
        setColumnSpan(fieldLabel, 2);

        setColumnIndex(selectImageBtn, 2);
        setColumnSpan(selectImageBtn, 2);

        setColumnIndex(removeImageBtn, 4);
        setColumnSpan(removeImageBtn, 2);
        setRowIndex(removeImageBtn, 5);

        setColumnIndex(image, 2);
        setColumnSpan(image, 4);
        setRowSpan(image, 4);

        getChildren().addAll(fieldLabel, selectImageBtn);
    }
}

