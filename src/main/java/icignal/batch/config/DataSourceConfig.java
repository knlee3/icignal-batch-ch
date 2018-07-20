package icignal.batch.config;



import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.core.env.Environment;

import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import net.sf.log4jdbc.Log4jdbcProxyDataSource;
import net.sf.log4jdbc.tools.Log4JdbcCustomFormatter;
import net.sf.log4jdbc.tools.LoggingType;



public abstract class DataSourceConfig {
	
	@Autowired
	protected Environment env;
	

	 public abstract DataSource dataSource(); 
	   
	
		
    public abstract SqlSessionTemplate sqlSessionTemplate(DataSource dataSource) throws Exception;
 
	
	

	
	/**
	 * SqlSessionFactory 객채 생성 Bean 함수 Property 파일의 dbType에 따른 동적 DB 설정
	 * 
	 * @return SqlSessionFactory
	 * @throws Exception
	 */
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource, String dbType) throws Exception {
		
		
    	SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
    	String sqlMapConfigPath = "classpath:/conf/mybatis/" + dbType + "/*_SQL.xml";
    
    	
	//	PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
	//	Resource resources = resolver.getResource(sqlMapConfigPath);
		String dbLog = env.getProperty("jdbc.sql.log");
		
		if (dbLog != null && dbLog.toString().toLowerCase().equals("true")) {
			factory.setDataSource(this.datasourceSpied(dataSource));
		}else {
			factory.setDataSource(dataSource);
		}
		
		
		factory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(sqlMapConfigPath));
		
	//	factory.setConfigLocation(resources);		
//		factory.setMapperLocations(resolver.getResources("classpath:/conf/mybatis/" + dbType + "/sqlMap/**/*.xml")); 
	//	factory.setMapperLocations(resolver.getResources(sqlMapConfigPath)); 
	//	factory.afterPropertiesSet();
		return factory.getObject();
    }
	
	
	public Log4jdbcProxyDataSource datasourceSpied(DataSource dataSource) {
		Log4jdbcProxyDataSource ljpds = new Log4jdbcProxyDataSource(dataSource);
		Log4JdbcCustomFormatter ljcf = new Log4JdbcCustomFormatter();
		ljcf.setLoggingType(LoggingType.MULTI_LINE);
		ljcf.setSqlPrefix("SQL		: \n");
		ljpds.setLogFormatter(ljcf);

		return ljpds;
	}
	
	
	

}
