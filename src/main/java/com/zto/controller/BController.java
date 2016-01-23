package com.zto.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONArray;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.filter.Filter;
import org.elasticsearch.search.aggregations.bucket.filters.Filters;
import org.elasticsearch.search.aggregations.bucket.missing.Missing;
import org.elasticsearch.search.aggregations.bucket.nested.Nested;
import org.elasticsearch.search.aggregations.metrics.max.Max;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zto.common.ElkHelper;
import com.zto.model.Account;
import com.zto.model.FiltersAttr;
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
		page.setPageSize(1000);
		SearchResponse response = helper.filter(page, "filterName", field,
				inputext, type);
		// 取出返回结果中的文档
		Filter aggs = response.getAggregations().get("filterName");
		long name = aggs.getDocCount();
		return name + "";
	}

	@RequestMapping("/filtersAccount")
	@ResponseBody
	public String filtersAccount(Page page, String list) {
		String[] str = list.split("=");
		List<FiltersAttr> filterList = new ArrayList<FiltersAttr>();
		for (int i = 0; i < str.length; i++) {
			log.info(str[i]);
			FiltersAttr filtersAttr = JSON.parseObject(new String(str[i]),
					FiltersAttr.class);
			filterList.add(filtersAttr);
		}
		page.setStartIndex(0);
		page.setPageSize(1000);
		SearchResponse response = helper.filters(page, filterList);
		// 取出返回结果中的文档
		Filters aggs = response.getAggregations().get("flitersname");
		String result = "{";
		for (Filters.Bucket entry : aggs.getBuckets()) {
			String key = entry.getKeyAsString(); // bucket key
			long docCount = entry.getDocCount(); // Doc count
			log.info("key [{}], doc_count [{}]", key, docCount);
			result += "\"" + key + "\":\"" + docCount + "\",";

		}
		result = result.subSequence(0, result.length() - 1) + "}";
		return result;
	}

	@RequestMapping("/missingAccount")
	@ResponseBody
	public String missingAccount(Page page, String str) {
		String result = "";
		page.setStartIndex(0);
		page.setPageSize(1000);
		SearchResponse response = helper.missing(page, str);
		Missing agg = response.getAggregations().get("missing");
		result = agg.getDocCount() + "";
		return result;

	}

	@RequestMapping("/nestedAccount")
	@ResponseBody
	public String nestedAccount(Page page) {
		String result = "";
		page.setStartIndex(0);
		page.setPageSize(1000);
		SearchResponse response = helper.nestedAggs(page);
		Nested agg = response.getAggregations().get("agg");
		result = agg.getDocCount() + "";
		return result;

	}
}
