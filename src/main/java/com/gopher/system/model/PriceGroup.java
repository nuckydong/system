package com.gopher.system.model;
/**
 * 定价组
 * @author nucky
 *
 */
public class PriceGroup extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8558405145491314372L;
	// 定价名称
	private String name;
	// 定价号
	private String number;
	// 备注
	private String remark;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


}
