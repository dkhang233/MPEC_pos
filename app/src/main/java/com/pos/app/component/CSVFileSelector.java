package com.pos.app.component;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

public class CSVFileSelector {
    public CSVFileSelector() {
        throw new UnsupportedOperationException("Utility class");
    }
    /**
     * Hiển thị hộp thoại chọn file CSV.
     *
     * @param stage Cửa sổ chủ của ứng dụng.
     * @return File được chọn nếu người dùng chọn file, ngược lại trả về null.
     */
    public static File chooseCSVFile(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Chọn file CSV");

        // Thiết lập bộ lọc để chỉ hiển thị các file có đuôi .csv
        FileChooser.ExtensionFilter csvFilter =
                new FileChooser.ExtensionFilter("CSV Files", "*.csv");
        fileChooser.getExtensionFilters().add(csvFilter);

        // Hiển thị hộp thoại chọn file
        File selectedFile = fileChooser.showOpenDialog(stage);

        // Nếu có file được chọn, in ra đường dẫn file
        if (selectedFile != null) {
            System.out.println("Đã chọn file: " + selectedFile.getAbsolutePath());
        } else {
            System.out.println("Không có file nào được chọn.");
        }
        return selectedFile;
    }
}
