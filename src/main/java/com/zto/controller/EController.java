package com.zto.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.metrics.avg.Avg;
import org.elasticsearch.search.aggregations.metrics.max.Max;
import org.elasticsearch.search.aggregations.metrics.min.Min;
import org.elasticsearch.search.aggregations.metrics.stats.Stats;
import org.elasticsearch.search.sort.SortOrder;
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
@RequestMapping("/accounts")
public class EController {

	@Autowired
	ElkHelper helper;
	private static final Logger log = LoggerFactory
			.getLogger(EController.class);

	/**
	 * 获取账户信息
	 * 
	 * @param model
	 * @param page
	 * @return String
	 */
	@RequestMapping("/getAccounts")
	public String getAccount(Model model, Page page) {

		QueryBuilder queryBuilder = QueryBuilders.matchAllQuery();
		page.setStartIndex(0);
		page.setPageSize(1000);
		SearchResponse response = helper.getAccount(queryBuilder, page);
		// 取出返回结果中的文档
		SearchHits hits = response.getHits();
		List<Account> accountList = new ArrayList<Account>();
		for (SearchHit hit : hits) {
			Account account = JSON.parseObject(
					new String(hit.sourceAsString()), Account.class);
			accountList.add(account);
		}
		model.addAttribute("accountList", accountList);
		// 取出聚合函数中的最大值、最小值、平均值
		Map<String, Object> maxList = new HashMap<String, Object>();
		Map<String, Object> minList = new HashMap<String, Object>();
		Map<String, Object> avgList = new HashMap<String, Object>();
		Map<String, Object> sumList = new HashMap<String, Object>();
		Map<String, Object> countList = new HashMap<String, Object>();
		Aggregations aggsMax = response.getAggregations();
		List<Aggregation> listAgg = aggsMax.asList();
		for (int i = 0; i < listAgg.size(); i++) {
			String name = listAgg.get(i).getName();
			// log.info(name.substring(name.length() - 3, name.length()));
			if (("Max")
					.equals(name.substring(name.length() - 3, name.length()))) {
				Max max = (Max) listAgg.get(i);
				maxList.put(max.getName(), max.getValue());
			} else if (("Min").equals(name.substring(name.length() - 3,
					name.length()))) {
				Min min = (Min) listAgg.get(i);
				minList.put(min.getName(), min.getValue());
			} else if (("Avg").equals(name.substring(name.length() - 3,
					name.length()))) {
				Avg avg = (Avg) listAgg.get(i);
				avgList.put(avg.getName(), new java.text.DecimalFormat("#.00")
						.format(avg.getValue()));
			} else if (("Stats").equals(name.substring(name.length() - 5,
					name.length()))) {
				// stats比较厉害，什么都可以取出来----最小值、最大值、平均值、总和、数量
				Stats stats = (Stats) listAgg.get(i);
				sumList.put(name.substring(0, name.length() - 5) + "Sum",
						stats.getSum());
				countList.put(name.substring(0, name.length() - 5) + "Count",
						stats.getCount());
			}

		}
		model.addAttribute("maxList", maxList);
		model.addAttribute("minList", minList);
		model.addAttribute("avgList", avgList);
		model.addAttribute("sumList", sumList);
		log.info(sumList.toString());
		model.addAttribute("countList", countList);
		log.info(countList.toString());
		return "accounts";
	}

	/**
	 * 删除账户信息
	 * 
	 * @param id
	 * @return String
	 */
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

	/**
	 * 排序
	 * 
	 * @param page
	 * @param name
	 * @param input
	 * @return String
	 */
	@RequestMapping("/sortAccounts")
	@ResponseBody
	public String sortAccount(Page page, String name, String input) {
		// 处理排序种类
		SortOrder order = null;
		if (("ascend").equals(name)) {
			order = SortOrder.ASC;
		}
		if (("descend").equals(name)) {
			order = SortOrder.DESC;
		}
		// 处理字段
		String field = null;
		if (("账户编号").equals(input)) {
			field = "account_number";
		} else if (("地址").equals(input)) {
			field = "address";
		} else if (("年龄").equals(input)) {
			field = "age";
		} else if (("薪水").equals(input)) {
			field = "balance";
		} else if (("城市").equals(input)) {
			field = "city";
		} else if (("email").equals(input)) {
			field = "email";
		} else if (("雇主").equals(input)) {
			field = "employer";
		} else if (("firstname").equals(input)) {
			field = "firstname";
		} else if (("性别").equals(input)) {
			field = "gender";
		} else if (("级别").equals(input)) {
			field = "grade";
		} else if (("lastname").equals(input)) {
			field = "lastname";
		} else if (("state").equals(input)) {
			field = "state";
		}
		page.setStartIndex(0);
		page.setPageSize(1000);
		List<String> accountList = helper.sortAccounts(page, field, order);
		String result = null;
		result = JSONArray.fromObject(accountList).toString();
		return result;
	}
}
