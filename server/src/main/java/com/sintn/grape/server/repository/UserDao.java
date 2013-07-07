package com.sintn.grape.server.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.sintn.grape.server.entity.User;

public interface UserDao extends PagingAndSortingRepository<User, Long> {
	User findByLoginName(String loginName);
}
