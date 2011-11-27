package com.hfk.imageprocessing.blur;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.hfk.imageprocessing.R;
import com.hfk.widget.*;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

public class BoxConfig extends Activity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.blur_box);
        
        mTextKernelSize = (EditText)findViewById(R.id.editKernalSizeBox);
//        mKernelMatrix = (MatrixView)findViewById(R.id.matrixBoxKernel);
        
        Bundle data = getIntent().getExtras();
        if(data != null){
        	if(data.containsKey("KERNEL_SIZE"))
        		mTextKernelSize.setText(Integer.toString(data.getInt("KERNEL_SIZE")));        	
        }
        
        TextWatcher kernelSizeTextWatcher = new TextWatcher() 
        {         
        	@Override         
        	public void afterTextChanged(Editable s) {         
        		updateKernelMatrix();         
        	}          
        	
        	@Override         
        	public void beforeTextChanged(CharSequence s, int start, int count, int after) {         
        		
        	}          
        	
        	@Override         
        	public void onTextChanged(CharSequence s, int start, int before, int count) {     
        		
        	}          
   
        };

        mTextKernelSize.addTextChangedListener(kernelSizeTextWatcher); 
        
        mTextKernelSize.setOnFocusChangeListener(new View.OnFocusChangeListener() {    
        	public void onFocusChange(View v, boolean hasFocus) {        
        		if (!hasFocus) {            
        			String kernelValueAsString = mTextKernelSize.getText().toString();
        			if(kernelValueAsString == null || kernelValueAsString.length() == 0 || Integer.parseInt(mTextKernelSize.getText().toString()) == 0) {
            			mTextKernelSize.requestFocus();        
        			}
        		}    
        	}
        });
	}
	
	@Override public void onResume() {     
		super.onResume(); 
		updateKernelMatrix();
	} 

	@Override
	public void onBackPressed() {
	    Intent result = new Intent();

	    Bundle b = new Bundle();
	    b.putInt("KERNEL_SIZE", Integer.parseInt(mTextKernelSize.getText().toString()));

	    result.putExtras(b);

	    setResult(Activity.RESULT_OK, result);
	    
	    super.onBackPressed();
	}
	
	private void updateKernelMatrix() {
//		String kernelValueAsString = mTextKernelSize.getText().toString();
//		if((kernelValueAsString == null || kernelValueAsString.length() == 0)) {
//			return;
//		}
//		
//		int kernelValue = Integer.parseInt(kernelValueAsString);
//    	
//    	mKernelMatrix.setNumberOfColumns(kernelValue);
//    	mKernelMatrix.setNumberOfRows(kernelValue);
//	    
//    	mKernelMatrix.invalidate();
	}

	private EditText mTextKernelSize;
	private MatrixView mKernelMatrix;
}
