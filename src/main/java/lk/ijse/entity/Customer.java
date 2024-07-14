package lk.ijse.entity;

import lombok.*;



public class Customer {
    private String customer_id;
    private String name;
    private String contact;
    private String address;

    public Customer() {
    }

    public Customer(String customer_id, String name, String contact, String address) {
        this.customer_id = customer_id;
        this.name = name;
        this.contact = contact;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customer_id='" + customer_id + '\'' +
                ", name='" + name + '\'' +
                ", contact='" + contact + '\'' +
                ", address='" + address + '\'' +
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
