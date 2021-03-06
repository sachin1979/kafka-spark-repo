package com.ibm.insite.kafkaetlprocessor.dataobject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "resultstats")
public class ResultStats {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Integer id;

	@Column(name = "productbrand")
	private String productbrand;

	@Column(name = "productname")
	private String productname;

	@Column(name = "numberoforders")
	private Integer numberoforders;

	public ResultStats(String productbrand, String productname, Integer numberoforders) {
		super();
		this.productbrand = productbrand;
		this.productname = productname;
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

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public ResultStats() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "ResultStats [productbrand=" + productbrand + ", numberoforders=" + numberoforders + "]";
	}
}
