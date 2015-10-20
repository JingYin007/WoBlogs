package com.mo.bean;

import java.io.Serializable;

public class Blogs implements Serializable {
	private String title;
	private String id;
	private String summary;
	private String published;
	private String updated;
	private String link;
	private int diggs;
	private int views;
	private int comments;
	private String avatar;
	private String sourceName;
	private String authorName;
	private String authorUri;
	private String authorAvater;
	private String content;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getSourceName() {
		return sourceName;
	}
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public String getAuthorUri() {
		return authorUri;
	}
	public void setAuthorUri(String authorUri) {
		this.authorUri = authorUri;
	}
	public String getAuthorAvater() {
		return authorAvater;
	}
	public void setAuthorAvater(String authorAvater) {
		this.authorAvater = authorAvater;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "Blogs [title=" + title + ", id=" + id + ", summary=" + summary
				+ ", published=" + published + ", updated=" + updated
				+ ", link=" + link + ", diggs=" + diggs + ", views=" + views
				+ ", comments=" + comments + ", avatar=" + avatar
				+ ", sourceName=" + sourceName + ", authorName=" + authorName
				+ ", authorUri=" + authorUri + ", authorAvater=" + authorAvater
				+ ", content=" + content + "]";
	}
	
}
