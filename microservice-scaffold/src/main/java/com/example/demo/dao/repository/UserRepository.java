package com.example.demo.dao.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.demo.dao.entity.User;

public interface UserRepository extends PagingAndSortingRepository<User, Integer>{

}
