package com.michaelminella.springbatch.tasklet;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.michaelminella.springbatch.domain.Target;

public class LoadPortsTasklet extends HibernateTemplate implements Tasklet {

	private static final int FULL_PORT_SCAN = 65536;
	private static final int NUMBER_OF_PORTS = 2048;
	private String ipAddress;
	private boolean fullPortScan;

	@Override
	public RepeatStatus execute(StepContribution arg0, ChunkContext arg1)
			throws Exception {

		System.out.println("**** BUILDING TARGETS FOR " + ipAddress + " ****");

		List<Target> targets = new ArrayList<Target>();

		if(fullPortScan) {
			for(int i = 1; i < NUMBER_OF_PORTS; i++) {
				Target target = new Target();
				target.setIp(ipAddress);
				target.setPort(i);
				targets.add(target);
			}

			saveOrUpdateAll(targets);
		}

		return RepeatStatus.FINISHED;
	}

	public void setIpAddress(String address) {
		ipAddress = address;
	}

	public void setFullPortScan(boolean scan) {
		fullPortScan = scan;
	}
}