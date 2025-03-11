package com.pos.app.util;

import com.pos.app.model.BindingNewItem;
import com.pos.app.model.Item;
import com.pos.app.store.ItemStore;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ImportExportFile {
    public static final int COLUMN_INDEX_ID = 0;
    public static final int COLUMN_INDEX_BARCODE = 1;
    public static final int COLUMN_INDEX_ITEM_NAME = 2;
    public static final int COLUMN_INDEX_CATEGORY = 3;
    public static final int COLUMN_INDEX_SUPPLIER_ID = 4;
    public static final int COLUMN_INDEX_STOCK_TYPE = 5;
    public static final int COLUMN_INDEX_ITEM_TYPE = 6;
    public static final int COLUMN_INDEX_TAX1_NAME = 7;
    public static final int COLUMN_INDEX_TAX1_VALUE = 8;
    public static final int COLUMN_INDEX_TAX2_NAME = 9;
    public static final int COLUMN_INDEX_TAX2_VALUE = 10;
    public static final int COLUMN_INDEX_DESCRIPTION = 11;

    private static CellStyle cellStyleFormatNumber = null;
    final List<Item> items = ImportExportFile.getItems();

    // Xử lý sự kiện khi nhấn nút import file trong Dialog.
    public static void handleImportFile(Dialog<Void> dialog) {
        // Lấy cửa sổ chủ từ Dialog hiện tại
        Stage owner = (Stage) dialog.getDialogPane().getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select File to Import");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv")
        );
        File selectedFile = fileChooser.showOpenDialog(owner);
        if (selectedFile != null) {
            String filePath = selectedFile.getAbsolutePath();
            if (filePath.endsWith(".csv")) {
                // Xử lý file được chọn
                importCSV(filePath);
            } else if (filePath.endsWith(".xls") || filePath.endsWith(".xlsx")) {
                importExcel(filePath);
            }
        }
        owner.close();
    }

    // Xử lý khi chọn file CSV
    public static void importCSV(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Nếu file CSV có header, bỏ qua dòng đầu tiên:
            br.readLine();
            while ((line = br.readLine()) != null) {
                // Giả sử các cột được phân cách bằng dấu phẩy
                String[] tokens = line.split(",");
                if (tokens.length >= 3) {
                    String barcode = tokens[1].trim();
                    String name = tokens[2].trim();

                    ItemStore.visibleItems.add(new Item(barcode, name));
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Xử lý khi chọn file Excel
    private static void importExcel(String filePath) {
        try (FileInputStream fis = new FileInputStream(new File(filePath))) {
            // Tự động nhận diện file .xls hoặc .xlsx
            Workbook workbook = WorkbookFactory.create(fis);
            // Lấy sheet đầu tiên (có thể thay đổi nếu cần)
            Sheet sheet = workbook.getSheetAt(0);

            Iterator<Row> iterator = sheet.iterator();
            DataFormatter formatter = new DataFormatter();

            if (iterator.hasNext()) iterator.next(); // Bỏ qua dòng tiêu đề

            while (iterator.hasNext()) {
                Row row = iterator.next();
                Cell cell0 = row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                Cell cell1 = row.getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                Cell cell2 = row.getCell(2, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

                String id = formatter.formatCellValue(cell0);
                String name = formatter.formatCellValue(cell1);
                String email = formatter.formatCellValue(cell2);


                }
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        ImportExportFile exportFile = new ImportExportFile();
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
                            exportFile.exportJSON(items, fileJSON.getAbsolutePath());
                            System.out.println("da chon json");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        break;

                    case "MS-Excel":
                        try {
                            File fileExcel = fileFormat("items_" + ".xlsx");
                            exportFile.exportExcel(items, fileExcel.getAbsolutePath());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        break;

                    case "CSV":
                        try {
                            File fileCsv = fileFormat("items.csv");
                            exportFile.exportCSV(items, fileCsv.getAbsolutePath());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        break;

                    case "PDF":
                        try {
                            File filePDF = fileFormat("items.pdf");
                            exportFile.exportPDF(items, filePDF.getAbsolutePath());
                            System.out.println("Da chon pdf");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        break;

                    case "XML":
                        try {
                            File fileXML = fileFormat("items.xml");
                            exportFile.exportXML(items, fileXML.getAbsolutePath());
                        } catch (IOException | PropertyException e) {
                            throw new RuntimeException(e);
                        } catch (JAXBException e) {
                            throw new RuntimeException(e);
                        }
                        break;

                    case "TXT":
                        try {
                            File fileTXT = fileFormat("items.txt");
                            exportFile.exportTxt(items, fileTXT.getAbsolutePath());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        break;

                    case "SQL":
                        try {
                            File fileSQL = fileFormat("items.sql");
                            exportFile.exportSQL(items, fileSQL.getAbsolutePath());
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

    private void exportJSON(List<Item> items, String jsonFilePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        // Ghi dữ liệu dưới dạng JSON với định dạng đẹp (pretty print)
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(jsonFilePath), items);
    }

    private void exportExcel(List<Item> items, String excelFilePath) throws IOException {
        // Tạo Workbook
        Workbook workbook = getWorkbook(excelFilePath);

        // Tạo sheet
        Sheet sheet = workbook.createSheet("Items"); // Tạo sheet với tên sheet

        int rowIndex = 0;

        // Viết header
        writeHeader(sheet, rowIndex);

        // Thêm data
        rowIndex++;
        for (Item item : items) {
            // Tạo row
            Row row = sheet.createRow(rowIndex);
            // Thêm data vào row
            writeBook(item, row);
            rowIndex++;
        }

        // Tạo footer
        writeFooter(sheet, rowIndex);

        // Tự động thay đổi kích thước cột
        int numberOfColumn = sheet.getRow(0).getPhysicalNumberOfCells();
        autosizeColumn(sheet, numberOfColumn);

        // Tạo file excel
        createOutputFile(workbook, excelFilePath);
    }

    private void exportCSV(List<Item> items, String csvFilePath) throws IOException {
        FileWriter writer = new FileWriter(csvFilePath);
            // Ghi header
            writer.append("Id,Barcode,Item Name,Stock Type,Item Type,Category,Supplier ID,Tax 1:,%,Tax 2:,%\n");

            // Ghi dữ liệu
            for (Item item : items) {
                writer.append(String.valueOf(item.getId())).append(",");
                writer.append(String.valueOf(item.getBarcode())).append(",");
                writer.append(String.valueOf(item.getItemName())).append(",");
                writer.append(String.valueOf(item.getStockType())).append(",");
                writer.append(String.valueOf(item.getItemType())).append(",");
                writer.append(String.valueOf(item.getCategory())).append(",");
                writer.append(String.valueOf(item.getSupplier())).append(",");
                writer.append(String.valueOf(item.getTax1Name())).append(",");
                writer.append(String.valueOf(item.getTax1())).append(",");
                writer.append(String.valueOf(item.getTax2Name())).append(",");
                writer.append(String.valueOf(item.getTax2())).append("\n");
            }
        }

    private void exportTxt(List<Item> items, String txtFilePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(txtFilePath))) {
            for (Item item : items) {
                writer.write(item.toString());
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void exportXML(List<Item> items, String xmlFilePath) throws IOException, JAXBException {
        ItemsWrapper wrapper = new ItemsWrapper();
        wrapper.setItems(items);
        JAXBContext context = JAXBContext.newInstance(ItemsWrapper.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(wrapper, new File(xmlFilePath));
    }

    private void exportPDF(List<Item> items, String pdfFilePath) throws IOException {
        // Tạo tài liệu PDF
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        // Khởi tạo PDPageContentStream để ghi nội dung
        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        // Cài đặt margin và các thông số cho bảng
        float margin = 50;
        float yStart = page.getMediaBox().getHeight() - margin; // điểm bắt đầu từ phía trên trang
        float rowHeight = 100; // chiều cao mỗi hàng
        float cellMargin = 5;
        float imageWidth = 80;  // kích thước hiển thị của hình ảnh
        float imageHeight = 80;
        float textStartX = margin + imageWidth + cellMargin * 2; // vị trí bắt đầu của văn bản, bên phải hình ảnh

        // Lặp qua danh sách Item để vẽ từng hàng
        for (int i = 0; i < items.size(); i++) {
            float currentY = yStart - (i * rowHeight);
            Item item = items.get(i);

            // Nếu item có hình ảnh (imagePath khác null hoặc rỗng)
            if (item.getAvatar() != null && !item.getAvatar().getValue().isEmpty()) {
                try {
                    PDImageXObject pdImage = PDImageXObject.createFromFile(item.getAvatar().getValue(), document);
                    // Vẽ hình ảnh tại vị trí (margin + cellMargin, currentY - imageHeight)
                    contentStream.drawImage(pdImage, margin + cellMargin, currentY - imageHeight, imageWidth, imageHeight);
                } catch (IOException e) {
                    // Nếu không tải được hình ảnh, bạn có thể ghi log hoặc bỏ qua
                    System.err.println("Không thể tải hình ảnh cho item " + item.getId() + ": " + e.getMessage());
                }
            }

            // Vẽ văn bản liên quan (ví dụ: id và tên) bên cạnh hình ảnh
            contentStream.beginText();
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
            // Vị trí văn bản: bắt đầu ở textStartX, tính từ currentY - 15 (để căn chỉnh theo hàng)
            contentStream.newLineAtOffset(textStartX, currentY - 15);
            String text = "ID: " + item.getId().getValue() + " - Name: " + item.getItemName().getValue();
            contentStream.showText(text);
            contentStream.endText();
        }

        // Đóng luồng nội dung và lưu tài liệu PDF
        contentStream.close();
        document.save(pdfFilePath);
        document.close();
    }

    private void exportSQL(List<Item> items, String sqlFilePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(sqlFilePath))) {
            writer.write("-- SQL Insert statements generated by exporter");
            writer.newLine();
            for (Item item : items) {
                // Lưu ý: Chỉ dùng cho mục đích demo. Hãy xử lý escape ký tự nếu cần.
                String sql = String.format("INSERT INTO items (id, name) VALUES (%d, '%s');",
                        item.getId().getValue(), item.getItemName().getValue());
                writer.write(sql);
                writer.newLine();
            }
        }
    }

    // Lấy dữ liệu hiện có trong bảng
    private static List<Item> getItems() {
        ItemStore.itemsPerLocation.computeIfAbsent(ItemStore.currentLocation.getName().getValue(), k -> new ArrayList<>());
        return ItemStore.visibleItems.get().stream().toList();
    }

    // Tạo workbook
    private static Workbook getWorkbook(String excelFilePath) throws IOException {
        Workbook workbook = null;

        if (excelFilePath.endsWith("xlsx")) {
            workbook = new XSSFWorkbook();
        } else if (excelFilePath.endsWith("xls")) {
            workbook = new HSSFWorkbook();
        } else {
            throw new IllegalArgumentException("The specified file is not Excel file");
        }

        return workbook;
    }

    // Write header with format
    private static void writeHeader(Sheet sheet, int rowIndex) {
        // create CellStyle
        CellStyle cellStyle = createStyleForHeader(sheet);

        // Create row
        Row row = sheet.createRow(rowIndex);

        // Create cells
        Cell cell = row.createCell(COLUMN_INDEX_ID);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Id");

        cell = row.createCell(COLUMN_INDEX_BARCODE);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Barcode");

        cell = row.createCell(COLUMN_INDEX_ITEM_NAME);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Item Name");

        cell = row.createCell(COLUMN_INDEX_CATEGORY);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Category");

        cell = row.createCell(COLUMN_INDEX_SUPPLIER_ID);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Supplier ID");

        cell = row.createCell(COLUMN_INDEX_STOCK_TYPE);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Stock Type");

        cell = row.createCell(COLUMN_INDEX_ITEM_TYPE);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Item Type");

        cell = row.createCell(COLUMN_INDEX_TAX1_NAME);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Tax 1");

        cell = row.createCell(COLUMN_INDEX_TAX1_VALUE);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Value(%)");

        cell = row.createCell(COLUMN_INDEX_TAX2_NAME);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Tax 2");

        cell = row.createCell(COLUMN_INDEX_TAX2_VALUE);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Value(%)");

        cell = row.createCell(COLUMN_INDEX_DESCRIPTION);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Description");


    }

    // Write data
    private static void writeBook(Item item, Row row) {
        if (cellStyleFormatNumber == null) {
            // Format number
            short format = (short) BuiltinFormats.getBuiltinFormat("#,##0");
            // DataFormat df = workbook.createDataFormat();
            // short format = df.getFormat("#,##0");

            //Create CellStyle
            Workbook workbook = row.getSheet().getWorkbook();
            cellStyleFormatNumber = workbook.createCellStyle();
            cellStyleFormatNumber.setDataFormat(format);
        }

        Cell cell = row.createCell(COLUMN_INDEX_ID);
        cell.setCellValue(item.getId().getValue());

        cell = row.createCell(COLUMN_INDEX_BARCODE);
        cell.setCellValue(item.getBarcode().getValue());

        cell = row.createCell(COLUMN_INDEX_ITEM_NAME);
        cell.setCellValue(item.getItemName().getValue());
        cell.setCellStyle(cellStyleFormatNumber);

        cell = row.createCell(COLUMN_INDEX_CATEGORY);
        cell.setCellValue(item.getCategory().getValue());

        cell = row.createCell(COLUMN_INDEX_SUPPLIER_ID);
        cell.setCellValue(item.getSupplier().getValue());

        cell = row.createCell(COLUMN_INDEX_STOCK_TYPE);
        cell.setCellValue(item.getStockType().getValue());

        cell = row.createCell(COLUMN_INDEX_ITEM_TYPE);
        cell.setCellValue(item.getItemType().getValue());

        cell = row.createCell(COLUMN_INDEX_TAX1_NAME);
        cell.setCellValue(item.getTax1Name().getValue());

        cell = row.createCell(COLUMN_INDEX_TAX1_VALUE);
        cell.setCellValue(item.getTax1().getValue());

        cell = row.createCell(COLUMN_INDEX_TAX2_NAME);
        cell.setCellValue(item.getTax2Name().getValue());

        cell = row.createCell(COLUMN_INDEX_TAX2_VALUE);
        cell.setCellValue(item.getTax2().getValue());

        cell = row.createCell(COLUMN_INDEX_DESCRIPTION);
        cell.setCellValue(item.getDescription().getValue());
    }

    // Create CellStyle for header
    private static CellStyle createStyleForHeader(Sheet sheet) {
        // Create font
        Font font = sheet.getWorkbook().createFont();
        font.setFontName("Times New Roman");
        font.setBold(true);
        font.setFontHeightInPoints((short) 14); // font size
        font.setColor(IndexedColors.WHITE.getIndex()); // text color

        // Create CellStyle
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        return cellStyle;
    }

    // Write footer
    private static void writeFooter(Sheet sheet, int rowIndex) {
        // Create row
        Row row = sheet.createRow(rowIndex);
        Cell cell = row.createCell(COLUMN_INDEX_SUPPLIER_ID, CellType.FORMULA);
//        cell.setCellFormula("SUM(E2:E6)");
    }

    // Tự động điều chỉnh kích thước cột
    private static void autosizeColumn(Sheet sheet, int lastColumn) {
        for (int columnIndex = 0; columnIndex < lastColumn; columnIndex++) {
            sheet.autoSizeColumn(columnIndex);
        }
    }

    // Create output file
    private static void createOutputFile(Workbook workbook, String excelFilePath) throws IOException {
        try (OutputStream os = new FileOutputStream(excelFilePath)) {
            workbook.write(os);
        }
    } private File fileFormat(String format) {
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
