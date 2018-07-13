package icignal.batch.config;

import java.util.Optional;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import net.sf.log4jdbc.Log4jdbcProxyDataSource;
import net.sf.log4jdbc.tools.Log4JdbcCustomFormatter;
import net.sf.log4jdbc.tools.LoggingType;



public abstract class DataSourceConfig {
	
	@Autowired
	private Environment env;
	

	 public abstract DataSource dataSource(); 
	   
	
	
	@ConfigurationProperties(prefix="datasource.icignal")
	@Primary
	@Bean(name = "icignalDB")
	public DataSource destinationDataSource(){
	  return DataSourceBuilder.create().build();
	}
	
	
	
	/**
	 * SqlSessionTemplate 객채 생성 Bean 함수
	 * 
	 * @return SqlSessionTemplate
	 * @throws Exception
	 */
	@Bean(name = "sqlSessionTemplateB2C")
    public SqlSessionTemplate sqlSessionTemplate(DataSource dataSource) throws Exception {
    	return new SqlSessionTemplate(this.sqlSessionFactory(dataSource , dbType));
    }
    
	
	

	/**
	 * SqlSessionTemplate 객채 생성 Bean 함수
	 * 
	 * @return SqlSessionTemplate
	 * @throws Exception
	 */
	@Bean(name = "sqlSessionTemplateIC")
    public SqlSessionTemplate sqlSessionTemplate(DataSource dataSource) throws Exception {
    	return new SqlSessionTemplate(this.sqlSessionFactoryIC(dataSource));
    }
	
	
	/**
	 * SqlSessionFactory 객채 생성 Bean 함수 Property 파일의 dbType에 따른 동적 DB 설정
	 * 
	 * @return SqlSessionFactory
	 * @throws Exception
	 */
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource, String dbType) throws Exception {
		
		
    	SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
    //	String dbType = env.getProperty("datasource.iciganl.dbType").toLowerCase();
    	String sqlMapConfigPath = "classpath:/conf/mybatis/" + dbType + "/SqlMapConfig.xml";
    	
    	
    	//String dbType = ICNCommon.getInstance().getServletProp("jdbc.dbType").toString().toLowerCase();
		//String sqlMapConfigPath = "classpath:/conf/mybatis/" + dbType + "/SqlMapConfig.xml";
    	
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		Resource resources = resolver.getResource(sqlMapConfigPath);
		String dbLog = env.getProperty("jdbc.sql.log");
		
		if (dbLog != null && dbLog.toString().toLowerCase().equals("true")) {
			factory.setDataSource(this.datasourceSpied(dataSource));
		}else {
			factory.setDataSource(dataSource);
		}
		
		
		factory.setConfigLocation(resources);		
		factory.setMapperLocations(resolver.getResources("classpath:/conf/mybatis/" + dbType + "/sqlMap/**/*.xml")); 
		factory.afterPropertiesSet();
		return factory.getObject();
    }
	
	
	
	/**
	 * SqlSessionFactory 객채 생성 Bean 함수 Property 파일의 dbType에 따른 동적 DB 설정
	 * 
	 * @return SqlSessionFactory
	 * @throws Exception
	 */
	@Bean(name = "sqlSessionFactoryIC")
	public SqlSessionFactory sqlSessionFactoryIC(DataSource dataSource) throws Exception {
		
		
    	SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
    	String dbType = env.getProperty("datasource.iciganl.dbType").toLowerCase();
    	String sqlMapConfigPath = "classpath:/conf/mybatis/" + dbType + "/SqlMapConfig.xml";
    	
    	
    	//String dbType = ICNCommon.getInstance().getServletProp("jdbc.dbType").toString().toLowerCase();
		//String sqlMapConfigPath = "classpath:/conf/mybatis/" + dbType + "/SqlMapConfig.xml";
    	
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		Resource resources = resolver.getResource(sqlMapConfigPath);
		String dbLog = env.getProperty("jdbc.sql.log");
		
		if (dbLog != null && dbLog.toString().toLowerCase().equals("true")) {
			factory.setDataSource(this.datasourceSpied(dataSource));
		}else {
			factory.setDataSource(dataSource);
		}
		
		
		factory.setConfigLocation(resources);		
		factory.setMapperLocations(resolver.getResources("classpath:/conf/mybatis/" + dbType + "/sqlMap/**/*.xml")); 
		factory.afterPropertiesSet();
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
