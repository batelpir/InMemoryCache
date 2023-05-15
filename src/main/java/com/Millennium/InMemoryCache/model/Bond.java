package com.Millennium.InMemoryCache.model;

import lombok.Data;

@Data
public class Bond {
    private String bondId;
    private String exchange;
    private String name;
    private String securityType;
    private String description;
    private String currency;
    private String country;

}
