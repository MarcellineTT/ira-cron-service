package ut.com.atlassian.plugins.services;

import org.junit.Test;
import com.atlassian.plugins.services.MyPluginComponent;
import com.atlassian.plugins.services.MyPluginComponentImpl;

import static org.junit.Assert.assertEquals;

public class MyComponentUnitTest
{
    @Test
    public void testMyName()
    {
        MyPluginComponent component = new MyPluginComponentImpl(null);
        assertEquals("names do not match!", "myComponent",component.getName());
    }
}