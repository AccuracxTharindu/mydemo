package com.example.mydemo.service;

import com.example.mydemo.models.Item;
import com.example.mydemo.models.Order;
import com.example.mydemo.models.OrderItem;
import com.example.mydemo.repositories.ItemRepository;
import com.example.mydemo.repositories.OrderItemRepository;
import com.example.mydemo.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private OrderItemRepository orderitemRepository;

    public List<Order> listAllOrders() {
        return orderRepository.findAll();
    }

    public void saveOrder(Order order) {
        boolean saveOrder = true;
        List<OrderItem> orderItems = order.getOrderItems();
        for (OrderItem orderItem : orderItems) {
            int itemId = orderItem.getItem().getId();
            int orderQty = orderItem.getOrderQty();
            Optional<Item> itemOptional = itemRepository.findById(itemId);
            if (itemOptional.isPresent()) {
                Item item = itemOptional.get();
                if (item.getQty() >= orderQty) {
                    int newQty = item.getQty() - orderQty;
                    item.setQty(newQty);
                    orderitemRepository.save(orderItem);
                    itemRepository.save(item);
                } else {
                    saveOrder = false;
                }
            }
        }
        if (saveOrder) {
            orderRepository.save(order);
        }
    }

    public Order getOrder(Integer id) {
        return orderRepository.findById(id).get();
    }

    public void deleteOrder(Integer id) {
        orderRepository.deleteById(id);
    }
}
