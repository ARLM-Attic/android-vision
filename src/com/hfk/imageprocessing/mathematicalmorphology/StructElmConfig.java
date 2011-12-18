package com.hfk.imageprocessing.mathematicalmorphology;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import com.hfk.imageprocessing.R;
import com.hfk.imageprocessing.widget.KernelView;
import com.hfk.widget.MatrixView;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class StructElmConfig extends Activity {
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.mathmorph_structelm);
        
		Resources res = getResources();
		String[] shapes = res.getStringArray(R.array.filters_mathmorph_kernelshapes);
		
		mKernelShape = (Spinner) findViewById(R.id.spinnerKernelShapeStructElm);

		ArrayAdapter<String> availableShapes = new ArrayAdapter<String>(
				this,
				android.R.layout.simple_spinner_item, 
				shapes);

		availableShapes.setDropDownViewResource(
		   android.R.layout.simple_spinner_dropdown_item);
		mKernelShape.setAdapter(availableShapes);
        
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
        	
        	if(data.containsKey("KERNEL_SHAPE")) {
        		int kernelShape = data.getInt("KERNEL_SHAPE");
    			mKernelShape.setSelection(kernelShape);
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
        
		mKernelShape.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
        		updateKernelMatrix();         
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}			
		});
        
        mKernelMatrix.setOnCellTouchHandler(new MatrixView.OnCellTouchHandler() {			
			@Override
			public void OnCellTouch(int colIndex, int rowIndex) {
			}
			
			@Override
			public void OnCellLongTouch(int colIndex, int rowIndex) {
		    	mKernelMatrix.setKernelCenter(colIndex, rowIndex);
			}
		});

	}

	@Override
	public void onBackPressed() {
	    Intent result = new Intent();

	    Bundle b = new Bundle();
	    b.putInt("KERNEL_SIZE", Integer.parseInt(mTextKernelSize.getText().toString()));
	    b.putInt("KERNEL_CENTERX", mKernelMatrix.getCenterX());
	    b.putInt("KERNEL_CENTERY", mKernelMatrix.getCenterY());
	    b.putInt("KERNEL_SHAPE", mKernelShape.getSelectedItemPosition());

	    result.putExtras(b);

	    setResult(Activity.RESULT_OK, result);
	    
	    super.onBackPressed();
	}

	private void updateKernelMatrix() {
		String kernelValueAsString = mTextKernelSize.getText().toString();
		if((kernelValueAsString == null || kernelValueAsString.length() == 0)) {
			return;
		}
		
		int kernelValue = Integer.parseInt(kernelValueAsString);
    	
		if(mKernelMatrix.getNumberOfColumns() != kernelValue){
	    	mKernelMatrix.setNumberOfColumns(kernelValue);
	    	mKernelMatrix.setNumberOfRows(kernelValue);
	    	
	    	mKernelMatrix.setKernelCenter((kernelValue-1)/2, (kernelValue-1)/2);			
		}
		
		int kernelShape = Imgproc.MORPH_RECT;
		switch(mKernelShape.getSelectedItemPosition()){
		case 0:
			kernelShape = Imgproc.MORPH_RECT;
			break;
		case 1:
			kernelShape = Imgproc.MORPH_ELLIPSE;
			break;
		case 2:
			kernelShape = Imgproc.MORPH_CROSS;
			break;
		case 3:
			kernelShape = -1;
			break;
		}
		
		if(kernelShape != -1){
	    	Size sz = new Size(kernelValue, kernelValue); 
	    	Mat kernel = Imgproc.getStructuringElement(
	    			kernelShape, 
	    			sz, 
	    			new Point(mKernelMatrix.getCenterX(), mKernelMatrix.getCenterY()));			
	    	for(int row = 0; row < kernel.rows(); row++) {
	    		for(int col = 0; col < kernel.cols(); col++){
	    			double cellValue = kernel.get(row, col)[0];
	    			if(cellValue == 1.0) {
	    				mKernelMatrix.setCellStyle(col, row, Color.RED);
	    			}
	    			else {
	    				mKernelMatrix.setCellStyle(col, row, Color.WHITE);
	    			}
	    		}
	    	}
		}
		else {
			//mKernelMatrix.setKernelValue(colIndex, rowIndex, value);
		}
	    
    	mKernelMatrix.invalidate();
	}

	private EditText mTextKernelSize;
	private KernelView mKernelMatrix;
	private Spinner mKernelShape;
}
