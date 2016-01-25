package com.zto.test;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.filter.Filter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.Model;

import com.alibaba.fastjson.JSON;
import com.zto.common.ElkHelper;
import com.zto.controller.EController;
import com.zto.model.Account;
import com.zto.model.Page;
import com.zto.service.AccountService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring-mybatis.xml")
public class EsTest {
	@Autowired
	ElkHelper helper;

	@Autowired
	AccountService accountService;

	private static final Logger log = LoggerFactory.getLogger(EsTest.class);

	/**
	 * 把数据转存到MySQL数据库中，有用
	 */
	@Test
	public void esHelperTest() {

		QueryBuilder queryBuilder = QueryBuilders.matchAllQuery();
		Page page = new Page();
		page.setStartIndex(0);
		page.setPageSize(1000);
		SearchResponse response = helper.getAccount(queryBuilder, page);
		// 取出返回结果中的文档
		SearchHits hits = response.getHits();
		for (SearchHit hit : hits) {
			Account account = JSON.parseObject(
					new String(hit.sourceAsString()), Account.class);
			accountService.insert(account);
		}
	}

	/*
	 * @Test public void filter() { Page page = new Page();
	 * page.setStartIndex(0); page.setPageSize(10); String filterName =
	 * "filterName"; String fieldName = "account_number"; String fieldValue =
	 * "183"; String type = "fuzzy"; helper.filter(page, filterName, fieldName,
	 * fieldValue, type); }
	 */
	@Test
	public void filterAccount() {
		Page page = new Page();
		String field = "account_number";
		String type = "fuzzy";
		String inputext = "543";
		page.setStartIndex(0);
		page.setPageSize(50);
		SearchResponse response = helper.filter(page, "filterName", field,
				inputext, type);
		// 取出返回结果中的文档
		Filter aggs = response.getAggregations().get("filterName");
		log.info(aggs + "");
		long name = aggs.getDocCount();
		log.info(name + "");
		// String result = JSONArray.fromObject(accountList).toString();
	}
}
