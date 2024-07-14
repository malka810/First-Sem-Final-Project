package lk.ijse.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;


public class CustomDTO {
    //Customer
    private String customer_id;
    private String name;
    private String address;
    private String contact;

    //Product

    private String product_id;
    private  String product_name;
    private String description;
    private String category;
    private int qty_on_hand;
    private BigDecimal weight;
    private BigDecimal unit_price;

    //Order
    private String order_id;
    private LocalDate order_date;
    private BigDecimal payment;
    //private String customer_id;
    private String user_id;

    //OrderDetails

    // private String order_id;
    // private String product_id;
    private int quantity;
    // private BigDecimal weight;
    // private BigDecimal unit_price;

    public CustomDTO() {
    }

    public CustomDTO(String customer_id, String name, String address, String contact, String product_id, String product_name, String description, String category, int qty_on_hand, BigDecimal weight, BigDecimal unit_price, String order_id, LocalDate order_date, BigDecimal payment, String user_id, int quantity) {
        this.customer_id = customer_id;
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.product_id = product_id;
        this.product_name = product_name;
        this.description = description;
        this.category = category;
        this.qty_on_hand = qty_on_hand;
        this.weight = weight;
        this.unit_price = unit_price;
        this.order_id = order_id;
        this.order_date = order_date;
        this.payment = payment;
        this.user_id = user_id;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "CustomDTO{" +
                "customer_id='" + customer_id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", contact='" + contact + '\'' +
                ", product_id='" + product_id + '\'' +
                ", product_name='" + product_name + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", qty_on_hand=" + qty_on_hand +
                ", weight=" + weight +
                ", unit_price=" + unit_price +
                ", order_id='" + order_id + '\'' +
                ", order_date=" + order_date +
                ", payment=" + payment +
                ", user_id='" + user_id + '\'' +
                ", quantity=" + quantity +
                '}';
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getQty_on_hand() {
        return qty_on_hand;
    }

    public void setQty_on_hand(int qty_on_hand) {
        this.qty_on_hand = qty_on_hand;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(BigDecimal unit_price) {
        this.unit_price = unit_price;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public LocalDate getOrder_date() {
        return order_date;
    }

    public void setOrder_date(LocalDate order_date) {
        this.order_date = order_date;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
