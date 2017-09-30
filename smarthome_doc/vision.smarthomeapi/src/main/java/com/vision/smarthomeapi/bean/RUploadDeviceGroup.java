package com.vision.smarthomeapi.bean;


public class RUploadDeviceGroup extends Bean{
	
	

	
	public RUploadDeviceGroup(String status, String statusMsg, String urlOrigin) {
		// TODO Auto-generated constructor stub
		super();
		
		this.status = status;
		this.statusMsg = statusMsg;
		this.urlOrigin = urlOrigin;
	
		
	}


    public int mode() {

        int rValue = super.mode();
        if (rValue != Bean.ERROR){
            return rValue;
        }
        switch (status){

        }
        return Bean.ERROR;
    }

	
	

}
