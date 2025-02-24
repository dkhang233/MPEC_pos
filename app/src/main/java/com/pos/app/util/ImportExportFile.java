package com.pos.app.util;

import com.pos.app.model.Item;
import com.pos.app.store.ItemStore;
import javafx.scene.control.Dialog;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ImportExportFile {
    public static final int COLUMN_INDEX_ID = 0;
    public static final int COLUMN_INDEX_TITLE = 1;
    public static final int COLUMN_INDEX_PRICE = 2;
    public static final int COLUMN_INDEX_QUANTITY = 3;
    public static final int COLUMN_INDEX_TOTAL = 4;
    private static CellStyle cellStyleFormatNumber = null;
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

//                    ItemStore.visibleItems.add(new Item(barcode, name));
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
            DataFormatter dataFormatter = new DataFormatter();

            boolean isFirstRow = true;
            for (Row row : sheet) {
                // Nếu file Excel có header, bỏ qua dòng đầu tiên
                if (isFirstRow) {
                    isFirstRow = false;
                    continue;
                }
                // Lấy dữ liệu từ các cột (giả sử cột 0: name, cột 1: value)
                Cell cell0 = (Cell) row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                Cell cell1 = (Cell) row.getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

                String name = dataFormatter.formatCellValue((Cell) cell0);
                String value = dataFormatter.formatCellValue((Cell) cell1);

//                ItemStore.visibleItems.add(new Item(name, value));
            }
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void handleJsonOption(List<Item> items, String jsonFilePath) throws IOException {
    }

    static void handleExcelOption(List<Item> items, String excelFilePath) throws IOException {
        // Tạo Workbook
        Workbook workbook = getWorkbook(excelFilePath);

        // Tạo sheet
        Sheet sheet = workbook.createSheet("Items"); // Create sheet with sheet name

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

        // Auto resize column witdth
        int numberOfColumn = sheet.getRow(0).getPhysicalNumberOfCells();
        autosizeColumn(sheet, numberOfColumn);

        // Tạo file excel
        createOutputFile(workbook, excelFilePath);
    }

    // Lấy dữ liệu hiện có trong bảng
    static List<Item> getItems() {
        ItemStore.itemsPerLocation.computeIfAbsent(ItemStore.currentLocation.getName().getValue(), k -> new ArrayList<>());
        return ItemStore.itemsPerLocation.get(ItemStore.currentLocation.getName().getValue());
    }

    // Create workbook
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

        cell = row.createCell(COLUMN_INDEX_TITLE);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Barcode");

        cell = row.createCell(COLUMN_INDEX_PRICE);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Item Name");

        cell = row.createCell(COLUMN_INDEX_QUANTITY);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Category");

        cell = row.createCell(COLUMN_INDEX_TOTAL);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Supplier ID");
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

        cell = row.createCell(COLUMN_INDEX_TITLE);
        cell.setCellValue(item.getBarcode().getValue());

        cell = row.createCell(COLUMN_INDEX_PRICE);
        cell.setCellValue(item.getItemName().getValue());
        cell.setCellStyle(cellStyleFormatNumber);

        cell = row.createCell(COLUMN_INDEX_QUANTITY);
        cell.setCellValue(item.getCategory().getValue());

        cell = row.createCell(COLUMN_INDEX_TOTAL);
        cell.setCellValue(item.getSupplier().getValue());

        int currentRow = row.getRowNum() + 1;
        String columnPrice = CellReference.convertNumToColString(COLUMN_INDEX_PRICE);
        String columnQuantity = CellReference.convertNumToColString(COLUMN_INDEX_QUANTITY);
        cell.setCellFormula(columnPrice + currentRow + "*" + columnQuantity + currentRow);
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
        Cell cell = row.createCell(COLUMN_INDEX_TOTAL, CellType.FORMULA);
        cell.setCellFormula("SUM(E2:E6)");
    }

    // Auto resize column width
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
    }


    static void handleCsvOption(List<Item> items, String csvFilePath) throws IOException {
    }
}
