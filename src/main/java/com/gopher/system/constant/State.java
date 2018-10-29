package com.gopher.system.constant;

public enum State {
	
	VALID(0,"有效"),
	INVALID(-1,"删除或无效");
	
	private String description;
	private int state;
	
	private State(int state, String description) {
		this.state = state;
		this.description = description;
	}

	public static String getMsg(int state) {
		for (State s: State.values()) {
			if (s.getState() == state) {
				return s.description;
			}
		}
		return null;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
	
	
	
}
