package icignal.batch.Listener;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;

import icignal.batch.icg.repository.ICGMapper;



public class MemberListener extends JobExecutionListenerSupport {
	private static final Logger log = LoggerFactory.getLogger(MemberListener.class);
/*	private final MemberDao memberDao;

	public MemberListener(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
*/
/*	
	public MemberListener(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
	*/
	//@Resource
	private final  ICGMapper mapper;
	
	public MemberListener(ICGMapper mapper) {
		this.mapper = mapper;
	}
	
	
	@Override
	public void beforeJob(JobExecution jobExecution) {
		// String tableName = "mrt.ch_mem_stg";
//		 System.out.println("jobExecution: " + jobExecution);
//		 System.out.println("jobExecution.getJobInstance(): " + jobExecution.getJobInstance());
//		 System.out.println("jobExecution.getJobInstance(): " + jobExecution.getJobInstance().getJobName());
		 
		log.info(jobExecution.getJobInstance().getJobName() + " is beginning execution.");
		
		List<String> tables = Arrays.asList("mrt.ch_mem_stg", "mrt.ch_mem_other_agree_stg", "mrt.ch_mobile_app_info_stg");
		for(String table : tables) {
			mapper.truncateTable(table);
			log.info(table + " is truncated.");
		}
		
		
		
	}
	
	
	
	
	@Override
	public void afterJob(JobExecution jobExecution) {
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			log.info("Finish Job! Check the results");
		    

//			int cnt = memberDao.countLoadMembers();
			log.info("ch_mem_stg table insert count::: " + mapper.cntChMemStg());

			/*for (Customer customer : customers) {
				log.info("Found <" + customer + "> in the database.");
			}*/
		}
	}
	
	
	
}
