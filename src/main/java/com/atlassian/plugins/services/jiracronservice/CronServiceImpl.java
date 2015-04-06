package com.atlassian.plugins.services.jiracronservice;

import com.atlassian.sal.api.lifecycle.LifecycleAware;
import com.atlassian.sal.api.scheduling.PluginScheduler;
import com.atlassian.jira.issue.Issue;

import org.apache.log4j.Logger;
//import twitter4j.Tweet;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class CronServiceImpl implements CronService, LifecycleAware {

	 /* package */ static final String KEY = CronServiceImpl.class.getName() + ":instance";
	    private static final String JOB_NAME = CronServiceImpl.class.getName() + ":job";

//	    private final Logger logger = Logger.getLogger(CronServiceImpl.class);
	    private final PluginScheduler pluginScheduler;  // provided by SAL

	    private String query = "Atlassian"; // default Twitter search
	    private long interval = 5000L;      // default job interval (5 sec)
	    private List<Issue> issues;         // results of the last search
	    private Date lastRun = null;        // time when the last search returned

	    public CronServiceImpl(PluginScheduler pluginScheduler) {
	        this.pluginScheduler = pluginScheduler;
	    }

	    // declared by LifecycleAware
	    public void onStart() {
	        reschedule(interval);
	    }

	    public void reschedule(long interval) {
//	        this.query = query;
	        this.interval = interval;
	        
	        pluginScheduler.scheduleJob(
	                JOB_NAME,                   // unique name of the job
	                CronServiceTask.class,     // class of the job
	                new HashMap<String,Object>() {{
	                    put(KEY, CronServiceImpl.this);
	                }},                         // data that needs to be passed to the job
	                new Date(),                 // the time the job is to start
	                interval);                  // interval between repeats, in milliseconds
	        System.out.println("Issues search task scheduled to run every "+interval);
	    }

	    public String getQuery() {
	        return query;
	    }

	    /* package */ void setIssues(List<Issue> issues) {
	        this.issues = issues;
	    }

	    /* package */ void setLastRun(Date lastRun) {
	        this.lastRun = lastRun;
	    }
}