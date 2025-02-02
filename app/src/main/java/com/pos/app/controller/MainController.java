package com.pos.app.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

// Controller dung cho main view
public class MainController {
    @FXML
    private Pane itemsView;

    @FXML
    private Pane itemsKitsView;


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
    }

    // Xu li khi nguoi dung chon items kits
    @FXML
    public void onItemsKitsClicked(){
        itemsView.setVisible(false);
        itemsKitsView.setVisible(true);
    }
}
