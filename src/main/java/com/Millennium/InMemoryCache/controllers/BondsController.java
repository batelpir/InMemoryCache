package com.Millennium.InMemoryCache.controllers;

import com.Millennium.InMemoryCache.bl.CacheService;
import com.Millennium.InMemoryCache.model.Bond;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class BondsController {
    @Autowired
    CacheService<String, Bond> service;

    @GetMapping("/bond/{bondId}")
    public ResponseEntity<Bond> getBond(@PathVariable String bondId){
        Bond bond = service.get(bondId);
        if(bond == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(bond,HttpStatus.OK);
    }

    @PostMapping("/bond")
    public ResponseEntity<Void> postBond(@RequestBody Bond bond){
        service.put(bond.getBondId(), bond);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
