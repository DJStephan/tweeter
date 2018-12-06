package com.cooksys.ftd.socialmediaassessmentDJStephan.entities;

public class Context {
	private Tweet target;
	private Tweet[] before;
	private Tweet[] after;
	
	public Context(Tweet target, Tweet[] before, Tweet[] after) {
		this.target = target;
		this.before = before;
		this.after = after;
	}

	public Tweet getTarget() {
		return target;
	}

	public void setTarget(Tweet target) {
		this.target = target;
	}

	public Tweet[] getBefore() {
		return before;
	}

	public void setBefore(Tweet[] before) {
		this.before = before;
	}

	public Tweet[] getAfter() {
		return after;
	}

	public void setAfter(Tweet[] after) {
		this.after = after;
	}
	
	
}
