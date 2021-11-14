package com.example.shop;

public class CustomerHelper {
    String customerName;
    String customerNumber;
    String customerPassword;
    public CustomerHelper() {
    }

    public CustomerHelper(String customerName, String customerNumber, String customerPassword) {
        this.customerName = customerName;
        this.customerNumber = customerNumber;
        this.customerPassword = customerPassword;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getCustomerPassword() {
        return customerPassword;
    }

    public void setCustomerPassword(String customerPassword) {
        this.customerPassword = customerPassword;
    }
}
