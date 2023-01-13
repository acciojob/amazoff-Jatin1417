package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;
@Repository
public class OrderRepository {
    HashMap<String,Order> ordermap = new HashMap<>();
    HashMap<String,DeliveryPartner> partnermap = new HashMap<>();
    HashMap<String,Boolean> ordersAssignedMap = new HashMap<>();
    HashMap<String,List<String>> ordersPartnerPair = new HashMap<>();

    public void addorder(Order order){
     String id = order.getId();
     ordermap.put(id,order);
    }
    public void addPartner(String pid){
        DeliveryPartner partner = new DeliveryPartner(pid);
        partnermap.put(pid,partner);
    }
    public void addOrderPartner(String Oid, String pid){
        String order = ordermap.get(Oid).getId();
        List<String> orderlist = new ArrayList<>();
        if(!ordersPartnerPair.containsKey(pid)){
        orderlist.add(order);
        ordersPartnerPair.put(pid,orderlist);
    }else{
        orderlist = ordersPartnerPair.get(pid);
        orderlist.add(order);
        ordersPartnerPair.put(pid,orderlist);
    }
        DeliveryPartner deliveryPartner = partnermap.get(pid);
        deliveryPartner.setNumberOfOrders(orderlist.size());
      ordersAssignedMap.put(order,true);
    }
    public Order getorderByid(String id){
          Order order = ordermap.get(id);
          return order;
    }
    public DeliveryPartner getPartner(String id){
        DeliveryPartner partner = partnermap.get(id);
        return partner;
    }
    public int OrderCount(String id){
        int count = ordersPartnerPair.get(id).size();
        return count;
    }
    public List<String> getOrdersbyPartner(String id){
        List<String> order = ordersPartnerPair.get(id);
        return order;
    }
    public List<String> getAllOrders(){
        List<String> orderlist = new ArrayList<String>();
        for(Order oid: ordermap.values()){
            orderlist.add(oid.getId());
        }
        return orderlist;
    }
    public int getCountOfUnassignedOrders(){
        int count = 0;
        for(boolean t: ordersAssignedMap.values()){
            if(t==false){
                count++;
            }
        }
        return count;
    }

    public void deletePartnerById(String id){
        List<String> order = ordersPartnerPair.get(id);
        ordersPartnerPair.remove(id);
        for(String o: order){
            if(ordersAssignedMap.containsKey(o)){
                ordersAssignedMap.put(o, false);
            }
        }
        partnermap.remove(id);
    }
    public void deleteOrderById(String id){
          // Map order id and partnerid.... remove order object
        for(String pid: ordersPartnerPair.keySet()){
            for(String oid: ordersPartnerPair.get(pid)){
                if(oid==id){
                    ordersPartnerPair.get(pid).remove(oid);
                }
            }
        }
        ordermap.remove(id);
        ordersAssignedMap.remove(id);
    }
   public String getLastDeliverytime(String id){
        int t = 0;
        List<String> order = ordersPartnerPair.get(id);
        for(String oid: order){
            int time = ordermap.get(oid).getDeliveryTime();
            if(time>=t){
                t = time;
            }
        }
        String stime = Integer.toString(t/60) + ":" + Integer.toString(t%60);
        return stime;
   }
    public int getOrdersLeft(String time,String pid){
        List<String> order = ordersPartnerPair.get(pid);
        int ans=0;
        String s[] = time.split(":");
        int newtime = Integer.parseInt(s[0])*60+Integer.parseInt(s[1]);
        for(String oid: order){
            int t = ordermap.get(oid).getDeliveryTime();
            if(t>=newtime){
               ans++;
            }
        }
        return ans;
    }

}
