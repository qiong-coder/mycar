package com.mycar.service.impl;



import com.alibaba.fastjson.JSONObject;
import com.mycar.logic.VehicleCostLogic;
import com.mycar.mapper.OrderMapper;
import com.mycar.model.*;
import com.mycar.model.Order;
import com.mycar.response.OrderConflict;
import com.mycar.response.OrderHistory;
import com.mycar.response.OrderSchedule;
import com.mycar.response.VehicleInfoCount;
import com.mycar.service.OrderService;
import com.mycar.service.VehicleInfoCostService;
import com.mycar.service.VehicleService;
import com.mycar.service.WeiXinPayService;
import com.mycar.utils.*;
import com.mycar.utils.OrderUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.TimeoutDeferredResultProcessingInterceptor;

import javax.xml.crypto.Data;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by stupid-coder on 7/18/17.
 */
@Service("OrderService")
public class OrderServiceImpl implements OrderService {

    private static Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private WeiXinPayService weiXinPayService;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private VehicleInfoCostService vehicleInfoCostService;

    private static void mergeInfo(Order l, Order r)
    {
        if ( r == null ) return;
        if ( r.getPay_info() != null )
            l.setPay_info(VehicleCostLogic.mergeInfo(l.getPay_info(),r.getPay_info()));

        if ( r.getCost_info() != null )
            l.setCost_info(VehicleCostLogic.mergeInfo(l.getCost_info(),r.getCost_info()));
    }

    @Override
    public Order getOrderById(long id) {
        Order order = orderMapper.getOrderById(id);
        if ( order == null ) logger.warn("falure to find the order by id - id:{}", id);
        return order;
    }

    @Override
    public List<Order> getOrdersByStatus(int status) {
        return orderMapper.getOrdersByStatus(status);
    }

    @Override
    public List<OrderStatusCount> getOrdersNumberByStatus() {
        List<OrderStatusCount> statusCounts = orderMapper.getOrdersNumberByStatus();
        if ( statusCounts == null && statusCounts.isEmpty() )logger.warn("failure to find the status number");
        return statusCounts;
    }

    @Override
    public List<Order> getOrdersByIdentityAndPhone(String identity, String phone) {
        List<Order> orders = orderMapper.getOrdersByIdentityAndPhone(identity,phone);
        if ( orders == null || orders.isEmpty() ) logger.warn("failure to find the order by identity-{} and phone-{}", identity,phone);
        return orders;
    }

    private JSONObject mergetVehicleInfoAndVehicle(List<Order> orderList) {
        JSONObject ret = new JSONObject();
        ret.put("vehicleInfos",vehicleService.getVehicleInfosByOrders(orderList));
        ret.put("vehicles", vehicleService.getVehiclesByOrders(orderList));
        ret.put("orders", orderList);
        return ret;
    }

    @Override
    public JSONObject getOrdersAndVehicleInfosByStatus(int status) {
        List<Order> orderList = getOrdersByStatus(status);

        if ( orderList != null && !orderList.isEmpty() ) {
           return mergetVehicleInfoAndVehicle(orderList);
        } else return null;
    }

    @Override
    public JSONObject getOrdersAndVechileInfosByIdentityAndPhone(String identity, String phone) {
        List<Order> orderList = getOrdersByIdentityAndPhone(identity, phone);

        if ( orderList != null && !orderList.isEmpty()) {
           return mergetVehicleInfoAndVehicle(orderList);
        } else return null;
    }

    @Override
    public JSONObject getOrderAndVehicleInfoByOrderId(long id) {
        JSONObject ret = null;
        Order order = getOrderById(id);
        if ( order != null ) {
            VehicleInfo vehicleInfo = vehicleService.getVehicleInfoById(order.getViid());
            if ( vehicleInfo != null ) {
                ret = new JSONObject();
                ret.put("order",order);
                ret.put("vehicleInfo", vehicleInfo);
            }
            if ( order.getVid() != null ) {
                Vehicle vehicle = vehicleService.getVehicleById(order.getVid());
                if ( vehicle != null ) ret.put("vehicle", vehicle);
            }
        } return ret;
    }

    @Override
    public int updateOrder(Order order) {
        return orderMapper.updateOrder(order);
    }

    @Override
    public int deleteOrderById(Long id) {
        return orderMapper.deleteOrderById(id);
    }

