package com.ibm.insite.kafkasender.dataobject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class KafkaInputOrderMessage {

	@JsonProperty(value = "productname")
	private String productname;

	@JsonProperty(value = "productbrand")
	private String productbrand;

	@JsonProperty(value = "customerfullname")
	private String customerfullname;

	@JsonProperty(value = "customerzip")
	private Integer customerzip;

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
}
