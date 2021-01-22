package cn.tedu.sp09.controller;

import cn.tedu.sp01.pojo.Item;
import cn.tedu.sp01.pojo.Order;
import cn.tedu.sp01.pojo.User;
import cn.tedu.sp09.feign.ItemClient;
import cn.tedu.sp09.feign.OrderClient;
import cn.tedu.sp09.feign.UserClient;
import cn.tedu.web.util.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class FeignController {

    @Autowired
    private ItemClient itemClient;
    @Autowired
    private UserClient userClient;
    @Autowired
    private OrderClient orderClient;

    @GetMapping("/item-service/{orderId}")
    public JsonResult<List<Item>> getItems(@PathVariable("orderId") String orderId){
        // 通过ItemClient声明式客服端接口，调用远程服务
        return itemClient.getItems(orderId);
    }

    @PostMapping("/item-service/decreaseNumber")
    public JsonResult<?> decreaseNumber(@RequestBody List<Item> items){
        return itemClient.decreaseNumer(items);
    }

    @GetMapping("/user-service/{userId}")
    public JsonResult<User> getUser(@PathVariable Integer userId){
        return userClient.getUser(userId);
    }

    @GetMapping("/user-service/{userId}/score")
    public JsonResult<?> addScore(@PathVariable Integer userId,Integer score){
        return userClient.addScore(userId,score);
    }

    @GetMapping("/order-service/{orderId}")
    public JsonResult<Order> getOrder(@PathVariable String orderId){
        return orderClient.getOrder(orderId);
    }

    @GetMapping("/order-service")
    public JsonResult<?> addOrder(){
        return orderClient.addOrder();
    }
}
