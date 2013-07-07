package com.sintn.grape.server.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sintn.grape.server.entity.Shop;

public interface ShopDao extends PagingAndSortingRepository<Shop, Long>, JpaSpecificationExecutor<Shop> {

//	Page<Shop> findByShopId(Long id, Pageable pageRequest);

	@Modifying
	@Query("delete from Shop shop where shop.id=?1")
	void deleteByShopId(Long id);
}
