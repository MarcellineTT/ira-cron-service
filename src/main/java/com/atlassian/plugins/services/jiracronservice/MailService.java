package com.atlassian.plugins.services.jiracronservice;

import com.atlassian.mail.queue.MailQueueItem;

public interface MailService
{
    void sendEmail(MailQueueItem mailQueueItem);
}