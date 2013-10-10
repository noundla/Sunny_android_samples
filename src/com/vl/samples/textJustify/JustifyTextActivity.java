package com.vl.samples.textJustify;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import com.vl.samples.R;

public class JustifyTextActivity extends Activity {
	WebView mJustifyText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main1);
		mJustifyText = (WebView)findViewById(R.id.text_justify);
		mJustifyText.setVisibility(View.VISIBLE);
		
//		String data="due to marriage of my frnd sister i hav gone to nzb there from i returnd to home.\nBut Myself thought like, firstly I hav to talk to samu before doing any task in jgl .So, while cmng i enquired about her brother through shekar bcoz i dont like him and also samu maynot feel comfertable to talk me  if he is in home. finally he is not in home.confirmed. now my heart is beating very fastly. I unable to control it. Then decided to talk now. so wt next??? what i hav to do call  or msg. which is better?? msg or call? cal or msg? its hard to decide bt finally choosen msg bcoz msg is the currect one to know wt z the present situation in her home and easy to start the conversation. As the result of  that i can easil y to talk to her. " +
//				"so i hav sent a msg to her abt  how r u? now my situation is not good bcoz abt alot of q's are playing with my mind.like...... is she using that number or not? is she in the position to give reply r not?wt z she doing ri8 now?";
//		
		String data ="due to marriage of my frnd sister i hav gone to nzb there from i returnd to home.\nBut Myself thought like, firstly I hav to talk to samu before doing any task in jgl .So, while cmng i enquired about her brother through shekar bcoz i dont like him and also samu maynot feel comfertable to talk me  if he is in home. finally he is not in home.confirmed. now my heart is beating very fastly. I unable to control it. Then decided to talk now. so wt next??? what i hav to do call  or msg. which is better?? msg or call? cal or msg? its hard to decide bt finally choosen msg bcoz msg is the currect one to know wt z the present situation in her home and easy to start the conversation..";
		String text = "<html><body style='text-align:justify;color:gray;'>"+data+"</body></html>";
		
		
		mJustifyText.loadData(text, "text/html", "utf-8");
		mJustifyText.setBackgroundColor(0x00000000);/*applies transparency */
		mJustifyText.setVerticalScrollBarEnabled(false);
	}
}
