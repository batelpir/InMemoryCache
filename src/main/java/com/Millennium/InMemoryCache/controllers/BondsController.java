package com.Millennium.InMemoryCache.controllers;

import com.Millennium.InMemoryCache.bl.CacheService;
import com.Millennium.InMemoryCache.model.Bond;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BondsController {
    @Autowired
    CacheService<String, Bond> service;

    @GetMapping("/bond/{bondId}")
    public ResponseEntity<Bond> getBond(@PathVariable String bondId){
        service.get(bondId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/bond")
    public ResponseEntity<Void> postBond(){
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
