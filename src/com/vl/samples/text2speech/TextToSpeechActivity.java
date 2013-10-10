package com.vl.samples.text2speech;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.vl.samples.R;

public class TextToSpeechActivity extends Activity implements OnInitListener{
	private EditText words = null;
	  private Button speakBtn = null;
	    private static final int REQ_TTS_STATUS_CHECK = 0;
	  private static final String TAG = "TTS Demo";
	    private TextToSpeech mTts;

	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.text2speech);

	        words = (EditText)findViewById(R.id.wordsToSpeak);
	        speakBtn = (Button)findViewById(R.id.speak);

	        Intent checkIntent = new Intent();
	        checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
	        startActivityForResult(checkIntent, REQ_TTS_STATUS_CHECK);
	    }
	    
	    public void doSpeak(View view) {
	    mTts.speak(words.getText().toString(), TextToSpeech.QUEUE_ADD, null);
	  };
	  
	    @Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	        if (requestCode == REQ_TTS_STATUS_CHECK) {
	            switch (resultCode) {
	            case TextToSpeech.Engine.CHECK_VOICE_DATA_PASS:
	                mTts = new TextToSpeech(this, (OnInitListener) this);
	                break;
	            case TextToSpeech.Engine.CHECK_VOICE_DATA_BAD_DATA:
	            case TextToSpeech.Engine.CHECK_VOICE_DATA_MISSING_DATA:
	            case TextToSpeech.Engine.CHECK_VOICE_DATA_MISSING_VOLUME:
	                Intent installIntent = new Intent();
	                installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
	                startActivity(installIntent);
	                break;
	            case TextToSpeech.Engine.CHECK_VOICE_DATA_FAIL:
	            default:
	                Log.e(TAG, "Got a failure.");
	            }
	        }
	    }
	  public void onInit(int status) {
	    if( status == TextToSpeech.SUCCESS) {
	      speakBtn.setEnabled(true);
	    }
	  }
	  @Override
	  public void onPause(){
	    super.onPause();
	    if( mTts != null)
	      mTts.stop();
	  }
	  @Override
	    public void onDestroy(){
	        super.onDestroy();
	        mTts.shutdown();
	    }
}
