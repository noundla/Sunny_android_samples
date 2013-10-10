package com.vl.samples.customTextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.vl.samples.R;

public class TypefacedTextView extends TextView {

	public TypefacedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //Typeface.createFromAsset doesn't work in the layout editor. Skipping...
        if (isInEditMode()) {
            return;
        }

        TypedArray styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.TypefacedTextView);
        //String fontName = styledAttrs.getString(R.styleable.TypefacedTextView_typeface);
        String fontNameForBold = styledAttrs.getString(R.styleable.TypefacedTextView_textStyle);
        styledAttrs.recycle();

        if (fontNameForBold!=null && fontNameForBold.equals("bold")) {
        	Typeface typeface_bold = Typeface.createFromAsset(context.getAssets(), "fonts/tahoma/tahoma_bold.ttf");
            setTypeface(typeface_bold);
		}else{
			Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/tahoma/tahoma.ttf");
            setTypeface(typeface);
		}
    }

}
