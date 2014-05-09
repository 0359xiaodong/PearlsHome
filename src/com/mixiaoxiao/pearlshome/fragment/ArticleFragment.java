package com.mixiaoxiao.pearlshome.fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mixiaoxiao.android.common.fragment.MxxRefreshableListViewFragment;
import com.mixiaoxiao.android.quickadapter.BaseAdapterHelper;
import com.mixiaoxiao.android.quickadapter.QuickAdapter;
import com.mixiaoxiao.android.view.MxxRefreshableListView;
import com.mixiaoxiao.pearlshome.ArticleDetailActivity;
import com.mixiaoxiao.pearlshome.R;
import com.mixiaoxiao.pearlshome.bean.ArticleItem;
import com.mixiaoxiao.pearlshome.manager.ArticleManager;
import com.nhaarman.listviewanimations.swinginadapters.AnimationAdapter;
import com.nhaarman.listviewanimations.swinginadapters.prepared.AlphaInAnimationAdapter;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;

public class ArticleFragment extends MxxRefreshableListViewFragment{
	
	private ArticleManager mArticleManager;
	
	
	@Override
	public void onActivityCreated(android.os.Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mArticleManager = new ArticleManager(getActivity());
		
//		QuickAdapter<ArticleItem> quickAdapter = new QuickAdapter<ArticleItem>(getActivity(),R.layout.listitem_article,mArticleManager.getArticleItems()) {
//			@Override
//			protected void convert(BaseAdapterHelper helper, ArticleItem item) {
//				// TODO Auto-generated method stub
//				helper.setText(R.id.article_title, item.getTitle())
//				.setText(R.id.article_section, item.getSection())
//				.setVisible(R.id.article_section, !TextUtils.isEmpty(item.getSection()))
//				.setText(R.id.article_footer, item.getFooter())
//				.setVisible(R.id.article_footer, !TextUtils.isEmpty(item.getFooter()));
//			}
//		};
//		final AlphaInAnimationAdapter adapter = new AlphaInAnimationAdapter(quickAdapter);
		final AlphaInAnimationAdapter adapter = new AlphaInAnimationAdapter(new ArticleAdapter());
//		CardsAnimationAdapter adapter = new CardsAnimationAdapter( quickAdapter);
		adapter.setAbsListView(mListView);
		mListView.setAdapter(adapter); 
		
		mListView.setOnTopRefreshListener(new MxxRefreshableListView.OnTopRefreshListener() {
			@Override
			public void onStart() {}
			@Override
			public void onEnd() {}
			
			@Override
			public void onDoinBackground() {
				mArticleManager.updateFirstPage();
			}
		});
		mListView.setOnBottomRefreshListener(new MxxRefreshableListView.OnBottomRefreshListener() {
			@Override
			public void onStart() {}
			@Override
			public void onEnd() {
				adapter.setShouldAnimateFromPosition(mListView.getLastVisiblePosition());
			}
			
			@Override
			public void onDoinBackground() {
				mArticleManager.updateNextPage();
			}
		});
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View convertView, int position,
					long arg3) {
				// TODO Auto-generated method stub
				final ArticleItem item = mArticleManager.getArticleItems().get(position-1);
				ArticleDetailActivity.startWithData(item.getHref(), getActivity());
//				
			}
		});
		mListView.post(new Runnable() {
			@Override
			public void run() {
				if(mArticleManager.loadDbData()){
					mListView.notifyDataSetChanged();
				}else{
					mListView.startUpdateImmediate();
				}
			}
		});
	};
	private class ArticleAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mArticleManager.getArticleItems().size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder = null;
			if(convertView==null){
				convertView = LayoutInflater.from(getActivity()).inflate(R.layout.listitem_article, null);
				holder = new ViewHolder();
				holder.title = (TextView) convertView.findViewById(R.id.article_title);
				holder.section = (TextView) convertView.findViewById(R.id.article_section);
				holder.footer = (TextView) convertView.findViewById(R.id.article_footer);
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			final ArticleItem item = mArticleManager.getArticleItems().get(position);
			holder.title.setText(item.getTitle());
			final String section = item.getSection();
			if(TextUtils.isEmpty(section)){
				holder.section.setVisibility(View.GONE);
			}else{
				holder.section.setVisibility(View.VISIBLE);
				holder.section.setText(section);
			}
			final String footer = item.getFooter();
			if(TextUtils.isEmpty(footer)){
				holder.footer.setVisibility(View.GONE);
			}else{
				holder.footer.setVisibility(View.VISIBLE);
				holder.footer.setText(footer);
			}
			return convertView;
		}
		class ViewHolder{
			public TextView title;
			public TextView section;
			public TextView footer;
		}
	}
	
	
	
	class CardsAnimationAdapter extends AnimationAdapter {
	    private float mTranslationY = 400;

	    private float mRotationX = 15;

	    private long mDuration = 400;

	    public CardsAnimationAdapter(BaseAdapter baseAdapter) {
	        super(baseAdapter);
	    }

	    @Override
	    protected long getAnimationDelayMillis() {
	        return 30;
	    }

	    @Override
	    protected long getAnimationDurationMillis() {
	        return mDuration;
	    }

	    @Override
	    public Animator[] getAnimators(ViewGroup parent, View view) {
	        return new Animator[]{
	                ObjectAnimator.ofFloat(view, "translationY", mTranslationY, 0),
	                ObjectAnimator.ofFloat(view, "rotationX", mRotationX, 0)
	        };
	    }
	}

}