    @Override
    public int insertOrder(long viid, Order order) {
        VehicleInfo vehicleInfo = vehicleService.getVehicleInfoById(viid);
        if ( vehicleInfo == null ) {
            logger.warn("failure to get the vehicle info by id - id:{}", viid);
            return HttpStatus.NO_VEHICLE_INFO;
        }

        order.setViid(viid);
        order.setOid(OrderUtils.getOrderId(order.getViid(),order.getRent_sid()));
        VehicleInfoCost vehicleInfoCost = vehicleInfoCostService.getVehicleInfoCostById(viid);

//        int pay =

        VehicleCostLogic.getPreCostInfo(vehicleInfoCost,order);

        //order.setPay_info(VehicleCostLogic.getPayInfo(pay, order.getName()));
        //order.setCost_info("[]");
        if ( orderMapper.insertOrder(order) != 1 ) return HttpStatus.ERROR;

        return HttpStatus.OK;
    }

    @Override
    public int checkOrder(long oid) {
        Order order = getOrderById(oid);
        if ( order == null ) return HttpStatus.NO_ORDER;

        OrderStatus status = OrderStatus.values()[order.getStatus()];

        if ( status.compareTo(OrderStatus.PENDING) == 0 ) return HttpStatus.OK;

        if ( status.compareTo(OrderStatus.UNPAID) != 0 ) {
            logger.error("order's status is not unpaid - {}", order);
            return HttpStatus.ORDER_STATUS_ERROR;
        } else if (  weiXinPayService.checkPay(oid) ) {
            order.setStatus(OrderStatus.PENDING.getStatus());
            if ( orderMapper.updateStatus(order) == 1 ) return HttpStatus.OK;
            else {
                logger.error("failure to update the status to PENDING");
                return HttpStatus.ERROR;
            }
        } else {
            logger.error("failure to check the order's pay status - {}",order);
            return HttpStatus.ERROR;
        }
    }

    @Override
    public int rentOrder(long id, Order order, String number) {
        Vehicle vehicle = vehicleService.getVehicleByNumber(number);
        if ( vehicle == null ) {
            logger.warn("failure to find the vehicle by number:{}",number);
            return HttpStatus.NO_VEHICLE;
        }

        Order o = getOrderById(id);
        if ( o == null ) return HttpStatus.NO_ORDER;

        OrderStatus status = OrderStatus.values()[o.getStatus()];
        if ( status != OrderStatus.PENDING ) {
            logger.warn("the order' status is not pending - order:{}", o);
            return HttpStatus.ORDER_STATUS_ERROR;
        }

        if (order.getDrivers() != null && order.getDrivers().compareTo("") != 0 ) o.setDrivers(order.getDrivers());
        o.setVid(vehicle.getId());

        if (order.getViid().compareTo(vehicle.getViid()) != 0 ) order.setViid(vehicle.getViid());

        o.setRbegin(order.getRbegin());
        if ( order.getRend() != null ) o.setRend(order.getRend());
        else o.setRend(o.getEnd());

        o.setRrent_sid(order.getRrent_sid());
        if ( order.getRreturn_sid() != null ) o.setRreturn_sid(order.getRreturn_sid());
        else o.setRreturn_sid(o.getReturn_sid());

        o.setDistance(order.getDistance());

        vehicle.setBegin(o.getRbegin());
        vehicle.setEnd(o.getRend());
        vehicle.setStatus(VehicleStatus.RENTING.getStatus());
        //vehicle.setSid(o.getRrent_sid());

        mergeInfo(o,order);

        o.setStatus(OrderStatus.RENTING.getStatus());

        if ( orderMapper.updateRentingOrder(o) == 1 && vehicleService.updateVehicleById(vehicle) == 1 )
            return HttpStatus.OK;
        else return HttpStatus.ERROR;
    }

    @Override
    public int drawBackOrder(long id, Order order) {

        Order o = getOrderById(id);

        if ( o == null ) return HttpStatus.NO_ORDER;

        if ( o.getStatus() != OrderStatus.RENTING.getStatus() ) {
            logger.warn("the order's status is not renting - {}", order);
            return HttpStatus.ORDER_STATUS_ERROR;
        }

        o.setRend(order.getRend());
        o.setRreturn_sid(order.getRreturn_sid());
        o.setDistance(order.getDistance());

        mergeInfo(o,order);

        o.setStatus(OrderStatus.DRAWBACK.getStatus());

        Vehicle vehicle = vehicleService.getVehicleById(o.getVid());
        vehicle.setStatus(VehicleStatus.OK.getStatus());
        vehicleService.updateVehicleById(vehicle);

        if ( orderMapper.updateInfoAndStatus(o) == 1 ) return HttpStatus.OK;
        else return HttpStatus.ERROR;
        //TODO: 退款线程
    }

