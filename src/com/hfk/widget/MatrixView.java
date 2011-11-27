package com.hfk.widget;

import android.view.*;
import android.content.*;
import android.graphics.*;
import android.util.AttributeSet;

import java.util.*;

public class MatrixView extends View {

	public MatrixView(Context context, AttributeSet attrs){
        super(context, attrs);
	}	
	
	public void setNumberOfColumns(int numberOfColumns) {
		mNumberOfColumns = numberOfColumns;
	}
	
	public void setNumberOfRows(int numberOfRows) {
		mNumberOfRows = numberOfRows;
	}
	
	public void setCellText(int colIndex, int rowIndex, String text) {
	}
	
	public void setCellStyle(int colIndex, int rowIndex, String text) {
	}

    @Override
	protected void onDraw(Canvas c){
	    super.onDraw(c);
	    Paint paint = new Paint();
	    paint.setStyle(Paint.Style.FILL);

	    // make the entire canvas white
	    paint.setColor(Color.WHITE);
	    c.drawPaint(paint);
	    
	    paint.setAntiAlias(true);
	    paint.setColor(Color.BLUE);
	    
	    int cellWidth = this.getWidth()/mNumberOfColumns;
	    int cellHeight = this.getHeight()/mNumberOfRows;
	    
	    for (int i = 0; i < mNumberOfColumns; i++) {
	    	c.drawLine( 
	    			/* startX */ i * cellWidth,
	    			/* startY */ 0,
	    			/* stopX */  i * cellWidth,
	    			/* stopY */  this.getHeight(),
	    			paint);
	    }
	    
	    for (int i = 0; i < mNumberOfRows; i++) {
	    	c.drawLine( 
	    			/* startX */ 0,
	    			/* startY */ i * cellHeight,
	    			/* stopX */  this.getWidth(),
	    			/* stopY */  i * cellHeight,
	    			paint);
	    }
	}
    
    private int mNumberOfColumns = 0;
    private int mNumberOfRows = 0;
    private List<List<Double>> mCellValueMatrix;
}
