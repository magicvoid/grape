package com.sintn.grape.server.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.net.URI;
import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springside.modules.mapper.JsonMapper;
import org.springside.modules.test.category.Smoke;

import com.sintn.grape.server.BaseFunctionalTestCase;
import com.sintn.grape.server.data.ShopData;
import com.sintn.grape.server.data.TaskData;
import com.sintn.grape.server.entity.Shop;

/**
 * 店铺管理的功能测试.
 * 
 * @author magic
 */
public class ShopRestFT extends BaseFunctionalTestCase {

	private final RestTemplate restTemplate = new RestTemplate();

	private final JsonMapper jsonMapper = new JsonMapper();

	private static class ShopList extends ArrayList<Shop> {
	};

	private static String resoureUrl;

	@BeforeClass
	public static void initUrl() {
		resoureUrl = baseUrl + "/rest/shop";
	}

	/**
	 * 查看任务列表.
	 */
	@Test
	@Category(Smoke.class)
	public void listShops() {
		ShopList shops = restTemplate.getForObject(resoureUrl, ShopList.class);
//		assertEquals(5, shops.size());
		assertEquals("Shop", shops.get(0).getName().substring(0,4));
	}

	/**
	 * 获取任务.
	 */
	@Test
	@Category(Smoke.class)
	public void getShop() {
		Shop shop = restTemplate.getForObject(resoureUrl + "/{id}", Shop.class, 1L);
		assertEquals("Shop1", shop.getName());
	}

	/**
	 * 创建/更新/删除任务.
	 */
	@Test
	@Category(Smoke.class)
	public void createUpdateAndDeleteShop() {

		//create
		Shop shop = ShopData.randomShop();

		URI shopUri = restTemplate.postForLocation(resoureUrl, shop);
		Shop createdShop = restTemplate.getForObject(shopUri, Shop.class);
		assertEquals(shop.getName(), createdShop.getName());

		//update
		String id = StringUtils.substringAfterLast(shopUri.toString(), "/");
		shop.setId(new Long(id));
		shop.setName(TaskData.randomTitle());

		restTemplate.put(shopUri, shop);

		Shop updatedShop = restTemplate.getForObject(shopUri, Shop.class);
		assertEquals(shop.getName(), updatedShop.getName());

		//delete
		restTemplate.delete(shopUri);

		try {
			restTemplate.getForObject(shopUri, Shop.class);
			fail("Get should fail while feth a deleted shop");
		} catch (HttpClientErrorException e) {
			assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
		}
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void invalidInput() {

		//create
		Shop blankNameShop = new Shop();
		try {
			restTemplate.postForLocation(resoureUrl, blankNameShop);
			fail("Create should fail while title is blank");
		} catch (HttpClientErrorException e) {
			assertEquals(HttpStatus.BAD_REQUEST, e.getStatusCode());
			Map messages = jsonMapper.fromJson(e.getResponseBodyAsString(), Map.class);
			assertEquals(1, messages.size());
			assertEquals("不能为空", messages.get("name"));
		}

		//update
		blankNameShop.setId(1L);
		try {
			restTemplate.put(resoureUrl + "/1", blankNameShop);
			fail("Update should fail while title is blank");
		} catch (HttpClientErrorException e) {
			assertEquals(HttpStatus.BAD_REQUEST, e.getStatusCode());
			Map messages = jsonMapper.fromJson(e.getResponseBodyAsString(), Map.class);
			assertEquals(1, messages.size());
			assertEquals("不能为空", messages.get("name"));
		}
	}
}
