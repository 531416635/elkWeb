package com.zto.controller;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
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
	public String getAccount(Model model) {

		List<Account> accountList = new ArrayList<Account>();
		QueryBuilder queryBuilder = QueryBuilders.matchAllQuery();
		Page page = new Page();
		page.setStartIndex(0);
		page.setPageSize(20);
		accountList = helper.getAccount(queryBuilder, page);
		model.addAttribute("accountList", accountList);
		return "accounts";
	}

	@RequestMapping("/delAccount")
	@ResponseBody
	public String delAccount(Model model, int id) {
		log.info(id + "");
		IndexRequest reqBulk = new IndexRequest();
		reqBulk.index("bank");
		reqBulk.type("accounts");
		reqBulk.id(id + "");

		BulkResponse accountList = helper.delAccount(reqBulk);

		return "accounts";
	}
}
