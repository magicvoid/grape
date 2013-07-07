package com.sintn.grape.server.service.shop;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.sintn.grape.server.entity.Shop;
import com.sintn.grape.server.repository.ShopDao;

/**
 * 用户管理类.
 * 
 * @author calvin
 */
// Spring Service Bean的标识.
@Component
@Transactional(readOnly = true)
public class ShopService {

//	private static Logger logger = LoggerFactory.getLogger(ShopService.class);
	
	private ShopDao shopDao;

	public List<Shop> getAllShop() {
		return (List<Shop>) shopDao.findAll();
	}

	public Shop getShop(Long id) {
		return shopDao.findOne(id);
	}

	@Transactional(readOnly = false)
	public void saveShop(Shop shop) {
		shop.setJoin_date(Calendar.getInstance().getTime());
		shopDao.save(shop);
	}

	@Transactional(readOnly = false)
	public void updateShop(Shop shop) {
		shopDao.save(shop);
	}

	@Transactional(readOnly = false)
	public void deleteShop(Long id) {
		shopDao.delete(id);

	}
	
	public Page<Shop> getUserShop(Long userId, Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<Shop> spec = buildSpecification(userId, searchParams);

		return shopDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建分页请求.
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "id");
		} else if ("join_date".equals(sortType)) {
			sort = new Sort(Direction.DESC, "join_date");
		}

		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

	/**
	 * 创建动态查询条件组合.
	 */
	private Specification<Shop> buildSpecification(Long userId, Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		filters.put("user.id", new SearchFilter("user.id", Operator.EQ, userId));
		Specification<Shop> spec = DynamicSpecifications.bySearchFilter(filters.values(), Shop.class);
		return spec;
	}
	
	
	@Autowired
	public void setShopDao(ShopDao shopDao) {
		this.shopDao = shopDao;
	}
}
