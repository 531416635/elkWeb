package com.zto.controller;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zto.common.ElkHelper;
import com.zto.model.Account;
import com.zto.model.Page;

@Controller
@RequestMapping("/accounts")
public class EController {

	@Autowired
	ElkHelper helper;
	private static final Logger log = LoggerFactory
			.getLogger(EController.class);

	@RequestMapping("/getAccounts")
	public String getAccount(Model model, Page page) {

		List<Account> accountList = new ArrayList<Account>();
		QueryBuilder queryBuilder = QueryBuilders.matchAllQuery();
		page.setStartIndex(0);
		page.setPageSize(1000);
		accountList = helper.getAccount(queryBuilder, page);
		model.addAttribute("accountList", accountList);
		return "accounts";
	}

	@RequestMapping("/delAccount")
	@ResponseBody
	public String delAccount(int id) {
		DeleteRequest reqBulk = new DeleteRequest();
		reqBulk.index("bank");
		reqBulk.type("accounts");
		reqBulk.id(id + "");

		String result = helper.delAccount(reqBulk);
		return result;
	}

	@RequestMapping("/sortAccounts")
	public String sortAccount(Page page, String name, String input) {
		// 处理排序种类
		SortOrder order;
		if (("ascend").equals(name)) {
			order = SortOrder.ASC;
		}
		if (("descend").equals(name)) {
			order = SortOrder.DESC;
		}
		// 处理字段
		String field;
		if (("账户编号").equals(input)) {
			field="account_number";
		}else if(("地址").equals(input)){
			field="address";
		}else if(("年龄").equals(input)){
			field="age";
		}else if(("薪水").equals(input)){
			field="balance";
		}else if(("城市").equals(input)){
			field="city";
		}else if(("email").equals(input)){
			field="email";
		}else if(("雇主").equals(input)){
			field="employer";
		}else if(("firstname").equals(input)){
			field="firstname";
		}else if(("性别").equals(input)){
			field="firstname";
		}else if(("lastname").equals(input)){
			field="gender";
		}else if(("state").equals(input)){
			field="state";
		}
		List<Account> accountList = new ArrayList<Account>();
		QueryBuilder queryBuilder = QueryBuilders.matchAllQuery();
		page.setStartIndex(0);
		page.setPageSize(1000);
		accountList = helper.sortAccounts(field, order);
		return "accounts";
	}
}
