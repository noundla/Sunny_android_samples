package com.vl.samples.googleCharts;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;

import com.vl.samples.R;

public class GoogleChartsActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.google_charts);
		ImageView ivGraph = (ImageView) findViewById(R.id.chart_image);
		String strUrl = "http://chart.apis.google.com/chart?chxr=0,0,158&chxs=0,676767,11.5,0,_,676767&chxt=y&chbh=a&chs="
				+ "WIDTH_GRAPH"
				+ "x"
				+ "HEIGHT_GRAPH"
				+ "&cht=bvg&chco=0000FF,FF0000&chds=0,158,0,158&chd=t:"
				+ 79
				+ "|" + 30 + "&chdl=Tuo|Media&chdlp=b";

		LoadingRemoteGraph task = new LoadingRemoteGraph(this,
				ivGraph);
		task.execute(strUrl);
	}

	
	private class LoadingRemoteGraph extends AsyncTask<String, Void, Bitmap> {
		private final WeakReference<ImageView> imageViewReference;
		ProgressDialog dialog = null;
		private Context context = null;
		private ImageView ivT;
		int vHeight=0;
		int vWidth = 0;

		public LoadingRemoteGraph(Context contextT, final ImageView imageView) {
			context = contextT;
			imageViewReference = new WeakReference<ImageView>(imageView);

			ivT=imageView;
		}

		@Override
		protected void onPreExecute() {
			dialog = ProgressDialog.show(context, "",
					"Loading", true);
		}

		@Override
		protected Bitmap doInBackground(String...params) {
			Bitmap bmGraphT = null;
			URL img = null;

			ViewTreeObserver vto = ivT.getViewTreeObserver();
			vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
				public void onGlobalLayout() {
					vHeight = ivT.getHeight();
					vWidth = ivT.getWidth();
				}
			});

			String url=params[0].replace("WIDTH_GRAPH", String.valueOf(vWidth));
			url=url.replace("HEIGHT_GRAPH", String.valueOf(vHeight));

			try {
//				img = new URL(url);
//				img = new URL("http://chart.apis.google.com/chart?chf=a,s,000000AF&chs=300x300&cht=p3&chd=s:pvttkokfZbfdmn&chdl=30%C2%B0|40%C2%B0|50%C2%B0|60%C2%B0&chl=sa|dsd|dfs|gd&chma=0,0,5,10&chtt=sandeep&chts=C71717,11.5");
				img = new URL("http://chart.apis.google.com/chart?chs=440x220&cht=lxy&chco=3072F3,FF0000&chd=t:10,20,40,80,90,95,99|20,30,40,50,60,70,80|-1|5,10,22,35,85&chdl=Ponies|Unicorns&chdlp=b&chls=2,4,1|1&chma=5,5,5,25");
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}

			try {
				BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize=1;
                bmGraphT = BitmapFactory.decodeStream(img.openStream(),null,options);
			} catch (IOException e) {
				e.printStackTrace();
			}

			return bmGraphT;
		}

		@Override
		protected void onPostExecute(Bitmap bmT) {

			if (isCancelled()) {
				bmT = null;
			}
			if (imageViewReference != null) {
				ImageView imageView = imageViewReference.get();
				if (imageView != null) {
					imageView.setImageBitmap(bmT);
				}
			}

			dialog.dismiss();
		}

	}
}
