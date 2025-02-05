package com.pos.app.controller;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

// Controller dung cho main view
public class MainController {
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
        itemsView.setVisible(true);
        itemsKitsView.setVisible(false);
        customersView.setVisible(false);
    }

    // Xu li khi nguoi dung chon items kits
    @FXML
    public void onItemsKitsClicked(){
        itemsView.setVisible(false);
        customersView.setVisible(false);
        itemsKitsView.setVisible(true);
    }

    //Xu li khi nguoi dung chon Customer
    @FXML
    public void onCustomerClicked(){
        itemsView.setVisible(false);
        itemsKitsView.setVisible(false);
        customersView.setVisible(true);
    }

    @FXML
    public void onReceivingClicked() {
    }

    @FXML
    public void onExpenseClicked() {
    }

    @FXML
    public void onCashupClicked() {
    }

    @FXML
    public void onOfficeClicked() {
    }

    @FXML
    public void onSupplierClicked() {
    }
}