    @Override
    public int finishedOrder(long id, Order order) {

        Order o = getOrderById(id);

        if ( o == null ) return HttpStatus.NO_ORDER;

        if ( o.getStatus() != OrderStatus.DRAWBACK.getStatus() ) {
            logger.warn("the order's status is not renting - {}", order);
            return HttpStatus.ORDER_STATUS_ERROR;
        }

        mergeInfo(o,order);
        o.setStatus(OrderStatus.FINISHED.getStatus());

        if ( orderMapper.updateInfoAndStatus(o) == 1 ) return  HttpStatus.OK;
        else return HttpStatus.ERROR;
    }

    @Override
    public int cancleOrder(long id, Order order) {
        Order o = getOrderById(id);

        if ( o == null ) return HttpStatus.NO_ORDER;

        // TODO: 退款操作

        mergeInfo(o,order);
        o.setStatus(OrderStatus.CANCLED.getStatus());
        if ( orderMapper.updateInfoAndStatus(o) == 1 ) return HttpStatus.OK;
        else return HttpStatus.ERROR;
    }

    @Override
    public OrderHistory orderHistory(Long viid, String number, Timestamp begin, Timestamp end) {
        OrderHistory orderHistory = new OrderHistory();
        List<Vehicle> vehicles = null;
        List<Order> orders = null;

        if ( viid != null ) {
            vehicles = vehicleService.getAllVehiclesByViid(viid,null);
            orders = orderMapper.getOrdersByViidInInterval(viid,begin,end);
        } else if ( number != null ) {
            Vehicle vehicle = vehicleService.getVehicleByNumber(number);
            if ( vehicle != null ) {
                vehicles = new ArrayList<>();
                vehicles.add(vehicle);
                orders = orderMapper.getOrdersByVidInInterval(vehicle.getId(), begin, end);
            }
        } else {
            vehicles = vehicleService.getAllVehicles(null);
            orders = orderMapper.getOrdersByViidInInterval(null,begin,end);
        }

        if ( vehicles == null ||  vehicles.isEmpty() ) return orderHistory;

        Map<Long, VehicleInfo> vehicleInfos = new HashMap<>();

        int use_days = 0;
        if ( orders != null ) {
            for (Order order : orders) {
                OrderHistory.OrderHistoryItem orderHistoryItem = orderHistory.new OrderHistoryItem();

                Vehicle vehicle = null;
                for (Vehicle vehicle_iter : vehicles) {
                    if (vehicle_iter.getId().compareTo(order.getVid()) == 0) {
                        vehicle = vehicle_iter;
                        break;
                    }
                }
                if (vehicle == null) continue;

                int days = TimeUtils.TimeDiff(begin.compareTo(order.getRbegin()) > 0 ? begin : order.getRbegin(),
                        end.compareTo(order.getRend()) > 0 ? order.getRend() : end);
                use_days += days;

                if (vehicleInfos.containsKey(order.getViid()))
                    orderHistoryItem.setName(vehicleInfos.get(order.getViid()).getName());
                else {
                    VehicleInfo vehicleInfo = vehicleService.getVehicleInfoById(order.getViid());
                    if (vehicleInfo == null) continue;
                    orderHistoryItem.setName(vehicleInfo.getName());
                    vehicleInfos.put(order.getViid(), vehicleInfo);
                }

                orderHistoryItem.setNumber(vehicle.getNumber());
                orderHistoryItem.setOid(order.getOid());
                orderHistoryItem.setRet_day(days);
                orderHistory.getHistory().add(orderHistoryItem);
            }
        }

        orderHistory.setRet_day_total(use_days);

        int total_days = 0;
        for ( Vehicle vehicle : vehicles ) {
            if (vehicle.getCreate_time().compareTo(end) > 0 ) continue;
            total_days += TimeUtils.TimeDiff(
                    begin.compareTo(vehicle.getCreate_time()) >= 0 ? begin : vehicle.getCreate_time(),
                    end);
        }
        if ( total_days - use_days >= 0 )
            orderHistory.setIdle_day(total_days-use_days);
        else orderHistory.setIdle_day(0);

        return orderHistory;
    }

