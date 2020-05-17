package entities;

import java.sql.Date;

public class Product {
    private int id;
    private String name;
    private int category;
    private float price;
    private int userId;
    private Date date;
    private String imgSrc;
    private int validation;

    public Product() {
    }

    public Product(int id, String name, int category, float price, int userId, Date date, String imgSrc, int validation) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.userId = userId;
        this.date = date;
        this.imgSrc = imgSrc;
        this.validation = validation;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category=" + category +
                ", price=" + price +
                ", userId=" + userId +
                ", date=" + date +
                ", imgSrc='" + imgSrc + '\'' +
                ", validation=" + validation +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public int getValidation() {
        return validation;
    }

    public void setValidation(int validation) {
        this.validation = validation;
    }
}
