package com.jd.service;

import com.jd.entity.ResultModel;

public interface ProductsService {
	 ResultModel query(String queryString, String catalog_name, String price, Integer page, String sort) throws Exception;
}
