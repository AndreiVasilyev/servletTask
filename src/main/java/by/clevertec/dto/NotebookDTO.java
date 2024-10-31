package by.clevertec.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotebookDTO {
    private String id;
    private String model;
    private String description;
    private String vendorName;
    private int quantity;
    private double price;
}
