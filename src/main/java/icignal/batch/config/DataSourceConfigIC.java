package icignal.batch.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;



@Configuration
@MapperScan(value="icignal.batch.icg.repository", sqlSessionFactoryRef="sqlSessionFactoryIC")
public class DataSourceConfigIC extends DataSourceConfig {
	
	@Autowired
	protected Environment env;
	
	@ConfigurationProperties(prefix="datasource.icignal")
	@Primary
	@Bean(name = "icignalDB")
	public DataSource dataSource(){
	  return DataSourceBuilder.create().build();
	}
	

	/**
	 * SqlSessionTemplate 객채 생성 Bean 함수
	 * 
	 * @return SqlSessionTemplate
	 * @throws Exception
	 */
	@Bean(name = "sqlSessionTemplateIC")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("icignalDB") DataSource dataSource) throws Exception {
		
		SqlSessionTemplate temp = new SqlSessionTemplate(this.sqlSessionFactory(dataSource), ExecutorType.BATCH );
		
		
    //	return new SqlSessionTemplate(this.sqlSessionFactory(dataSource));
		return temp;
    }
	
	
	/**
	 * SqlSessionFactory 객채 생성 Bean 함수 Property 파일의 dbType에 따른 동적 DB 설정
	 * 
	 * @return SqlSessionFactory
	 * @throws Exception
	 */
	@Bean(name = "sqlSessionFactoryIC")
	public SqlSessionFactory sqlSessionFactory(@Qualifier("icignalDB") DataSource dataSource) throws Exception {
    	return sqlSessionFactory(dataSource, env.getProperty("datasource.iciganl.dbType").toLowerCase());    	
    }
	
	/**
	 * SqlSessionFactory 객채 생성 Bean 함수 Property 파일의 dbType에 따른 동적 DB 설정
	 * sqlSessionFactoryIC 하나로만 사용하면 ItemWriter에서 "spring cannot change the executortype when there is an existing transaction"
	 * 에러가 발생하여, sqlSessionFactory를 별로도 나눔.
	 * @return SqlSessionFactory
	 * @throws Exception
	 */
	@Bean(name = "sqlSessionFactoryWriter")
	public SqlSessionFactory sqlSessionFactoryWriter(@Qualifier("icignalDB") DataSource dataSource) throws Exception {
    	return sqlSessionFactory(dataSource, env.getProperty("datasource.iciganl.dbType").toLowerCase());
    }
	
	
	
	

}
