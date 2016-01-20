package com.zto.controller;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zto.common.ElkHelper;
import com.zto.model.Account;
import com.zto.model.Page;

@Controller
@RequestMapping("/accounts")
public class EController {

	@Autowired
	ElkHelper helper;

	@RequestMapping("/getAccounts")
	public String getAccount(Model model) {
	/*	ElkHelper helper=new ElkHelper();
		try {
			helper.afterPropertiesSet();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		List<Account> accountList = new ArrayList<Account>();
		QueryBuilder queryBuilder = QueryBuilders.matchAllQuery();
		Page page = new Page();
		page.setStartIndex(0);
		page.setPageSize(20);
		accountList = helper.getAccount(queryBuilder, page);
		model.addAttribute("accountList", accountList);
		return "accounts";
	}
}
