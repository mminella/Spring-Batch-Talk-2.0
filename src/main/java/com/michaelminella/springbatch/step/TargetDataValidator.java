package com.michaelminella.springbatch.step;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.batch.core.ItemWriteListener;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;

public class TargetDataValidator implements ItemWriteListener {

	private DataSource dataSource;
	private JdbcOperations template;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.template = new JdbcTemplate(dataSource);
	}

	@Override
	public void afterWrite(List arg0) {
		int targets = template.queryForInt("select count(*) from target");

		System.out.println("```````````````````````````````````````````````````````");
		System.out.println("```````````````````````````````````````````````````````");
		System.out.println("```````````````````````````````````````````````````````");
		System.out.println(" there are " + targets + " in the database right now");
		System.out.println("```````````````````````````````````````````````````````");
		System.out.println("```````````````````````````````````````````````````````");
		System.out.println("```````````````````````````````````````````````````````");
	}

	@Override
	public void beforeWrite(List arg0) {
		System.out.println("||||||||||||||||||||||||||||||||||||||||||||||||||||||");
		System.out.println("||||||||||||||||||||||||||||||||||||||||||||||||||||||");
		System.out.println("||||||||||||||||||||||||||||||||||||||||||||||||||||||");
		System.out.println("about to write: " + arg0);
		System.out.println("||||||||||||||||||||||||||||||||||||||||||||||||||||||");
		System.out.println("||||||||||||||||||||||||||||||||||||||||||||||||||||||");
		System.out.println("||||||||||||||||||||||||||||||||||||||||||||||||||||||");
	}

	@Override
	public void onWriteError(Exception arg0, List arg1) {
		int targets = template.queryForInt("select count(*) from target");

		System.out.println("******************************************************");
		System.out.println("******************************************************");
		System.out.println("******************************************************");
		System.out.println(" there are " + targets + " in the database right now");
		System.out.println(" and the exception was: " + arg0);
		System.out.println("******************************************************");
		System.out.println("******************************************************");
		System.out.println("******************************************************");
	}
}