    @Override
    public List<OrderSchedule> orderSchedule(Long viid, Timestamp begin, Timestamp end) {
        List<Order> orders = orderMapper.getOrdersByScheduleInterval(viid, begin, end, null);
        Map<Long, VehicleInfoCount> vehicleInfoCounts = vehicleService.getVehicleCount(viid);

        int total_days = TimeUtils.TimeDiff(begin,end);

        Map<Long,Long[]> use_vehicle = new HashMap<>();

        for ( Order order : orders ) {
            Long id = order.getViid();
            Long[] viid_use_vehicle;
            if ( !use_vehicle.containsKey(id) ) {
                viid_use_vehicle = new Long[total_days];
                use_vehicle.put(id,viid_use_vehicle);
            } else {
                viid_use_vehicle = use_vehicle.get(id);
            }
            Timestamp use_begin;
            Timestamp use_end;
            if ( order.getStatus() == OrderStatus.PENDING.getStatus() ) {
                use_begin = order.getBegin().compareTo(begin) <= 0 ? begin : order.getBegin();
                use_end = order.getEnd().compareTo(end) >= 0 ? end : order.getEnd();
            } else if ( order.getStatus() == OrderStatus.RENTING.getStatus() ) {
                use_begin = order.getRbegin().compareTo(begin) <= 0 ? begin : order.getRbegin();
                use_end = order.getRend().compareTo(end) >= 0 ? end : order.getRend();
            } else continue;

            int begin_index = TimeUtils.TimeDiff(begin,use_begin);
            int end_index = TimeUtils.TimeDiff(begin,use_end);
            //if (end_index > total_days) end_index = total_days;

            for ( int i = begin_index; i < end_index; ++ i ) {
                if ( viid_use_vehicle[i] == null ) viid_use_vehicle[i] = new Long(1);
                else viid_use_vehicle[i] += 1;
            }
        }

        List<OrderSchedule> orderSchedules = new ArrayList<>();
        for ( Map.Entry<Long,Long[]> entry : use_vehicle.entrySet() ) {
            Long id = entry.getKey();
            Long[] viid_use_vehicle = entry.getValue();
            Calendar calender = Calendar.getInstance();
            calender.setTime(begin);

            VehicleInfoCount vehicleInfoCount = vehicleInfoCounts.get(id);
            if ( vehicleInfoCount == null ) return null;

            OrderSchedule orderSchedule = new OrderSchedule(vehicleInfoCount.getName(),
                    vehicleInfoCount.getCount(),
                    vehicleInfoCount.getCount()-vehicleInfoCount.getSpare()-(viid_use_vehicle[0]==null?0:viid_use_vehicle[0]),
                    calender.getTime());
            int index = 0;
            while ( index++ < viid_use_vehicle.length ) {

                long stock = vehicleInfoCount.getCount() - vehicleInfoCount.getSpare() - (viid_use_vehicle[index-1] == null?0:viid_use_vehicle[index-1]);

                if ( orderSchedule.getStock() == stock ) {
                    calender.add(Calendar.DATE,1);
                    continue;
                } else {
                    orderSchedule.setEnd(calender.getTime());
                    orderSchedules.add(orderSchedule);
                    orderSchedule = new OrderSchedule(vehicleInfoCount.getName(),
                            vehicleInfoCount.getCount(),
                            stock,
                            calender.getTime());
                }
            }
            orderSchedule.setEnd(calender.getTime());
            orderSchedules.add(orderSchedule);
        }

        return orderSchedules;
    }

    @Override
    public OrderConflict orderConflict(Long viid, Timestamp begin, Timestamp end) {
        OrderConflict orderConflict = new OrderConflict();
        // 获取所有当前车型下车的总数量
        Map<Long, VehicleInfoCount> vehicleInfoCounts = vehicleService.getVehicleCount(viid);

        VehicleInfoCount vehicleInfoCount = vehicleInfoCounts.get(viid);

        if ( vehicleInfoCount == null ) return null;

        orderConflict.setTotal(vehicleInfoCount.getCount()+vehicleInfoCount.getSpare());

        // 获取未来的订单
        List<Order> orders = orderMapper.getOrdersByScheduleInterval(viid, begin, end, null);
        List<Order> pending_orders = new ArrayList<>();
        for ( Order order : orders ) {
            if ( order.getStatus() == OrderStatus.PENDING.getStatus() ) {
                pending_orders.add(order);
            }
        }

        orderConflict.setUsed(new Long(orders.size()-pending_orders.size()));
        orderConflict.setTo_used(new Long(orders.size()));
        orderConflict.setOrders(pending_orders);
        return orderConflict;
    }
}
