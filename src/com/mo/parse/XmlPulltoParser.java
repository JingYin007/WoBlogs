package com.mo.parse;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;
import android.util.Xml;

import com.mo.bean.Blogger;
import com.mo.bean.Blogs;
import com.mo.bean.BlogsContent;
import com.mo.bean.Comment;
import com.mo.bean.News;
import com.mo.bean.NewsContent;

public class XmlPulltoParser {
	
	
/**
 * 热点新闻的解析方法	
 * @param inputStream
 * @return
 */
public static ArrayList<News> ParseHotNews(InputStream inputStream){
		
		ArrayList<News> hotNewsList = null;
		News news = null;
		
		boolean isEntry=false;
		XmlPullParser parser=Xml.newPullParser();
		try {
			parser.setInput(inputStream, "UTF-8");
			int eventType=parser.getEventType();
			
			while(eventType!=XmlPullParser.END_DOCUMENT){
			
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
					hotNewsList=new ArrayList<News>();
					break;
				case XmlPullParser.START_TAG:
					if(parser.getName().equals("entry")){  //一条新闻开始
						isEntry=true;
						news=new News();
					}else if(parser.getName().equals("id") && isEntry){
						news.setId(parser.nextText());	
					//	news.setLink();
					}else if(parser.getName().equals("title") && isEntry){
						news.setTitle(parser.nextText());	
					}else if(parser.getName().equals("summary") && isEntry){
						news.setSummary(parser.nextText());			
					}else if(parser.getName().equals("published") && isEntry){
						String[] t=parser.nextText().split("T");
						news.setPublished(t[0]+t[1]);
					}else if(parser.getName().equals("views") && isEntry){
						news.setViews(Integer.parseInt(parser.nextText()));
					}else if(parser.getName().equals("comments") && isEntry){
						news.setComments(Integer.parseInt(parser.nextText()));
					}else if(parser.getName().equals("diggs") && isEntry){
						news.setDiggs(Integer.parseInt(parser.nextText()));
					}else if(parser.getName().equals("sourceName") && isEntry){
						String sourceName=parser.nextText();
						news.setSourceName(sourceName);
						
					}else if(parser.getName().equals("topicIcon") && isEntry){
						news.setTopicIcon(parser.nextText());
					}
					break;
				case XmlPullParser.END_TAG:
					if(parser.getName().equals("entry") && news!=null){
						hotNewsList.add(news);
						news=null;
					}
					break;
				default:
					break;
				}
				parser.next();
				eventType=parser.getEventType();
			}
			inputStream.close();		
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		return hotNewsList;
	}


/**
 * 新闻内容的解析方法	
 * @param inputStream
 * @return
 */
public static ArrayList<NewsContent> ParseNewsContent(InputStream inputStream){
		ArrayList<NewsContent> newsContentList = null;
		NewsContent content = null;
		boolean isEntry=false;
		XmlPullParser parser=Xml.newPullParser();
		try {
			parser.setInput(inputStream, "UTF-8");
			int eventType=parser.getEventType();
			while(eventType!=XmlPullParser.END_DOCUMENT){
			
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
					newsContentList=new ArrayList<NewsContent>();
					break;
				case XmlPullParser.START_TAG:
					if(parser.getName().equals("NewsBody")){  //一条新闻开始
						isEntry=true;
						content=new NewsContent();
					}else if(parser.getName().equals("Title") && isEntry){
						content.setTitle(parser.nextText());	
					}else if(parser.getName().equals("SourceName") && isEntry){
						content.setSourceName(parser.nextText());			
					}else if(parser.getName().equals("Content") && isEntry){
						content.setContent(parser.nextText());
					}else if(parser.getName().equals("CommentCount") && isEntry){
						content.setCommentCount(Integer.parseInt(parser.nextText()));
					}
					break;
				case XmlPullParser.END_TAG:
					if(parser.getName().equals("NewsBody") && content!=null){
						newsContentList.add(content);
						content=null;
					}
					break;
				default:
					break;
				}
				
				parser.next();
				eventType=parser.getEventType();

			}
			
			inputStream.close();		
			
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return newsContentList;
	}

/**
 * 博客内容的解析方法	
 * @param inputStream
 * @return
 */
public static String ParseBlogsContent(InputStream inputStream){
		
		String content = null;
		boolean isEntry=false;
		XmlPullParser parser=Xml.newPullParser();
		try {
			parser.setInput(inputStream, "UTF-8");
			int eventType=parser.getEventType();
			
			while(eventType!=XmlPullParser.END_DOCUMENT){
			
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
					content="";
					break;
				case XmlPullParser.START_TAG:
					if(parser.getName().equals("string")){  //一条新闻开始
						isEntry=true;
						content= parser.nextText();
					}
					break;
				case XmlPullParser.END_TAG:
					if(parser.getName().equals("string") && content!=null){
						content=null;
					}
					break;
				default:
					break;
				}
				parser.next();
				eventType=parser.getEventType();
			}
			inputStream.close();		
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}

