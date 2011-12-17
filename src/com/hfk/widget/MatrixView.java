package com.hfk.widget;

import android.view.*;
import android.content.*;
import android.graphics.*;
import android.util.*;

import java.util.*;

public class MatrixView extends View {

	public MatrixView(Context context, AttributeSet attrs){
        super(context, attrs);
	}	
	
	public void setNumberOfColumns(int numberOfColumns) {
		mNumberOfColumns = numberOfColumns;
		mCellStyleMatrix = null;
	}
	
	public int getNumberOfColumns() {
		return mNumberOfColumns;
	}
	
	public void setNumberOfRows(int numberOfRows) {
		mNumberOfRows = numberOfRows;
		mCellStyleMatrix = null;
	}
	
	public int getNumberOfRows() {
		return mNumberOfRows;
	}
	
	public int getColumnAtPoint(int xCoord) {
		return xCoord / (this.getWidth()/mNumberOfColumns);
	}
	
	public int getRowAtPoint(int yCoord) {
		return yCoord / (this.getHeight()/mNumberOfRows);
	}
	
	public void setCellText(int colIndex, int rowIndex, String text) {
		if(mCellValueMatrix == null) {
			mCellValueMatrix = new ArrayList<List<String>>();
			
			for(int i=0; i<mNumberOfColumns; i++) {
				mCellValueMatrix.add(new ArrayList<String>());
				for(int j=0; j<mNumberOfColumns; j++) {
					mCellValueMatrix.get(i).add("");
				}
			}
		}
		
		mCellValueMatrix.get(colIndex).add(rowIndex, text);
	}
	
	public String getCellText(int colIndex, int rowIndex) {
		return mCellValueMatrix.get(colIndex).get(rowIndex);
	}
	
	public void setCellStyle(int colIndex, int rowIndex, int color) {
		if(mCellStyleMatrix == null) {
			mCellStyleMatrix = new ArrayList<List<Integer>>();
			
			for(int i=0; i<mNumberOfColumns; i++) {
				mCellStyleMatrix.add(new ArrayList<Integer>());
				for(int j=0; j<mNumberOfColumns; j++) {
					mCellStyleMatrix.get(i).add(Color.WHITE);
				}
			}
		}
		
		mCellStyleMatrix.get(colIndex).add(rowIndex, color);
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
	    
	    if((mNumberOfColumns == 0) || (mNumberOfRows == 0)) {
	    	return;
	    }
	    
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
	    
	    if(mCellStyleMatrix != null) {
			for(int i=0; i<mNumberOfColumns; i++) {
				for(int j=0; j<mNumberOfColumns; j++) {
					int color = mCellStyleMatrix.get(i).get(j);
					if(color != Color.WHITE) {
					    paint.setColor(color);
						c.drawRect(/* left */ i * cellWidth, 
								/* top */ j * cellHeight, 
								/* right */ (i + 1) * cellWidth, 
								/* bottom*/ (j + 1) * cellHeight, 
								paint);
					}
				}
			}	    	
	    }
	    
	    if(mCellValueMatrix != null) {
			for(int i=0; i<mNumberOfColumns; i++) {
				for(int j=0; j<mNumberOfColumns; j++) {
					String text = mCellValueMatrix.get(i).get(j);
					if((text != null) && (text != ""))  {
						c.drawText(/* text */ text, 
								/* x */ j * cellHeight, 
								/* y */ (i + 1) * cellWidth, 
								paint);
					}
				}
			}	    	
	    }
	}
    
    private int mNumberOfColumns = 0;
    private int mNumberOfRows = 0;
    private List<List<String>> mCellValueMatrix;
    private List<List<Integer>> mCellStyleMatrix;
}
