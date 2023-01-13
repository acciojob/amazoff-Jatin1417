package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;
    public void addOrder(Order order){
        orderRepository.addorder(order);
    }
    public void addPartner(String id){
        orderRepository.addPartner(id);
    }
    public void addOrderPartnerPair(String oid, String pid){
        orderRepository.addOrderPartner(oid,pid);
    }
    public Order getOrderByid(String id){
        Order order = orderRepository.getorderByid(id);
        return order;
    }
    public DeliveryPartner getParnterById(String id){
        DeliveryPartner partner = orderRepository.getPartner(id);
        return partner;
    }
    public int getOrdercount(String id){
        int c = orderRepository.OrderCount(id);
        return c;
    }
    public List<String> getordersbyid(String id){
        List<String> orderlist = orderRepository.getOrdersbyPartner(id);
        return orderlist;
    }
    public List<String> getAllorder(){
        List<String> orders = orderRepository.getAllOrders();
        return  orders;
    }
   public int getcountOfunassinged(){
        int c = orderRepository.getCountOfUnassignedOrders();
        return c;
   }
   public void deletePartner(String id){
        orderRepository.deletePartnerById(id);
   }
   public void deleteOrder(String id){
        orderRepository.deleteOrderById(id);
   }
   public String getLastDelivery(String id){
       return orderRepository.getLastDeliverytime(id);
   }
   public int getOrdersLeft(String time, String pid){
        return orderRepository.getOrdersLeft(time, pid);
   }
}