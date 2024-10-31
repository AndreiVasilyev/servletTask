package by.clevertec.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Notebook {
    private String id;
    private String model;
    private String description;
    private String vendorName;
    private int quantity;
    private double price;
}
