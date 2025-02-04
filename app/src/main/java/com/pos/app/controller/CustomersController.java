package com.pos.app.controller;


import com.pos.app.model.Customer;
import com.pos.app.model.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class CustomersController {
    @FXML
    public TableView<Customer> customersTable;

   public void initialize(){
       ObservableList<Customer> people = FXCollections.observableArrayList(
               Customer.builder()
                       .id(1)
                       .firstName("Do")
                       .lastName("Hieu")
                       .email("abc")
                       .build()
       );
       customersTable.setItems(people);
   }
}
