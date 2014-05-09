package com.mixiaoxiao.pearlshome.manager;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.content.Context;

import com.mixiaoxiao.pearlshome.bean.ArticleItem;

import net.tsz.afinal.FinalDb;

public class ArticleManager {

	private FinalDb finalDb;
	private ArrayList<ArticleItem> articleItems;
	private int page;

	public ArticleManager(Context context) {
		articleItems = new ArrayList<ArticleItem>();
		finalDb = FinalDb.create(context, "article_db", false);
		page = 1;
	}

	public ArrayList<ArticleItem> getArticleItems() {
		return articleItems;
	}

	public boolean loadDbData() {
		this.articleItems.addAll(finalDb.findAll(ArticleItem.class));
		return articleItems.size() > 0;
	}

	public void updateFirstPage() {
		this.page = 1;
		updateListInBackground();
	}

	public void updateNextPage() {
		this.page = page + 1;
		updateListInBackground();
	}

	private void updateListInBackground() {
		ArrayList<ArticleItem> articleItems_tmp = new ArrayList<ArticleItem>();
		String url = "http://www.zhenzhusheng.com/?mod=pad&page=" + page;
		try {
			Document doc = Jsoup.connect(url).get();
			Element content = doc.getElementById("main");
			Elements links = content.getElementsByTag("article");
			boolean shouldSaveInDb = page == 1;
			if(shouldSaveInDb){
				finalDb.deleteByWhere(ArticleItem.class, null);
			}
			for (Element article : links) {
//				String linkHref = article.attr("href");
				boolean isTop = article.hasAttr("class");
				

				String href = article.getElementsByTag("h2").get(0)
						.getElementsByTag("a").attr("href");
				String title = article.getElementsByTag("h2").text();
				String section = article.getElementsByTag("section").text();
				String footer = article.getElementsByTag("footer").text();

				ArticleItem item = new ArticleItem();
				item.setTop(isTop);
				item.setHref(href);
				item.setTitle(title);
				item.setSection(section);
				item.setFooter(footer);
				
				if(page==1){
					articleItems_tmp.add(item);
					finalDb.save(item);
				}else{
					if(!isTop){
						articleItems_tmp.add(item);
					}
				}
				
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.page = page - 1;
			return ;
		}
		if(page==1){
			this.articleItems.clear();
		}
		this.articleItems.addAll(articleItems_tmp);
	}

}
