package com.mo.bean;

public class News {
	private String title;
	private String id;
	private String topicIcon;
	private String summary;
	private String published;
	private String updated;
	private String link;
	private int diggs;
	private int views;
	private int comments;
	private String sourceName;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getPublished() {
		return published;
	}
	public void setPublished(String published) {
		this.published = published;
	}
	public String getUpdated() {
		return updated;
	}
	public void setUpdated(String updated) {
		this.updated = updated;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public int getDiggs() {
		return diggs;
	}
	public void setDiggs(int diggs) {
		this.diggs = diggs;
	}
	public int getViews() {
		return views;
	}
	public void setViews(int views) {
		this.views = views;
	}
	public int getComments() {
		return comments;
	}
	public void setComments(int comments) {
		this.comments = comments;
	}
	public String getSourceName() {
		return sourceName;
	}
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTopicIcon() {
		return topicIcon;
	}
	public void setTopicIcon(String topicIcon) {
		this.topicIcon = topicIcon;
	}
	@Override
	public String toString() {
		return "News [title=" + title + ", id=" + id + ", topicIcon="
				+ topicIcon + ", summary=" + summary + ", published="
				+ published + ", updated=" + updated + ", link=" + link
				+ ", diggs=" + diggs + ", views=" + views + ", comments="
				+ comments + ", sourceName=" + sourceName + "]";
	}
	
	

}
