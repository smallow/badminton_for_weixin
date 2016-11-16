package com.smallow.weixin.message.resp;

/**
 * Created by smallow on 16/8/19.
 */
public class VideoMessage extends BaseMessage {
    // 视频
    private com.smallow.weixin.message.resp.Video Video;

    public com.smallow.weixin.message.resp.Video getVideo() {
        return Video;
    }

    public void setVideo(com.smallow.weixin.message.resp.Video video) {
        Video = video;
    }
}
