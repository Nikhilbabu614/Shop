package com.example.shop;

import java.util.ArrayList;

public class   OrdersHelper {
    public String sellerNo;
    public String custNo;
    public String deliverNo;
    public ArrayList<String>  itemslist = new ArrayList<>();
    public boolean OrderConfirm;

    public String getSellerNo() {
        return sellerNo;
    }

    public void setSellerNo(String sellerNo) {
        this.sellerNo = sellerNo;
    }

    public String getCustNo() {
        return custNo;
    }

    public void setCustNo(String custNo) {
        this.custNo = custNo;
    }

    public String getDeliverNo() {
        return deliverNo;
    }

    public void setDeliverNo(String deliverNo) {
        this.deliverNo = deliverNo;
    }

    public ArrayList<String> getItemslist() {
        return itemslist;
    }

    public void setItemslist(ArrayList<String> itemslist) {
        this.itemslist = itemslist;
    }

    public boolean isOrderConfirm() {
        return OrderConfirm;
    }

    public void setOrderConfirm(boolean orderConfirm) {
        OrderConfirm = orderConfirm;
    }

    public OrdersHelper(String sellerNo, String custNo, String deliverNo, ArrayList<String> itemslist, boolean orderConfirm) {
        this.sellerNo = sellerNo;
        this.custNo = custNo;
        this.deliverNo = deliverNo;
        this.itemslist = itemslist;
        OrderConfirm = orderConfirm;
    }

    public OrdersHelper() {
    }
}