	/**
	 * 推荐博客的Pull解析方法
	 * @param inputStream
	 * @return
	 */
	public static ArrayList<Blogs> ParseBlogs(InputStream inputStream) {
		
		ArrayList<Blogs> blogList = null;
		Blogs blog = null;
		boolean isEntry=false;
		XmlPullParser parser=Xml.newPullParser();
		try {
			parser.setInput(inputStream, "UTF-8");
			int eventType=parser.getEventType();
			
			while(eventType!=XmlPullParser.END_DOCUMENT){
			
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
					blogList=new ArrayList<Blogs>();
					break;
				case XmlPullParser.START_TAG:
					if(parser.getName().equals("entry")){  
						isEntry=true;
						blog=new Blogs();
					}else if(parser.getName().equals("id") && isEntry){
						blog.setId(parser.nextText());	
					}else if(parser.getName().equals("title") && isEntry){
						blog.setTitle(parser.nextText());	
					}else if(parser.getName().equals("summary") && isEntry){
						blog.setSummary(parser.nextText());			
					}else if(parser.getName().equals("published") && isEntry){
						blog.setPublished(parser.nextText());	
					}else if(parser.getName().equals("updated") && isEntry){
						String[] t=parser.nextText().split("T");
						blog.setUpdated(t[0]+t[1]);
					}else if(parser.getName().equals("views") && isEntry){
						blog.setViews(Integer.parseInt(parser.nextText()));
					}else if(parser.getName().equals("comments") && isEntry){
						blog.setComments(Integer.parseInt(parser.nextText()));
					}else if(parser.getName().equals("diggs") && isEntry){
						blog.setDiggs(Integer.parseInt(parser.nextText()));
					}else if(parser.getName().equals("name") && isEntry){
						blog.setAuthorName(parser.nextText());
					}
					else if(parser.getName().equals("uri") && isEntry){
						blog.setAuthorUri(parser.nextText());
					}
					else if(parser.getName().equals("avatar") && isEntry){
						blog.setAuthorAvater(parser.nextText());
					}
					break;
				case XmlPullParser.END_TAG:
					if(parser.getName().equals("entry") && blog!=null){
						blogList.add(blog);
						blog=null;
					}
					break;
				default:
					break;
				}
				parser.next();
				eventType=parser.getEventType();
			}
			inputStream.close();		
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return blogList;
	}
	
	/**
	 * 获取评论的Pull解析方法
	 * @param inputStream
	 * @return
	 */
	public static ArrayList<Comment> ParseComment(InputStream inputStream) {
		
		ArrayList<Comment> commentList = null;
		Comment comment = null;
		boolean isEntry=false;
		XmlPullParser parser=Xml.newPullParser();
		try {
			parser.setInput(inputStream, "UTF-8");
			int eventType=parser.getEventType();
			
			while(eventType!=XmlPullParser.END_DOCUMENT){
			
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
					commentList=new ArrayList<Comment>();
					break;
				case XmlPullParser.START_TAG:
					if(parser.getName().equals("entry")){  
						isEntry=true;
						comment=new Comment();
					}else if(parser.getName().equals("id") && isEntry){
						comment.setId(parser.nextText());	
					}else if(parser.getName().equals("title") && isEntry){
						comment.setTitle(parser.nextText());	
					}else if(parser.getName().equals("published") && isEntry){
						comment.setPublished(parser.nextText());	
					}else if(parser.getName().equals("updated") && isEntry){
						String[] t=parser.nextText().split("T");
						comment.setUpdated(t[0]+t[1]);
					}else if(parser.getName().equals("name") && isEntry){
						comment.setName(parser.nextText());
					}else if(parser.getName().equals("uri") && isEntry){
						comment.setUri(parser.nextText());
					}else if(parser.getName().equals("content") && isEntry){
						comment.setContent(parser.nextText());
					}
					
					break;
				case XmlPullParser.END_TAG:
					if(parser.getName().equals("entry") && comment!=null){
						commentList.add(comment);
						comment=null;
					}
					break;
				default:
					break;
				}
				parser.next();
				eventType=parser.getEventType();
			}
			inputStream.close();		
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return commentList;
	}
	
	
	/**
	 * 搜索博主的Pull解析方法
	 * @param inputStream
	 * @return
	 */
	public static ArrayList<Blogger> ParseSearch(InputStream inputStream) {
		
		ArrayList<Blogger> commentList = null;
		Blogger comment = null;
		boolean isEntry=false;
		XmlPullParser parser=Xml.newPullParser();
		try {
			parser.setInput(inputStream, "UTF-8");
			int eventType=parser.getEventType();
			
			while(eventType!=XmlPullParser.END_DOCUMENT){
			
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
					commentList=new ArrayList<Blogger>();
					break;
				case XmlPullParser.START_TAG:
					if(parser.getName().equals("entry")){  
						isEntry=true;
						comment=new Blogger();
					}else if(parser.getName().equals("id") && isEntry){
						comment.setId(parser.nextText());	
					}else if(parser.getName().equals("title") && isEntry){
						comment.setTitle(parser.nextText());	
					}else if(parser.getName().equals("updated") && isEntry){
						String[] t=parser.nextText().split("T");
						comment.setUpdated(t[0]+t[1]);
					}else if(parser.getName().equals("avatar") && isEntry){
						comment.setAvatar(parser.nextText());
					}else if(parser.getName().equals("blogapp") && isEntry){
						comment.setBlogapp(parser.nextText());
					}
					else if(parser.getName().equals("postcount") && isEntry){
						comment.setPostcount(parser.nextText());
					}
					
					break;
				case XmlPullParser.END_TAG:
					if(parser.getName().equals("entry") && comment!=null){
						commentList.add(comment);
						comment=null;
					}
					break;
				default:
					break;
				}
				parser.next();
				eventType=parser.getEventType();
			}
			inputStream.close();		
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return commentList;
	}
	
}
