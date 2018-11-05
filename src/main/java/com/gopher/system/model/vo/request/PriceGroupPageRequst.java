package com.gopher.system.model.vo.request;

import com.gopher.system.model.Page;
import com.gopher.system.model.PriceGroup;

public class PriceGroupPageRequst extends Page<PriceGroup>{
	
    private String name;
    
    private String number;
    
    private int skip;

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
