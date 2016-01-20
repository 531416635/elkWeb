package com.zto.common;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.client.transport.TransportClient.Builder;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.zto.model.Account;
import com.zto.model.Page;

@Component("elkHelper")
public class ElkHelper implements InitializingBean {
	private static TransportClient client = null;
	private static Logger log = LoggerFactory.getLogger(ElkHelper.class);
	private static String[] indices = { "bank" };
	private static String[] types = { "accounts" };

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		Settings settings = Settings.settingsBuilder()
				.put("cluster.name", "yaoyuxiao-cluster").build();
		Builder builder = TransportClient.builder();
		Builder builders = builder.settings(settings);
		client = builders.build();
		client.addTransportAddress(
				new InetSocketTransportAddress(InetAddress
						.getByName("10.10.19.172"), 9300))
				.addTransportAddress(
						new InetSocketTransportAddress(InetAddress
								.getByName("10.10.19.172"), 9301))
				.addTransportAddress(
						new InetSocketTransportAddress(InetAddress
								.getByName("10.10.19.172"), 9302));

	}

	// 获取账户列表
	public List<Account> getAccount(QueryBuilder queryBuilder, Page page) {

		SearchResponse response = client.prepareSearch(indices)
				.setQuery(queryBuilder).setFrom(page.getStartIndex())
				.setSize(page.getPageSize()).execute().actionGet();
		SearchHits hits = response.getHits();
		List<Account> accountList = new ArrayList<Account>();
		for (SearchHit hit : hits) {
			Account account = JSON.parseObject(
					new String(hit.sourceAsString()), Account.class);
			accountList.add(account);

		}
		return accountList;
	}

	// 删除账户
	public String delAccount(DeleteRequest request) {
		BulkResponse BulkResponse = client.prepareBulk().add(request).get();
		BulkItemResponse[] string = BulkResponse.getItems();
		for (BulkItemResponse r : string) {
			if (r.isFailed()) {
				return "false";
			}
		}
		return "true";
	}

	// 排序
	public List<String> sortAccounts(Page page, String field, SortOrder order) {
		List<String> result = new ArrayList<String>();
		SearchResponse response = client.prepareSearch().addSort(field, order)
				.setFrom(page.getStartIndex()).setSize(page.getPageSize())
				.get();
		SearchHits hits = response.getHits();
		for (SearchHit hit : hits) {
			result.add(hit.sourceAsString());
		}
		
		return result;
	}
}
