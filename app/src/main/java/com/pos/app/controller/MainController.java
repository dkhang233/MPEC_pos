package com.pos.app.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.Objects;

// Controller dung cho main view
public class MainController {

    @FXML
    private Pane wrapView;

    @FXML
    public Pane supplierView;

    // @FXML
    // public Pane expenseView;

    @FXML
    public Pane cashupView;

    // @FXML
    // public Pane officeView;

    @FXML
    public Pane receivingView;

    @FXML
    private Pane itemsView;

    // @FXML
    // private Pane itemsKitsView;

    @FXML
    private Pane customersView;

    @FXML
    private Pane salesView;

    // Xu li khi nguoi dung chon sales
    @FXML
    public void onSalesClicked() {
        hideAllView();
        salesView.setVisible(true);
    }

    // Xu li khi nguoi dung chon items
    @FXML
    public void onItemsClicked() {
        hideAllView();
        itemsView.setVisible(true);
    }

    // Xu li khi nguoi dung chon Customer
    @FXML
    public void onCustomerClicked() {
        System.out.println("Customer clicked");
        hideAllView();
        customersView.setVisible(true);
    }

    @FXML
    public void onReceivingClicked() {
        hideAllView();
        receivingView.setVisible(true);
    }

    @FXML
    public void onCashupClicked() {
        hideAllView();
        cashupView.setVisible(true);
    }

    @FXML
    public void onSupplierClicked() {
        hideAllView();
        supplierView.setVisible(true);
    }

    private void hideAllView() {
        for (Node node : wrapView.getChildren()) {
            node.setVisible(false);
        }
    }

    @FXML
    public void initialize() {
        hideAllView();
        itemsView.setVisible(true);
    }
}
