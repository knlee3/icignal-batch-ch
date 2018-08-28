package icignal.batch.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import icignal.batch.config.properties.QuartzProperties;

@Component
@EnableConfigurationProperties(QuartzProperties.class)
public class BatchNameProvider {

	
    @Autowired
    private QuartzProperties quartzProperties;

}
