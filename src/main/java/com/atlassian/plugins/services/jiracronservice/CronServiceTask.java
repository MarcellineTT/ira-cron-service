package com.atlassian.plugins.services.jiracronservice;

import java.util.Date;
import java.util.Map;
import java.util.List;

import com.atlassian.sal.api.scheduling.PluginJob;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.jql.builder.JqlClauseBuilder;
import com.atlassian.jira.jql.builder.JqlQueryBuilder;
import com.atlassian.jira.mail.Email;
import com.atlassian.sal.api.user.UserManager;
import com.atlassian.crowd.embedded.api.User;
import com.atlassian.jira.web.bean.PagerFilter;
import com.atlassian.jira.bc.issue.search.SearchService;
import com.atlassian.jira.issue.search.SearchException;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.mail.MailException;
import com.atlassian.mail.MailFactory;
//import com.atlassian.mail.queue.SingleMailQueueItem;
import com.atlassian.core.task.MultiQueueTaskManager;
import com.atlassian.mail.queue.SingleMailQueueItem;
import com.atlassian.mail.server.SMTPMailServer;

import org.apache.log4j.Logger;

import twitter4j.Query;
import twitter4j.Twitter;
import twitter4j.TwitterException;


public class CronServiceTask implements PluginJob{

	public static final String MAIL = "mail";
	private MultiQueueTaskManager taskManager;
	private SearchService searchService;
//	private final SMTPMailServer  mailServer = MailFactory.getServerManager().getDefaultSMTPMailServer();
	private final Logger logger = Logger.getLogger(CronServiceTask.class);
	

	    /**
	     * Executes this job.
	     *
	     * @param jobDataMap any data the job needs to execute. Changes to this data will be remembered between executions.
	     */
	    public void execute(Map<String, Object> jobDataMap) {

	    	Email email = new Email("denniselite@live.com");
	    	email.setBody("Some body...");
	    	email.setMimeType("text/plain");
	    	email.setSubject("Some Subject");
	    	SingleMailQueueItem item = new SingleMailQueueItem(email);
	    	final MailServiceImpl mailService = new MailServiceImpl(taskManager);
	    	mailService.sendEmail(item);
	        final CronServiceImpl monitor = (CronServiceImpl)jobDataMap.get(CronServiceImpl.KEY);
	        assert monitor != null;
//	        try {
	        	final List<Issue> issues = this.getIssues();
//	            final Twitter twitter = new Twitter();
//	            monitor.setTweets(twitter.search(new Query(monitor.getQuery())).getTweets());
	        	monitor.setIssues(issues);
	            monitor.setLastRun(new Date());
//	        } catch (TwitterException te) {
//	            logger.error("Error talking to Twitter: " + te.getMessage(), te);
//	        }
	    }
	    
	    private List<Issue> getIssues() {
	    	User user = ComponentAccessor.getJiraAuthenticationContext().getUser().getDirectoryUser();;
			// search issues
			// The search interface requires JQL clause... so let's build one
			JqlClauseBuilder jqlClauseBuilder = JqlQueryBuilder.newClauseBuilder();
			// Our JQL clause is simple project="TUTORIAL"
			com.atlassian.query.Query query = jqlClauseBuilder.project("TestProject").buildQuery();
			// A page filter is used to provide pagination. Let's use an unlimited filter to
			// to bypass pagination.
			PagerFilter pagerFilter = PagerFilter.getUnlimitedFilter();
			com.atlassian.jira.issue.search.SearchResults searchResults = null;
			try {
				// Perform search results
				searchResults = searchService.search(user, query, pagerFilter);
			} catch (SearchException e) {
				e.printStackTrace();
			}
			// return the results
			return searchResults.getIssues();
	    }

}