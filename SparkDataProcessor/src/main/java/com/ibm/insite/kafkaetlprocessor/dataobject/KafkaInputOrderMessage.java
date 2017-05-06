package com.ibm.insite.kafkaetlprocessor.dataobject;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("serial")
@JsonIgnoreProperties(ignoreUnknown = true)
public class KafkaInputOrderMessage implements Serializable {

	@JsonProperty(value = "productname")
	private String productname;

	@JsonProperty(value = "productbrand")
	private String productbrand;

	@JsonProperty(value = "customerfullname")
	private String customerfullname;

	@JsonProperty(value = "customerzip")
	private int customerzip;

	public KafkaInputOrderMessage() {
		
	}
	
	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public String getProductbrand() {
		return productbrand;
	}

	public void setProductbrand(String productbrand) {
		this.productbrand = productbrand;
	}

	public String getCustomerfullname() {
		return customerfullname;
	}

	public void setCustomerfullname(String customerfullname) {
		this.customerfullname = customerfullname;
	}

	public Integer getCustomerzip() {
		return customerzip;
	}

	public void setCustomerzip(Integer customerzip) {
		this.customerzip = customerzip;
	}

	@Override
	public String toString() {
		return "KafkaInputOrderMessage [productname=" + productname + ", productbrand=" + productbrand
				+ ", customerfullname=" + customerfullname + ", customerzip=" + customerzip + "]";
	}

}
