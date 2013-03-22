package com.michaelminella.springbatch.tasklet;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;

public class LoadPortsTasklet implements Tasklet {

	protected static final Log logger = LogFactory.getLog(LoadPortsTasklet.class);
	private static final String INSERT_TARGETS = "INSERT INTO TARGET (ID, IP, PORT, CONNECTED, BANNER) VALUES (?, ?, ?, NULL, NULL)";
	private static final String START_ID = "SELECT MAX(ID) FROM TARGET";
	private int numberOfPorts;
	private String ipAddress;
	private JdbcOperations template;

	@Override
	public RepeatStatus execute(StepContribution arg0, ChunkContext arg1)
			throws Exception {

		logger.debug("*********************************************");
		logger.debug("About to load targets for ip " + ipAddress);
		logger.debug("*********************************************");

		List<Object []> targets = new ArrayList<Object []>();

		long curMaxId = template.queryForLong(START_ID);

		for(int i = 1; i < numberOfPorts; i++) {
			Object [] params = new Object[3];
			params[0] = curMaxId + i;
			params[1] = ipAddress;
			params[2] = i;

			targets.add(params);
		}

		template.batchUpdate(INSERT_TARGETS, targets);

		return RepeatStatus.FINISHED;
	}

	public void setIpAddress(String address) {
		ipAddress = address;
	}

	public void setDataSource(DataSource dataSource) {
		this.template = new JdbcTemplate(dataSource);
	}

	public void setNumberOfPorts(int ports) {
		this.numberOfPorts = ports;
	}
}