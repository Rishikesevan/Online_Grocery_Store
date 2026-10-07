package com.project.Grocery_Store.Entity;


import jakarta.persistence.*;
import lombok.*;

//@ToString(onlyExplicitlyIncluded = true)
@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        
        private Long id;
        
        private String name;
        
        private double price;

        @Lob
        @Column(columnDefinition = "LONGBLOB")
        
        private byte[] image; // Store image as BLOB

        @Column(columnDefinition = "LONGTEXT")
        
        private String description;
        
        private Integer quantity;

        @ManyToOne
        @JoinColumn(name = "category_id")
        private Category category;

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public double getPrice() { return price; }
        public void setPrice(double price) { this.price = price; }

        public byte[] getImage() { return image; }
        public void setImage(byte[] image) { this.image = image; }

        public Category getCategory() { return category; }
        public void setCategory(Category category) { this.category = category; }

//        @Override
//        public String toString() {
//                return "Product{" +
//                        "category=" + category +
//                        ", id=" + id +
//                        ", name='" + name + '\'' +
//                        ", price=" + price +
//                        '}';
//        }
}
