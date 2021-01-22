package cn.tedu.sp04.order.feign;

import cn.tedu.sp01.pojo.Item;
import cn.tedu.web.util.JsonResult;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ItemClientFB implements ItemClient{

    @Override
    public JsonResult<List<Item>> getItems(String orderId) {
        // 从缓存中取数据
        if (Math.random() < 0.5) {
            List<Item> list=new ArrayList<>();
            list.add(new Item(1,"缓存商品1",1));
            list.add(new Item(2,"缓存商品2",3));
            list.add(new Item(3,"缓存商品3",5));
            list.add(new Item(4,"缓存商品4",2));
            list.add(new Item(5,"缓存商品5",1));
            return JsonResult.ok().data(list);
        }
        return JsonResult.err().msg("获取商品列表失败");
    }

    @Override
    public JsonResult<?> decreaseNumber(List<Item> items) {
        return JsonResult.err().msg("减少商品库存失败");
    }
}
