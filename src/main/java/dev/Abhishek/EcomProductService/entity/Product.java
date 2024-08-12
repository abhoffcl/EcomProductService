package dev.Abhishek.EcomProductService.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Product extends BaseModel {
    private String title;
    private double price;
    private String description;
    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;
    private String imageURL;
    private double rating;
    private int quantity;
}
