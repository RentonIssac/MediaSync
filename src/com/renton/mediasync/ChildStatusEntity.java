package com.renton.mediasync;

/**
 * ����Itemʵ����
 * 
 * @author zihao
 * 
 */
public class ChildStatusEntity {
	/** Ԥ�����ʱ�� **/
	private String contentText;
	/** �Ƿ������ **/
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
