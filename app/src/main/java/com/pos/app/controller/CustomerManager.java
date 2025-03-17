package com.pos.app.controller;

import com.pos.app.model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CustomerManager {
    private final ObservableList<Customer> customers;

    // Khởi tạo danh sách khách hàng
    public CustomerManager() {
        this.customers = FXCollections.observableArrayList(
                new Customer(1, "Nguyen", "Huy", "huy@gmail.com", "0123456789"),
                new Customer(2, "Danh", "Khang", "khang@gmail.com", "0987654321")
        );
    }

    // Lấy danh sách khách hàng
    public ObservableList<Customer> getAllCustomers() {
        return customers;
    }

    // Thêm khách hàng mới
    public void createCustomer(Customer newCustomer) {
        customers.add(newCustomer);
        System.out.println("Thêm khách hàng: " + newCustomer.getFirstName());
    }

    // Xóa khách hàng
    public void deleteCustomer(Customer customer) {
        customers.remove(customer);
        System.out.println("Đã xóa khách hàng: " + customer.getFirstName());
    }

    // Cập nhật thông tin khách hàng
    public void updateCustomer(Customer customer, String newFirstName, String newLastName, String newEmail, String newPhone) {
        customer.setFirstName(newFirstName);
        customer.setLastName(newLastName);
        customer.setEmail(newEmail);
        customer.setPhoneNumber(newPhone);
        System.out.println("Cập nhật khách hàng: " + customer.getFirstName());
    }

    // Xuất dữ liệu khách hàng (chỉ để kiểm tra)
    public void exportCustomerData() {
        System.out.println("Danh sách khách hàng:");
        customers.forEach(c -> System.out.println(c.getId() + " - " + c.getFirstName() + " " + c.getLastName()));
    }
}
