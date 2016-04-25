package com.renton.mediasync;

/**
 * 二级Item实体类
 * 
 * @author zihao
 * 
 */
public class ChildStatusEntity {
	/** 预计完成时间 **/
	private String contentText;
	/** 是否已完成 **/
	private boolean isfinished;
    private int imgSrc;
    
	public int getImgSrc() {
		return imgSrc;
	}

	public void setImgSrc(int imgSrc) {
		this.imgSrc = imgSrc;
	}

	
	public String getContentText() {
		return contentText;
	}

	public void setContentText(String contentText) {
		this.contentText = contentText;
	}

	public boolean isIsfinished() {
		return isfinished;
	}

	public void setIsfinished(boolean isfinished) {
		this.isfinished = isfinished;
	}

}
