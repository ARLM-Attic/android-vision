package com.hfk.imageprocessing.edgedetection;

import org.opencv.core.Mat;
import org.opencv.core.CvType;
import org.opencv.imgproc.Imgproc;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;

import com.hfk.imageprocessing.ImageProcessingView;
import com.hfk.imageprocessing.R;
import com.hfk.imageprocessing.gesture.ActionGesture;

public class EdgeDetectionView extends ImageProcessingView implements ActionGesture {
	static final int None = 0;
	static final int Sobel = 1;
	static final int Canny = 2;
	
    public EdgeDetectionView(Context context) {
        super(context);
    }

    @Override
    protected String getTitle() {
    	Resources res = getResources();
    	if(res == null)
    	{
    		return "empty";
    	}
    	String title = "nothing";
    	try {
    		title = res.getString(R.string.filters_edgedetection_title);        		
    	}
    	catch (Resources.NotFoundException ex) {
    		return "nfexception";
    	}
    	catch (Exception ex) {
    		return "exception";    		
    	}
    	
    	return title;
    }

    @Override
    protected String[] getAvailableFilters() {
    	Resources res = getResources();
    	return res.getStringArray(R.array.filters_edgedetection_items);
    }
    
    public void onTapLeft() {
    }
    
    public void onTapRight() {
    }

    @Override
    public Class<?> getConfigClassForFilter(int filter)
    {
    	Class<?> configClass = null; 
    	switch(filter) {
    	case None:
    		break;
    	case Sobel:
    	case Canny:
    		break;
		default:
			break;
    	}
    	
    	return configClass;
    }

    @Override
    public Bundle getDefaultConfigBundleForFilter(int filter)
    {
    	Bundle configBundle = null; 
    	switch(filter) {
    	case None:
    		break;
    	case Sobel:
    	case Canny:
    		break;
		default:
			break;
    	}
    	
    	return configBundle;
    }

    @Override
    protected void applyFilter(int filter, Mat sourceMat, Mat targetMat, Bundle configValues, StringBuilder text)
    {
    	switch(filter) {
    	case None:
    		text.append("Applying None");
    		sourceMat.copyTo(targetMat);
    		break;
    	case Sobel:
    		text.append("Applying Sobel");
    		Imgproc.Sobel(sourceMat, targetMat, CvType.CV_8U, 0, 1, 3, 0.4, 128);
    		break;
    	case Canny:
    		text.append("Applying Sobel");
    		Imgproc.Canny(sourceMat, targetMat, 150, 250, 3);
    		break;
		default:
			text.append("Applying Default");
			break;
    	}
	}
}
