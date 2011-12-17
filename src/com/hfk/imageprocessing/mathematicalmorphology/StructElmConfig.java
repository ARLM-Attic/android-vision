package com.hfk.imageprocessing.mathematicalmorphology;

import com.hfk.imageprocessing.R;
import com.hfk.imageprocessing.widget.KernelView;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class StructElmConfig extends Activity implements OnItemSelectedListener {
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.mathmorph_structelm);
        
		Resources res = getResources();
		String[] shapes = res.getStringArray(R.array.filters_mathmorph_kernelshapes);
		
		Spinner spin = (Spinner) findViewById(R.id.spinnerKernelShapeStructElm);
		spin.setOnItemSelectedListener(this);

		ArrayAdapter<String> aa = new ArrayAdapter<String>(
				this,
				android.R.layout.simple_spinner_item, 
				shapes);

		aa.setDropDownViewResource(
		   android.R.layout.simple_spinner_dropdown_item);
		spin.setAdapter(aa);
        
        mTextKernelSize = (EditText)findViewById(R.id.editKernelSizeStructElm);
        mKernelMatrix = (KernelView)findViewById(R.id.matrixStructElm);
        
        Bundle data = getIntent().getExtras();
        if(data != null){
        	if(data.containsKey("KERNEL_SIZE")) {
        		int kernelSize = data.getInt("KERNEL_SIZE");
        		mTextKernelSize.setText(Integer.toString(kernelSize));        		
        		mKernelMatrix.setNumberOfColumns(kernelSize);
        		mKernelMatrix.setNumberOfRows(kernelSize);
        	}
        		
        	if(data.containsKey("KERNEL_CENTERX") && data.containsKey("KERNEL_CENTERY")) {        		
        		mKernelMatrix.setKernelCenter(data.getInt("KERNEL_CENTERX"), data.getInt("KERNEL_CENTERY"));
        	}
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

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	
	private void updateKernelMatrix() {
		String kernelValueAsString = mTextKernelSize.getText().toString();
		if((kernelValueAsString == null || kernelValueAsString.length() == 0)) {
			return;
		}
		
		int kernelValue = Integer.parseInt(kernelValueAsString);
    	
    	mKernelMatrix.setNumberOfColumns(kernelValue);
    	mKernelMatrix.setNumberOfRows(kernelValue);
    	
    	mKernelMatrix.setKernelCenter((kernelValue-1)/2, (kernelValue-1)/2);
	    
    	mKernelMatrix.invalidate();
	}

	private EditText mTextKernelSize;
	private KernelView mKernelMatrix;
}
