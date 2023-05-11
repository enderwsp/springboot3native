package com.example1.demo2;

import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.EurekaClientConfig;
import org.springframework.aot.hint.*;
import org.springframework.cloud.netflix.eureka.CloudEurekaClient;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClientConfiguration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.util.stream.Stream;



//@TypeHint(types = InstanceInfo.class, typeNames = "com.netflix.appinfo.InstanceInfo$PortWrapper")
//@TypeHint(
//        types = {
//                Application.class
//                , MyDataCenterInfo.class
//        },
//        access = {TypeAccess.PUBLIC_CONSTRUCTORS, TypeAccess.PUBLIC_CLASSES, TypeAccess.PUBLIC_FIELDS, TypeAccess.PUBLIC_METHODS, TypeAccess.DECLARED_CLASSES, TypeAccess.DECLARED_CONSTRUCTORS, TypeAccess.DECLARED_FIELDS, TypeAccess.DECLARED_METHODS}
//)
//@NativeHint(trigger = org.springframework.cloud.netflix.eureka.loadbalancer.EurekaLoadBalancerClientConfiguration.class
//        , extractTypesFromAttributes = "org.springframework.boot.autoconfigure.condition.ConditionalOnBean"
//        , types = {@TypeHint(
//        types = {org.springframework.cloud.loadbalancer.config.LoadBalancerZoneConfig.class
//                , org.springframework.cloud.netflix.eureka.loadbalancer.EurekaLoadBalancerProperties.class
//        })}
//)
@Component
public class TomcatWebRuntimeHints implements RuntimeHintsRegistrar {
    @Override
    public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
        hints.reflection().registerType(EurekaDiscoveryClientConfiguration.class, (typeHint) -> {
            typeHint.onReachableType(CloudEurekaClient.class);
            registerMethod(hints.reflection(), EurekaDiscoveryClientConfiguration.class, "discoveryClient", EurekaClient.class, EurekaClientConfig.class);
        });
    }

    private void registerMethod(ReflectionHints hints, Class<?> requestFactoryType,
                                String methodName, Class<?>... parameterTypes) {
        Method method = ReflectionUtils.findMethod(requestFactoryType, methodName, parameterTypes);
        if (method != null) {
            hints.registerMethod(method, ExecutableMode.INVOKE);
        }
    }
}
