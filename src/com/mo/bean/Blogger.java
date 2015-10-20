package com.mo.bean;

import java.io.Serializable;

public class Blogger implements Serializable {

	private String id;
	private String title;
	private String updated;
	private String blogapp;
	private String avatar;
	private String postcount;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUpdated() {
		return updated;
	}
	public void setUpdated(String updated) {
		this.updated = updated;
	}
	public String getBlogapp() {
		return blogapp;
	}
	public void setBlogapp(String blogapp) {
		this.blogapp = blogapp;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getPostcount() {
		return postcount;
	}
	public void setPostcount(String postcount) {
		this.postcount = postcount;
	}
	@Override
	public String toString() {
		return "Blogger [id=" + id + ", title=" + title + ", updated="
				+ updated + ", blogapp=" + blogapp + ", avatar=" + avatar
				+ ", postcount=" + postcount + "]";
	}
	
}
