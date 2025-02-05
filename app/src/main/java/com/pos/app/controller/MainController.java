package com.pos.app.controller;

import com.gluonhq.charm.glisten.control.Avatar;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.Objects;

// Controller dung cho main view
public class MainController {

    @FXML
    private Pane wrapView;

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
        hideAllView();
        itemsView.setVisible(true);
    }

    // Xu li khi nguoi dung chon items kits
    @FXML
    public void onItemsKitsClicked(){
        hideAllView();
        itemsKitsView.setVisible(true);
    }

    private void hideAllView() {
        for (Node node : wrapView.getChildren()) {
            node.setVisible(false);
        }
    }

//    public void initialize(){
//        // Set hình ảnh cho avatar
//        try {
//            avatar.setImage(new Image( Objects.requireNonNull(getClass().getClassLoader().getResource("static/default-item.png")).toExternalForm()));
//
//            System.out.println("----------Avatar set--------" + avatar.getImage().getUrl());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
