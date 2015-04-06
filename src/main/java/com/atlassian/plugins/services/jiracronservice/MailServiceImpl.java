package com.atlassian.plugins.services.jiracronservice;

import com.atlassian.core.task.MultiQueueTaskManager;
import com.atlassian.mail.queue.MailQueueItem;

public class MailServiceImpl implements MailService
{
    public static final String MAIL = "mail";
    private final MultiQueueTaskManager taskManager;

    public MailServiceImpl(MultiQueueTaskManager taskManager)
    {
        this.taskManager = taskManager;
    }

    /**
     * This will use a MultiQueueTaskManager to add add the mailQueueItem to a queue
     * to be sent
     *
     * @param mailQueueItem the item to send
     */
    @Override
    public void sendEmail(MailQueueItem mailQueueItem)
    {
        taskManager.addTask(MAIL, mailQueueItem);
    }
}