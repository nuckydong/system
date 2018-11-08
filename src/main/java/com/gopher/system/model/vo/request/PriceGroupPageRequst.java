package com.gopher.system.model.vo.request;

import com.gopher.system.model.PriceGroup;
import com.gopher.system.util.Page;

public class PriceGroupPageRequst extends Page<PriceGroup>{
	
    private String name;
    
    private String number;
    
    private int skip;
    
    private int state;

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getSkip() {
		return skip;
	}

	public void setSkip(int skip) {
		this.skip = skip;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
    
    
}
