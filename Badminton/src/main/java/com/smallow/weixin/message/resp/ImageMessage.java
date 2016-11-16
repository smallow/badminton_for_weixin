package com.smallow.weixin.message.resp;

/**
 * Created by smallow on 16/8/19.
 */
public class ImageMessage extends BaseMessage {

    private com.smallow.weixin.message.resp.Image Image;

    public com.smallow.weixin.message.resp.Image getImage() {
        return Image;
    }

    public void setImage(com.smallow.weixin.message.resp.Image image) {
        Image = image;
    }
}
