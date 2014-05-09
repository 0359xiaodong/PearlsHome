package com.mixiaoxiao.pearlshome;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mixiaoxiao.android.common.activity.MxxBrowserActivity;
import com.mixiaoxiao.android.util.MxxSystemBarTintUtil;
import com.mixiaoxiao.android.util.MxxTextUtil;
import com.mixiaoxiao.android.util.MxxToastUtil;
import com.mixiaoxiao.pearlshome.bean.ArticleItem;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.Html;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.widget.TextView;

public class ArticleDetailActivity extends Activity {
	
	private static final String extra_url = "url";
	
	public static void startWithData(String url, Activity activity){
		Intent intent = new Intent(activity, ArticleDetailActivity.class);
		intent.putExtra(extra_url, url);
		activity.startActivity(intent);
	}
	
	private DetailTask detailTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setTitle(MxxTextUtil.getTypefaceSpannableString(this,  getString(R.string.app_name),MxxTextUtil.Roboto_Light, false));
		getActionBar().setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.activity_article_detail);
		MxxSystemBarTintUtil.insertPadding(findViewById(R.id.detail_scrollview), this);
		MxxSystemBarTintUtil.insertPadding(findViewById(R.id.detail_loading_layout), this);
		String url = getIntent().getStringExtra(extra_url);
		if(!TextUtils.isEmpty(url)){
			detailTask = new DetailTask();
			detailTask.execute(url);
		}else{
			finish();
		}
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(detailTask!=null){
			detailTask.cancel(true);
		}
	}

	private class DetailTask extends AsyncTask<String, Void, ArticleItem> {

		@Override
		protected ArticleItem doInBackground(String... params) {
			// TODO Auto-generated method stub
			Document doc = null;
			try {
				doc = Jsoup.connect(params[0]).get();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
			Element content = doc.getElementById("main");
			Elements links = content.getElementsByTag("article");
			Element article = links.get(0);
			boolean isTop = article.hasAttr("class");
			String title = article.getElementsByTag("h2").text();
//			String section = article.getElementsByTag("section").get(0)
//					.getElementsByTag("p").text();
			String section = article.getElementsByTag("section").get(0).html();
			String footer = article.getElementsByTag("footer").text();
			ArticleItem item = new ArticleItem();
			item.setTop(isTop);
			// item.setHref(href);
			item.setTitle(title);
			item.setSection(section);
			item.setFooter(footer);
			// System.out.println("title:" + title + "\nsection:" + section
			// + "\nfooter:" + footer + "\n==========");
			return item;
		}
		
		@Override
		protected void onPostExecute(ArticleItem item) {
			// TODO Auto-generated method stub
			super.onPostExecute(item);
			if(item == null){
				MxxToastUtil.showToast(ArticleDetailActivity.this, "º”‘ÿ ß∞‹");
				finish();
				return;
			}
//			getActionBar().setTitle(item.getTitle());
			((TextView)findViewById(R.id.article_title)).setText(item.getTitle());
			WebView webView = (WebView)findViewById(R.id.article_section);
			WebSettings settings = webView.getSettings(); 
//			settings.setUseWideViewPort(true); 
//			settings.setLoadWithOverviewMode(true);
			settings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
			webView.getSettings().setJavaScriptEnabled(true);
			webView.setWebViewClient(new WebViewClient(){
				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					MxxBrowserActivity.startWithExtra(ArticleDetailActivity.this, url);
//					view.loadUrl(url);
					return true;
				}
			});
			webView.loadDataWithBaseURL(null, item.getSection(), "text/html", "utf-8",
					null);
			//((TextView)findViewById(R.id.article_section)).setText(Html.fromHtml(item.getSection()));
			((TextView)findViewById(R.id.article_footer)).setText(item.getFooter());
			findViewById(R.id.detail_loading_layout).setVisibility(View.GONE);
		}

	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.article_detail, menu);
//		return true;
//	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if(item.getItemId() == android.R.id.home){
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
