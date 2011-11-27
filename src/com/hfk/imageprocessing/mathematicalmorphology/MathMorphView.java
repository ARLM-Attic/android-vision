package com.hfk.imageprocessing.mathematicalmorphology;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;

import com.hfk.imageprocessing.ImageProcessingView;
import com.hfk.imageprocessing.R;
//import com.hfk.imageprocessing.blur.GaussianConfig;
//import com.hfk.imageprocessing.blur.MedianConfig;
import com.hfk.imageprocessing.gesture.ActionGesture;

public class MathMorphView extends ImageProcessingView implements ActionGesture {
	int kernel = 5;
	double sigma = 2.0;
	
    public MathMorphView(Context context) {
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
    		title = res.getString(R.string.filters_mathmorph_title);        		
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
    	return res.getStringArray(R.array.filters_mathmorph_items);
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

    	Bundle bottomConfigBundle = getConfigBundleForView(0);
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
//    	switch(filter) {
//    	case 0:
//    		//mText = "Applying None";
//    		break;
//    	case 1:
//    		//mText = "Applying Block";
//    		break;
//    	case 2:
//    		//mText = "Applying Median";
//    		configClass = MedianConfig.class;
//    		break;
//    	case 3:
//    		//mText = "Applying Gaussian";
//    		configClass = GaussianConfig.class;
//    		break;
//		default:
//			//mText = "Applying Default";
//			break;
//    	}
    	
    	return configClass;
    }

    @Override
    public Bundle getDefaultConfigBundleForFilter(int filter)
    {
    	Bundle configBundle = null; 
//    	switch(filter) {
//    	case 0:
//    		//mText = "Applying None";
//    		break;
//    	case 1:
//    		//mText = "Applying Block";
//    		configBundle = new Bundle();
//    		configBundle.putInt("KERNEL_SIZE", kernel);
//    		break;
//    	case 2:
//    		//mText = "Applying Median";
//    		configBundle = new Bundle();
//    		configBundle.putInt("KERNEL_SIZE", kernel);
//    		break;
//    	case 3:
//    		//mText = "Applying Gaussian";
//    		configBundle = new Bundle();
//    		configBundle.putInt("KERNEL_SIZE", kernel);
//    		configBundle.putDouble("SIGMA", sigma);
//    		break;
//		default:
//			//mText = "Applying Default";
//			break;
//    	}
    	
    	return configBundle;
    }

    @Override
    protected void applyFilter(int filter, Mat sourceMat, Mat targetMat, Bundle configValues, StringBuilder text)
    {
//    	int kernelValue = kernel;
//    	if(configValues != null && configValues.containsKey("KERNEL_SIZE"))
//    	{
//    		kernelValue = configValues.getInt("KERNEL_SIZE", kernel);
//    	}
//    	double sigmaValue = sigma;
//    	if(configValues != null && configValues.containsKey("SIGMA"))
//    	{
//    		sigmaValue = configValues.getDouble("SIGMA", sigma);
//    	}
//    	
//    	Size sz = new Size(kernelValue, kernelValue); 
    	switch(filter) {
    	case 0:
    		text.append("Applying None");
    		sourceMat.copyTo(targetMat);
    		break;
    	case 1:
    		text.append("Applying Erosion"); // " + kernelValue);
    		Imgproc.threshold(sourceMat, targetMat, 128, 255, Imgproc.THRESH_BINARY);
        	Imgproc.erode(targetMat, targetMat, new Mat());
    		break;
    	case 2:
    		text.append("Applying Dilation"); // " + kernelValue);
    		Imgproc.threshold(sourceMat, targetMat, 128, 255, Imgproc.THRESH_BINARY);
        	Imgproc.dilate(targetMat, targetMat, new Mat());
    		break;
    	case 3:
    		text.append("Applying Opening"); // " + kernelValue);
    		Imgproc.threshold(sourceMat, targetMat, 128, 255, Imgproc.THRESH_BINARY);
        	Imgproc.morphologyEx(targetMat, targetMat, Imgproc.MORPH_OPEN, new Mat());
    		break;
    	case 4:
    		text.append("Applying Closing"); // " + kernelValue);
    		Imgproc.threshold(sourceMat, targetMat, 128, 255, Imgproc.THRESH_BINARY);
        	Imgproc.morphologyEx(targetMat, targetMat, Imgproc.MORPH_CLOSE, new Mat());
    		break;
    	case 5:
    		text.append("Applying Blackhat"); // " + kernelValue);
    		Imgproc.threshold(sourceMat, targetMat, 128, 255, Imgproc.THRESH_BINARY);
        	Imgproc.morphologyEx(targetMat, targetMat, Imgproc.MORPH_BLACKHAT, new Mat());
    		break;
    	case 6:
    		text.append("Applying Tophat"); // " + kernelValue);
    		Imgproc.threshold(sourceMat, targetMat, 128, 255, Imgproc.THRESH_BINARY);
        	Imgproc.morphologyEx(targetMat, targetMat, Imgproc.MORPH_TOPHAT, new Mat());
    		break;
    	case 7:
    		text.append("Applying Gradient"); // " + kernelValue);
    		Imgproc.threshold(sourceMat, targetMat, 128, 255, Imgproc.THRESH_BINARY);
        	Imgproc.morphologyEx(targetMat, targetMat, Imgproc.MORPH_GRADIENT, new Mat());
    		break;
		default:
			text.append("Applying Default");
			break;
      }
    }
}
