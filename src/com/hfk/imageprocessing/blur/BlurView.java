package com.hfk.imageprocessing.blur;

import org.opencv.android;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import android.content.*;
import android.content.res.*;
import android.os.*;
import android.widget.*;

import com.hfk.imageprocessing.*;
import com.hfk.imageprocessing.gesture.ActionGesture;

public class BlurView extends ImageProcessingView implements ActionGesture {
	int kernel = 5;
	double sigma = 2.0;
	
    public BlurView(Context context) {
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
    		title = res.getString(R.string.filters_blur_title);        		
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
    	return res.getStringArray(R.array.filters_blur_items);
    }
    
    public void onTapLeft() {
    	DecrementKernel();
    }
    
    public void onTapRight() {
    	IncrementKernel();
    }

    public void IncrementKernel()
    {
    	Bundle topConfigBundle = getConfigBundleForView(0);
    	if(topConfigBundle != null) {
	    	int topKernelSize = topConfigBundle.getInt("KERNEL_SIZE");
	    	topConfigBundle.putInt("KERNEL_SIZE", IncrementKernelValue(topKernelSize));
    	}

    	Bundle bottomConfigBundle = getConfigBundleForView(0);
    	if(bottomConfigBundle != null) {
	    	int bottomKernelSize = bottomConfigBundle.getInt("KERNEL_SIZE");
	    	bottomConfigBundle.putInt("KERNEL_SIZE", IncrementKernelValue(bottomKernelSize));
    	}
    	
//		int newValue = size;
//	    StringBuilder sb = new StringBuilder();
//        //sb.append("[").append(mainView.getWidth() / 2).append("][").append(e.getX()).append("]");
//	    sb.append("Increment[").append(newValue).append("]");
//    	Toast.makeText(this.getContext(), sb.toString(), Toast.LENGTH_SHORT).show();
    }
    
    public int IncrementKernelValue(int kernelValue)
    {
		if(kernelValue + 2 > 31)
		{
			return 31;
		}
		else
		{
	    	return kernelValue + 2;			
		}
    	
    }
    
    public void DecrementKernel()
    {
    	Bundle topConfigBundle = getConfigBundleForView(0);
    	if(topConfigBundle != null) {
	    	int topKernelSize = topConfigBundle.getInt("KERNEL_SIZE");
	    	topConfigBundle.putInt("KERNEL_SIZE", DecrementKernelValue(topKernelSize));
    	}

    	Bundle bottomConfigBundle = getConfigBundleForView(1);
    	if(bottomConfigBundle != null) {
	    	int bottomKernelSize = bottomConfigBundle.getInt("KERNEL_SIZE");
	    	bottomConfigBundle.putInt("KERNEL_SIZE", DecrementKernelValue(bottomKernelSize));
    	}
		
//	    int newValue = size;
//	    StringBuilder sb = new StringBuilder();
//        //sb.append("[").append(mainView.getWidth() / 2).append("][").append(e.getX()).append("]");
//        sb.append("Decrement[").append(newValue).append("]");
//		Toast.makeText(this.getContext(), sb.toString(), Toast.LENGTH_SHORT).show();
		
	}
    
    public int DecrementKernelValue(int kernelValue)
    {
		if(kernelValue - 2 < 3)
		{
			return 3;
		}
		else
		{
	    	return kernelValue - 2;
		}
    }

    @Override
    public Class<?> getConfigClassForFilter(int filter)
    {
    	Class<?> configClass = null; 
    	switch(filter) {
    	case 0:
    		//mText = "Applying None";
    		break;
    	case 1:
    		//mText = "Applying Block";
    		configClass = BoxConfig.class;
    		break;
    	case 2:
    		//mText = "Applying Median";
    		configClass = MedianConfig.class;
    		break;
    	case 3:
    		//mText = "Applying Gaussian";
    		configClass = GaussianConfig.class;
    		break;
		default:
			//mText = "Applying Default";
			break;
    	}
    	
    	return configClass;
    }

    @Override
    public Bundle getDefaultConfigBundleForFilter(int filter)
    {
    	Bundle configBundle = null; 
    	switch(filter) {
    	case 0:
    		//mText = "Applying None";
    		break;
    	case 1:
    		//mText = "Applying Block";
    		configBundle = new Bundle();
    		configBundle.putInt("KERNEL_SIZE", kernel);
    		break;
    	case 2:
    		//mText = "Applying Median";
    		configBundle = new Bundle();
    		configBundle.putInt("KERNEL_SIZE", kernel);
    		break;
    	case 3:
    		//mText = "Applying Gaussian";
    		configBundle = new Bundle();
    		configBundle.putInt("KERNEL_SIZE", kernel);
    		configBundle.putDouble("SIGMA", sigma);
    		break;
		default:
			//mText = "Applying Default";
			break;
    	}
    	
    	return configBundle;
    }

    @Override
    protected void applyFilter(int filter, Mat sourceMat, Mat targetMat, Bundle configValues, StringBuilder text)
    {
    	int kernelValue = kernel;
    	if(configValues != null && configValues.containsKey("KERNEL_SIZE"))
    	{
    		kernelValue = configValues.getInt("KERNEL_SIZE", kernel);
    	}
    	double sigmaValue = sigma;
    	if(configValues != null && configValues.containsKey("SIGMA"))
    	{
    		sigmaValue = configValues.getDouble("SIGMA", sigma);
    	}
    	
    	Size sz = new Size(kernelValue, kernelValue); 
    	switch(filter) {
    	case 0:
    		text.append("Applying None");
    		sourceMat.copyTo(targetMat);
    		break;
    	case 1:
    		text.append("Applying Box " + kernelValue);
        	Imgproc.blur(sourceMat, targetMat, sz);
    		break;
    	case 2:
    		text.append("Applying Median " + kernelValue);
        	Imgproc.medianBlur(sourceMat, targetMat, kernelValue);
    		break;
    	case 3:
    		text.append("Applying Gaussian " + kernelValue + " Sigma:" + sigmaValue);
        	Imgproc.GaussianBlur(sourceMat, targetMat, sz, sigmaValue);
    		break;
		default:
			text.append("Applying Default");
			break;
    	}
    }
}
