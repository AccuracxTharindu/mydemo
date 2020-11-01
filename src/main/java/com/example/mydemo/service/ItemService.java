package com.example.mydemo.service;

import com.example.mydemo.models.Item;
import com.example.mydemo.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service
@Transactional
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;
    public List<Item> listAllItems() {
        return itemRepository.findAll();
    }

    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    public Item getItem(Integer id) {
        return itemRepository.findById(id).get();
    }

    public void deleteItem(Integer id) {
        itemRepository.deleteById(id);
    }
}
