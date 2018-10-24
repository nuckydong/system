package com.gopher.system.model;

import java.io.Serializable;
import java.util.Date;

public class BaseModel implements Serializable {

    private static final long serialVersionUID = 1309753304387753614L;
    private int id;
    private Date  createTime;
    private Date  updateTime;
    private int createUser;
    private int updateUser;


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public Date getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public Date getUpdateTime() {
		return updateTime;
	}


	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}


	public int getCreateUser() {
		return createUser;
	}


	public void setCreateUser(int createUser) {
		this.createUser = createUser;
	}


	public int getUpdateUser() {
		return updateUser;
	}


	public void setUpdateUser(int updateUser) {
		this.updateUser = updateUser;
	}


	@Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BaseModel{");
        sb.append("id=").append(id);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", createUser=").append(createUser);
        sb.append(", updateUser=").append(updateUser);
        sb.append('}');
        return sb.toString();
    }
}
