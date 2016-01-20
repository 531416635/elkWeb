package com.zto.test;

import java.util.List;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zto.common.ElkHelper;
import com.zto.model.Account;
import com.zto.model.Page;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring-eshelpertest.xml")
public class EsTest {
	@Autowired
	ElkHelper helper;

	@Test
	public void esHelperTest() {

		QueryBuilder queryBuilder = QueryBuilders.matchAllQuery();
		Page page = new Page();
		page.setStartIndex(0);
		page.setPageSize(20);
		List<Account> litAccounts = helper.getAccount(queryBuilder, page);
		System.out.println(litAccounts);
	}
}
