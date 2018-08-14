package icignal.batch.config;



import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(value="icignal.batch.b2c.repository", sqlSessionFactoryRef="sqlSessionFactoryB2C")
public class DataSourceConfigB2C extends DataSourceConfig {
	
	 @ConfigurationProperties(prefix = "datasource.b2c")
	 @Bean(name= "b2cDB")
	 public DataSource dataSource() {		 
	        return DataSourceBuilder.create().build();
	 }
	   
	
	/**
	 * SqlSessionTemplate 객채 생성 Bean 함수
	 * 
	 * @return SqlSessionTemplate
	 * @throws Exception
	 */
	@Bean(name = "sqlSessionTemplateB2C")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("b2cDB") DataSource dataSource) throws Exception {
    	return new SqlSessionTemplate(this.sqlSessionFactory(dataSource));
    }
    
	
	/**
	 * SqlSessionFactory 객채 생성 Bean 함수 Property 파일의 dbType에 따른 동적 DB 설정
	 * 
	 * @return SqlSessionFactory
	 * @throws Exception
	 */
	@Bean(name = "sqlSessionFactoryB2C")
	public SqlSessionFactory sqlSessionFactory(@Qualifier("b2cDB") DataSource dataSource) throws Exception {
		String dbType = env.getProperty("datasource.b2c.dbType").toLowerCase();
		return sqlSessionFactory(dataSource, dbType);
    }
	

	
	

}
