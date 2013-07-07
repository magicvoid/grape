package com.sintn.grape.server.data;

import org.springside.modules.test.data.RandomData;

import com.sintn.grape.server.entity.Shop;
import com.sintn.grape.server.entity.User;

/**
 * Task相关实体测试数据生成.
 * 
 * @author calvin
 */
public class ShopData {

	public static Shop randomShop() {
		Shop shop = new Shop();
		shop.setName(randomName());
		User user = new User(1L);
		shop.setOwner(user);
		return shop;
	}

	public static String randomName() {
		return RandomData.randomName("Shop");
	}
}
