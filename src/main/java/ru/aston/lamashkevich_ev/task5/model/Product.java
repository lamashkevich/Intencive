package ru.aston.lamashkevich_ev.task5.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Product {
    private BigDecimal price;
    private Category category;
}
