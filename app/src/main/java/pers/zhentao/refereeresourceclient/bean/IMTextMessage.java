package pers.zhentao.refereeresourceclient.bean;

import java.util.Map;

/**
 * Created by 张镇涛 on 2017/1/4.
 */

public class IMTextMessage extends IMMessage{
    private String content;
    private Map<String,Object> extraMap;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Map<String, Object> getExtraMap() {
        return extraMap;
    }

    public void setExtraMap(Map<String, Object> extraMap) {
        this.extraMap = extraMap;
    }
}
