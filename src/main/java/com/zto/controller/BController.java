package com.zto.controller;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.metrics.max.Max;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.zto.common.ElkHelper;
import com.zto.model.Account;
import com.zto.model.Page;

@Controller
@RequestMapping("/bucket")
public class BController {
	@Autowired
	ElkHelper helper;
	private static final Logger log = LoggerFactory
			.getLogger(EController.class);

	@RequestMapping("/getAccounts")
	public String getAccount(Model model, Page page, String name) {
		page.setStartIndex(0);
		page.setPageSize(50);
		SearchResponse response = helper.getBucket(page);
		// 取出返回结果中的文档
		SearchHits hits = response.getHits();
		List<Account> accountList = new ArrayList<Account>();
		for (SearchHit hit : hits) {
			Account account = JSON.parseObject(
					new String(hit.sourceAsString()), Account.class);
			accountList.add(account);
		}
		model.addAttribute("accountList", accountList);
		// AggregationBuilder filterBuilder=AggregationBuilders.filter("");
		return "bucket";
	}

	@RequestMapping("/filterAccount")
	@ResponseBody
	public String filterAccount(Model model, Page page, String field,
			String type, String inputext) {
		page.setStartIndex(0);
		page.setPageSize(50);
		SearchResponse response = helper.filter(page, "filterName", field,
				inputext, type);
		// 取出返回结果中的文档
		Aggregations aggs = response.getAggregations().get("agg");
		List<Account> accountList = new ArrayList<Account>();
		List<Aggregation> listAgg = aggs.asList();
		for (int i = 0; i < listAgg.size(); i++) {
			String name = listAgg.get(i).getName();
			
			} 
		/*for (SearchHit hit : hits) {
			Account account = JSON.parseObject(
					new String(hit.sourceAsString()), Account.class);
			accountList.add(account);
		}*/
		String result = JSONArray.fromObject(accountList).toString();
		return result;
	}
}
