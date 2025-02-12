package com.pos.app.component;

import com.dlsc.formsfx.model.structure.StringField;
import com.dlsc.formsfx.view.controls.SimpleControl;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;


// Custom component dùng để upload hình ảnh với thư viện FormsFX
public class ImageUpload extends SimpleControl<StringField> {

    // Nhãn của trường
    private Label fieldLabel;

    // Nút chọn hình ảnh
    private Button selectImageBtn;

    // Nút xóa hình ảnh
    private Button removeImageBtn;

    // Hình ảnh
    private ImageView image;

    // Hỗ trợ chọn hình ảnh
    private FileChooser fileChooser;

    // Khởi tạo các thành phần
    @Override
    public void initializeParts() {
        super.initializeParts();
        setVgap(6);

        this.fieldLabel = new Label(this.field.getLabel());

        this.selectImageBtn = new Button("Select");
        this.selectImageBtn.getStyleClass().addAll("upload-btn");
        this.selectImageBtn.setOnAction(event -> {
            File file = this.fileChooser.showOpenDialog(getScene().getWindow());
            if (file != null) {
                this.field.valueProperty().set(file.toURI().toString());
                this.image.setImage(new Image(file.toURI().toString()));
                getChildren().add(this.image);
                setRowIndex(this.selectImageBtn, 5);
                getChildren().add(this.removeImageBtn);
            }
        });

        this.removeImageBtn = new Button("Remove");
        this.removeImageBtn.getStyleClass().addAll("remove-btn");
        this.removeImageBtn.setOnAction(event -> {
            this.field.valueProperty().set("");
            getChildren().remove(this.image);
            getChildren().remove(this.removeImageBtn);
            setRowIndex(this.selectImageBtn, 0);
        });

        this.image = new ImageView();
        this.image.setFitWidth(100);
        this.image.setFitHeight(100);

        this.fileChooser = new FileChooser();
        this.fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
    }

    // Tùy chỉnh layout
    @Override
    public void layoutParts() {
        super.layoutParts();
        setColumnIndex(this.fieldLabel, 0);
        setColumnSpan(this.fieldLabel, 2);
        
        setColumnIndex(this.selectImageBtn, 2);
        setColumnSpan(this.selectImageBtn, 2);

        setColumnIndex(this.removeImageBtn, 4);
        setColumnSpan(this.removeImageBtn, 2);
        setRowIndex(this.removeImageBtn, 5);

        setColumnIndex(this.image, 2);
        setColumnSpan(this.image, 4);
        setRowSpan(this.image, 4);
        getChildren().addAll(this.fieldLabel,this.selectImageBtn);
    }
}
