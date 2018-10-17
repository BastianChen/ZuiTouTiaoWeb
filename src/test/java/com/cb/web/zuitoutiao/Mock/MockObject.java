package com.cb.web.zuitoutiao.Mock;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * @Description:
 * @author: YFZX-CB-1784 ChenBen
 * @create: 2018-10-17 11:22
 **/
public class MockObject {
    @Test
    public void MockTest() {
        List list = mock(List.class);
        list.add("aaa");
        when(list.get(0)).thenReturn("hello world");

        System.out.println(list.get(0));

        System.out.println(list.size());
    }

    @Test
    public void SpyMockTest() {
        /* 创建真实对象 */
        List list = new LinkedList();
        List spy = spy(list);

        spy.add("hello");

        when(spy.get(0)).thenReturn("hello world");

        System.out.println(spy.get(0));
        System.out.println(spy.size());
    }

    @Test
    public void spyTest() {
        List list = new LinkedList();
        List spy = spy(list);
        // optionally, you can stub out some methods:
        when(spy.size()).thenReturn(100);
        // using the spy calls real methods
        spy.add("one");
        spy.add("two");
        // prints "one" - the first element of a list
        System.out.println(spy.get(0));
        // size() method was stubbed - 100 is printed
        System.out.println(spy.size());
        // optionally, you can verify
        verify(spy).add("one");
        verify(spy).add("two");
    }
}
