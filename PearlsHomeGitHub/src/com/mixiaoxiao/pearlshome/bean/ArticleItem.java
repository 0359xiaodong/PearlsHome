package com.mixiaoxiao.pearlshome.bean;

import net.tsz.afinal.annotation.sqlite.Id;

public class ArticleItem {
	
	@Id
	private String href;
	private String title;
	private String section;
	private String footer;
	private boolean top;
	
	public ArticleItem() {
		super();
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getFooter() {
		return footer;
	}

	public void setFooter(String footer) {
		this.footer = footer;
	}

	public boolean isTop() {
		return top;
	}

	public void setTop(boolean top) {
		this.top = top;
	}

	
}
