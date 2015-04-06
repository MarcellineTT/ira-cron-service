package com.atlassian.plugins.services.jiracronservice;

public interface CronService {
	
	public void reschedule(long interval);
    
}