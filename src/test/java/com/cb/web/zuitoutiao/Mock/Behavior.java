package com.cb.web.zuitoutiao.Mock;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;

import java.util.List;

import static org.mockito.Mockito.*;

/**
 * @Description: Mock 对象的行为验证
 * @author: YFZX-CB-1784 ChenBen
 * @create: 2018-10-17 11:06
 **/
public class Behavior {
    @Test
    public void behaviorCheck() {
        List mock1 = mock(List.class);
        List mock2 = mock(List.class);

        /* 设置预期 */
        when(mock1.get(0)).thenReturn("hello world");
        when(mock1.get(1)).thenReturn("hello world");
        when(mock2.get(0)).thenReturn("hello world");
        mock1.get(0);

        /* 验证方法调用一次 */
        verify(mock1).get(0);
        mock1.get(0);
        /* 验证方法调用两次 */
        verify(mock1, times(2)).get(0);
        /* 验证方法从未被调用过 */
        verify(mock2, never()).get(0);
        /* 验证方法 100 毫秒内调用两次 */
        verify(mock1, timeout(100).times(2)).get(anyInt());

        /* 设置方法调用顺序 */
        InOrder inOrder = inOrder(mock1, mock2);
        inOrder.verify(mock1, times(2)).get(0);
        inOrder.verify(mock2, never()).get(1);

        /*  查询是否存在被调用，但未被 verify 验证的方法 */
        verifyNoMoreInteractions(mock1, mock2);
        /* 验证 Mock 对象被调用 */
        verifyZeroInteractions(mock1, mock2);

        /* 参数捕获器 */
        ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(mock1, times(2)).get(argumentCaptor.capture());
        System.out.println("argument:" + argumentCaptor.getValue());
    }
}
