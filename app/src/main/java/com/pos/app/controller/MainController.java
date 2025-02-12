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

    @FXML
    public Pane expenseView;

    @FXML
    public Pane cashupView;

    @FXML
    public Pane officeView;

    @FXML
    public Pane receivingView;

    @FXML
    private Pane itemsView;

    @FXML
    private Pane itemsKitsView;

    @FXML
    private Pane customersView;

    // Xu li khi nguoi dung chon sales
    @FXML
    public void onSalesClicked(){
        System.out.println("Sales clicked");
    }

    // Xu li khi nguoi dung chon items
    @FXML
    public void onItemsClicked(){
        hideAllView();
        itemsView.setVisible(true);
    }

    // Xu li khi nguoi dung chon items kits
    @FXML
    public void onItemsKitsClicked(){
        hideAllView();
        itemsKitsView.setVisible(true);
    }

    //Xu li khi nguoi dung chon Customer
    @FXML
    public void onCustomerClicked(){
        hideAllView();
        customersView.setVisible(true);
    }

    @FXML
    public void onReceivingClicked() {
        hideAllView();
        receivingView.setVisible(true);
    }

    @FXML
    public void onExpenseClicked() {
        hideAllView();
        expenseView.setVisible(true);
    }

    @FXML
    public void onCashupClicked() {
        hideAllView();
        cashupView.setVisible(true);
    }

    @FXML
    public void onOfficeClicked() {
        hideAllView();
        officeView.setVisible(true);
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
}
