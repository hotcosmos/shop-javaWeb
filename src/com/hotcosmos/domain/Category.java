package com.hotcosmos.domain;

/**
 * 商品分类表
 * @author Administrator
 * @date 2018年5月16日
 */
public class Category {
	// `cid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	// `cname` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
	private String cid;
	private String cname;

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

}
