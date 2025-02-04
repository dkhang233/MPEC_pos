package com.pos.app.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

// Controller dung cho main view
public class MainController {
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
        System.out.println("Clicked");
    }
}
