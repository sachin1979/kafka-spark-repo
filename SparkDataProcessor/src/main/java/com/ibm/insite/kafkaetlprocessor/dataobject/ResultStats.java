package com.ibm.insite.kafkaetlprocessor.dataobject;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ResultStats implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String productbrand;
	private Integer numberoforders;

	public ResultStats(String productbrand, Integer numberoforders) {
		super();
		this.productbrand = productbrand;
		this.numberoforders = numberoforders;
	}

	public String getProductbrand() {
		return productbrand;
	}

	public void setProductbrand(String productbrand) {
		this.productbrand = productbrand;
	}

	public Integer getNumberoforders() {
		return numberoforders;
	}

	public void setNumberoforders(Integer numberoforders) {
		this.numberoforders = numberoforders;
	}

	public ResultStats() {
	}

	@Override
	public String toString() {
		return "ResultStats [productbrand=" + productbrand + ", numberoforders=" + numberoforders + "]";
	}

}
