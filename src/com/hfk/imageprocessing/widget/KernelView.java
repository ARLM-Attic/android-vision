package com.hfk.imageprocessing.widget;

import java.util.ArrayList;

import android.content.*;
import android.graphics.Color;
import android.util.*;
import android.view.*;
import android.graphics.*;

import com.hfk.widget.*;

public class KernelView extends MatrixView {
	
    public KernelView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
    public void setKernelCenter(int column, int row) {
    	kernelCenterX = column;
    	kernelCenterY = row;
    	
		for(int i = 0; i < getNumberOfColumns(); i++) {
			for(int j = 0; j < getNumberOfColumns(); j++) {
				setCellStyle(i, j, Color.WHITE);
			}
		}

    	setCellStyle(kernelCenterX, row, Color.RED);
    	
    	invalidate();    	
    }
    
    public int getCenterX(){
    	return kernelCenterX;
    }
    
    public int getCenterY(){
    	return kernelCenterY;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	int touchedColumnIndex = getColumnAtPoint((int)event.getX());
    	int touchedRowIndex = getRowAtPoint((int)event.getY());
    	
    	setKernelCenter(touchedColumnIndex, touchedRowIndex);
    	
    	return true;
    }
    
    private int kernelCenterX;
    private int kernelCenterY;
}
