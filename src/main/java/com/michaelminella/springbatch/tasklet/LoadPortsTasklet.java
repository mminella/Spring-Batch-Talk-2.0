package com.michaelminella.springbatch.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.michaelminella.springbatch.domain.Target;

public class LoadPortsTasklet extends HibernateTemplate implements Tasklet {

	private String ipAddress;
	private boolean fullPortScan;

	@Override
	public RepeatStatus execute(StepContribution arg0, ChunkContext arg1)
			throws Exception {

		if(fullPortScan) {
			for(int i = 1; i < 10000; i++) {
				Target target = new Target();
				target.setIp(ipAddress);
				target.setPort(i);
				save(target);
			}
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