package cn.tedu.sp04.order.service;

import cn.tedu.sp01.pojo.Item;
import cn.tedu.sp01.pojo.Order;
import cn.tedu.sp01.pojo.User;
import cn.tedu.sp01.service.OrderService;
import cn.tedu.sp04.order.feign.ItemClient;
import cn.tedu.sp04.order.feign.UserClient;
import cn.tedu.web.util.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ItemClient itemClient;
    @Autowired
    private UserClient userClient;

    @Override
    public Order getOrder(String orderId) {
        JsonResult<List<Item>> items = itemClient.getItems(orderId);
        JsonResult<User> user = userClient.getUser(8);
        Order order=new Order();
        order.setId(orderId);
        order.setUser(user.getData());
        order.setItems(items.getData());
        return order;
    }

    @Override
    public void addOrder(Order order) {
        log.info("添加订单："+order);
        itemClient.decreaseNumber(order.getItems());
        userClient.addScore(order.getUser().getId(), 1000);
    }
}
