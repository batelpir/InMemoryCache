package com.Millennium.InMemoryCache.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bond {
    private String bondId;
    private String exchange;
    private String name;
    private String securityType;
    private String description;
    private String currency;
    private String country;

}
