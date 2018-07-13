package icignal.batch.member.step;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;



import icignal.batch.mrt.dao.MemberDao;



public class MemberListener extends JobExecutionListenerSupport {
	private static final Logger log = LoggerFactory.getLogger(MemberListener.class);
	private final MemberDao memberDao;

	public MemberListener(MemberDao memberDao) {
		this.memberDao = memberDao;
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			log.info("Finish Job! Check the results");

			int cnt = memberDao.countLoadMembers();
			log.info("member count::: " + cnt);

			/*for (Customer customer : customers) {
				log.info("Found <" + customer + "> in the database.");
			}*/
		}
	}
	
	@Override
	public void beforeJob(JobExecution jobExecution) {
		String tableName = "mrt.ch_mem_mst";
		log.info(jobExecution.getJobInstance().getJobName() + " is beginning execution.");
		//super.beforeJob(jobExecution);
		memberDao.truncate(tableName);
		log.info(tableName +" is truncated.");
	}
	
	
}
