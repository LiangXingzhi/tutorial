package com.ericsson.ct.cloud.nfvi.config;

import com.ericsson.ct.cloud.nfvi.util.TypeBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "mapping")
public class MappingConfig {
    private List<TypeBean> typebeans=new ArrayList<TypeBean>();

    public List<TypeBean> getTypebeans() {
        return typebeans;
    }

    public void setTypebeans(List<TypeBean> typebeans) {
        this.typebeans = typebeans;
    }
}
