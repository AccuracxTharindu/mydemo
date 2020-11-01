package com.example.mydemo.resources;

import com.example.mydemo.models.Item;
import com.example.mydemo.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/items")

    public class ItemResource {
        @Autowired
        ItemService itemService;

        @GetMapping("")
        public List<Item> list() {
            return itemService.listAllItems();
        }

        @GetMapping("/{id}")
        public ResponseEntity<Item> get(@PathVariable Integer id) {
            try {
                Item item = itemService.getItem(id);
                return new ResponseEntity<Item>(item, HttpStatus.OK);
            } catch (NoSuchElementException e) {
                return new ResponseEntity<Item>(HttpStatus.NOT_FOUND);
            }
        }
        @PostMapping("/")
        public void add(@RequestBody Item item) {
            itemService.saveItem(item);
        }
        @PutMapping("/{id}")
        public ResponseEntity<?> update(@RequestBody Item item, @PathVariable Integer id) {
            try {
                Item existUser = itemService.getItem(id);
                item.setId(id);
                itemService.saveItem(item);
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (NoSuchElementException e) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        @DeleteMapping("/{id}")
        public void delete(@PathVariable Integer id) {

            itemService.deleteItem(id);
        }
    }