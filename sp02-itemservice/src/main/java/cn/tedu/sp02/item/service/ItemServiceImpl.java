package cn.tedu.sp02.item.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import cn.tedu.sp01.pojo.Item;
import cn.tedu.sp01.service.ItemService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class ItemServiceImpl implements ItemService {

    @Override
    public List<Item> getItems(String orderId) {
        ArrayList<Item> items = new ArrayList<>();
        items.add(new Item(1, "商品 1",1));
        items.add(new Item(2, "商品 2",4));
        items.add(new Item(3, "商品 3",2));
        items.add(new Item(4, "商品 4",6));
        items.add(new Item(5, "商品 5",1));
        return items;
    }

    @Override
    public void decreaseNumbers(List<Item> items) {
        for(Item item : items) {
            log.info("减少商品库存："+item);
        }
    }
}