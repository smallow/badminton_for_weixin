package com.smallow.weixin.message.req;

/**
 * Created by smallow on 16/8/19.
 */
public class TextMessage extends BaseMessage {

    // 消息内容
    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
