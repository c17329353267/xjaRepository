package com.jd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jd.entity.ResultModel;
import com.jd.service.ProductsService;

@Controller
public class ProductsController {
	@Autowired
	private ProductsService service;

	@RequestMapping(value = "/list.action")
	public String index(String queryString, String catalog_name, String price, Integer page, String sort, Model model) {

		try {
			ResultModel resultModel = service.query(queryString, catalog_name, price, page, sort);
			model.addAttribute("result", resultModel);
			model.addAttribute("queryString", queryString);
			model.addAttribute("catalog_name", catalog_name);
			model.addAttribute("price", price);
			model.addAttribute("sort", sort);
			System.out.println(resultModel.getProductList());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "product_list";
	}

}
