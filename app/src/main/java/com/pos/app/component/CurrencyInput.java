package com.pos.app.component;

import com.dlsc.formsfx.model.structure.DataField;
import com.dlsc.formsfx.view.controls.SimpleDoubleControl;
import com.pos.app.util.FormatHelper;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;

// Custom component dùng để nhập số tiền với thư viện FormsFX
public class CurrencyInput extends SimpleDoubleControl{
    private HBox container;

    private Label currencySymbol;
    private int labelCol;

    public CurrencyInput(int labelCol) {
        this.labelCol = labelCol;
    }
    @Override
    public void initializeParts() {
        super.initializeParts();

        currencySymbol = new Label("$");
        currencySymbol.getStyleClass().add("currency-symbol");
        
        this.editableSpinner.getEditor().textProperty().bindBidirectional(field.userInputProperty(), new StringConverter<String>() {
            @Override
            public String toString(String object) {
                if (object == null || object.isEmpty()) return "";
                try {
                    return FormatHelper.formatDecimalNumber(Double.parseDouble(object));
                } catch (Exception e) {
                    return "";
                }
            }

            @Override
            public String fromString(String string) {
                if (string == null || string.isEmpty()) return "";
                String result = string.replaceAll("[^\\d.]", "");
                try {
                    editableSpinner.getEditor().setText(FormatHelper.formatDecimalNumber(Double.parseDouble(result)));
                } catch (Exception e) {
                    editableSpinner.getEditor().setText("");
                }
                return result;
            }
        });

        container = new HBox();
    }

    @Override
    public void layoutParts() {
        super.layoutParts();
        this.stack.getChildren().remove(editableSpinner);
        this.getChildren().remove(this.stack);
        container.getChildren().addAll(currencySymbol, editableSpinner);
        int columns = ((DataField)this.field).getSpan();
        this.add(container,2,0, columns - 2 ,1);
//        GridPane.setConstraints(this.fieldLabel,0,0,2,1);
    }
}
