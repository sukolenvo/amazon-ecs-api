package de.malkusch.amazon.ecs.test;

import com.amazon.wsdl.AWSECommerceServicePortType;
import de.malkusch.amazon.ecs.throttle.ThrottledAWSECommerseServicePort;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.xml.ws.BindingProvider;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@Slf4j
public class ThrottleTest {

    @Test
    public void testThrottle() throws Exception {
        int throttle = 1000;
        AWSECommerceServicePortType proxy = (AWSECommerceServicePortType) Proxy.newProxyInstance(ThrottleTest.class.getClassLoader(),
                new Class[]{AWSECommerceServicePortType.class, BindingProvider.class}, new InvocationHandler() {

                    private long lastInvocation;

                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if (System.currentTimeMillis() - lastInvocation < throttle) {
                            throw new RuntimeException("Request are executed too fast");
                        }
                        lastInvocation = System.currentTimeMillis();
                        return null;
                    }
                });
        ThrottledAWSECommerseServicePort throttled = new ThrottledAWSECommerseServicePort(proxy, throttle);
        throttled.itemSearch(null, null,null, null, null, null, null, null, null);
        throttled.itemSearch(null, null,null, null, null, null, null, null, null);
        throttled.itemSearch(null, null,null, null, null, null, null, null, null);
        Thread.sleep(1500);
        throttled.itemSearch(null, null,null, null, null, null, null, null, null);
        throttled.itemSearch(null, null,null, null, null, null, null, null, null);


    }
}
