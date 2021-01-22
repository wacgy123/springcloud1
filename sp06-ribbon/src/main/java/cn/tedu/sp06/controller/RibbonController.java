package cn.tedu.sp06.controller;

import cn.tedu.sp01.pojo.Item;
import cn.tedu.sp01.pojo.Order;
import cn.tedu.sp01.pojo.User;
import cn.tedu.web.util.JsonResult;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedList;
import java.util.List;

@RestController
@Slf4j
public class RibbonController {

    @Autowired
    private RestTemplate restTemplate;

    // 获取商品列表
    @HystrixCommand(fallbackMethod = "getItemsFB")
    @GetMapping("/item-service/{orderId}")
    public JsonResult<List<Item>> getItems(@PathVariable String orderId){
        // 调用远程的商品服务，获取商品列表
        String url="http://item-service:8001/{1}";
        return restTemplate.getForObject(url,JsonResult.class,orderId);
    }

    // 减少商品库存
    @HystrixCommand(fallbackMethod = "decreaseNumberFB")
    @PostMapping("/item-service/decreaseNumber")
    public JsonResult<?> decreaseNumber(@RequestBody List<Item> items){
        String url="http://item-service:8001/decreaseNumber";
        return restTemplate.postForObject(url,items,JsonResult.class);
    }

    @HystrixCommand(fallbackMethod = "getUserFB")
    @GetMapping("/user-service/{userId}")
    public JsonResult<User> getUser(@PathVariable Integer userId){
        String url="http://userservice:8101/{1}";
        return restTemplate.getForObject(url, JsonResult.class, userId);
    }

    @HystrixCommand(fallbackMethod = "getUserFB")
    @GetMapping("/user-service/{userId}/score")
    public JsonResult<?> addScore(@PathVariable Integer userId,Integer score){
        String url="http://userservice:8101/{1}/score?score={2}";
        return restTemplate.getForObject(url, JsonResult.class, userId,score);
    }

    @GetMapping("/order-service/{orderId}")
    @HystrixCommand(fallbackMethod = "getOrderFB")
    public JsonResult<Order> getOrder(@PathVariable String orderId){
        String url="http://order-service:8201/{1}";
        return restTemplate.getForObject(url, JsonResult.class, orderId);
    }

    @GetMapping("/order-service")
    @HystrixCommand(fallbackMethod = "addOrderFB")
    public JsonResult<?> addOrder(){
        String url="http://order-service:8201";
        return restTemplate.getForObject(url,JsonResult.class);
    }

    //获取商品列表降级方法
    public JsonResult<List<Item>> getItemsFB(String orderId){
        return JsonResult.err().msg("获取商品列表失败");
    }

    public JsonResult<?> decreaseNumberFB(List<Item> items){
        return JsonResult.err().msg("减少商品库存失败");
    }

    public JsonResult<User> getUserFB(Integer userId){
        return JsonResult.err().msg("获取用户失败");
    }

    public JsonResult<?> addScoreFB(Integer userId,Integer score){
        return JsonResult.err().msg("增加积分失败");
    }

    public JsonResult<Order> getOrderFB(String orderId){
        return JsonResult.err().msg("获取订单失败");
    }

    public JsonResult<?> addOrderFB(){
        return JsonResult.err().msg("添加订单失败");
    }
}
