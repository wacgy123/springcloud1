package cn.tedu.sp02.item.controller;

import cn.tedu.sp01.pojo.Item;
import cn.tedu.sp01.pojo.Order;
import cn.tedu.sp01.service.ItemService;
import cn.tedu.web.util.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
@Slf4j
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Value("${server.port}")
    private int port;

    @GetMapping("/{orderId}")
    public JsonResult<List<Item>> getItems(@PathVariable String orderId) throws InterruptedException {
        List<Item> items = itemService.getItems(orderId);
        log.info("获取商品列表，orderId="+orderId);

        //随机延迟
        if (Math.random() < 0.9) {
            //随机延迟时长
            int delay = new Random().nextInt(5000);
            System.out.println("延迟:"+delay);
            Thread.sleep(delay);
        }

        return JsonResult.ok().msg("server.port="+port).data(items);
    }

    // @RequestBody 完整地接收请求协议体数据
    @PostMapping("/decreaseNumber")
    public JsonResult<?> decreaseNumber(@RequestBody List<Item> items){
        itemService.decreaseNumbers(items);
        return JsonResult.ok().msg("减少商品库存成功");
    }
}
