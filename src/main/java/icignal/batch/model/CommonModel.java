package icignal.batch.model;

import org.apache.commons.lang.builder.ToStringBuilder;

public class CommonModel {

	public  String toString(){
	    return ToStringBuilder.reflectionToString(this);
	    /*
	    return ToStringBuilder.reflectionToString(this
	        , ToStringStyle.SHORT_PREFIX_STYLE);
	    return ToStringBuilder.reflectionToString(this
	        , ToStringStyle.MULTI_LINE_STYLE);
	    return new ToStringBuilder(this)
	        .append("processor", processor).toString();
	     */
	}
}
