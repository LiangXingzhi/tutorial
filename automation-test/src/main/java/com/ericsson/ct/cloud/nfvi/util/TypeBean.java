package com.ericsson.ct.cloud.nfvi.util;

public class TypeBean {
    private String typename;
    private String url;
    private String oidPattern;
    private String varPattern;

    public String getOidPattern() {
        return oidPattern;
    }

    public void setOidPattern(String oidPattern) {
        this.oidPattern = oidPattern;
    }

    public String getVarPattern() {
        return varPattern;
    }

    public void setVarPattern(String varPattern) {
        this.varPattern = varPattern;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
