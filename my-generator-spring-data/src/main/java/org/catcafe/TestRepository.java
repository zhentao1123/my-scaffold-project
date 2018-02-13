package org.catcafe;

import org.springframework.data.repository.PagingAndSortingRepository;

import cn.xglory.tmc.dao.entity.Test;

public interface TestRepository extends PagingAndSortingRepository<Test, Long>{

}
