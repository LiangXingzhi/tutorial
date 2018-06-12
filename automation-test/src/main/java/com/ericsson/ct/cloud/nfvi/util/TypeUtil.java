package com.ericsson.ct.cloud.nfvi.util;

import java.util.List;
public class TypeUtil {
    public static String mapIp2Type(String ip,List<TypeBean> typeBeanList){
        for (int i=0;i<typeBeanList.size();i++) {
            if (ip.equals(typeBeanList.get(i).getUrl())) {
                return typeBeanList.get(i).getTypename();
            }
        }
        return "Unknown Type";
    }
}