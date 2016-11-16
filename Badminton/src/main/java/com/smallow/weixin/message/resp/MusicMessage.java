package com.smallow.weixin.message.resp;

/**
 * Created by smallow on 16/8/19.
 */
public class MusicMessage extends BaseMessage {
    // 音乐
    private com.smallow.weixin.message.resp.Music Music;

    public com.smallow.weixin.message.resp.Music getMusic() {
        return Music;
    }

    public void setMusic(com.smallow.weixin.message.resp.Music music) {
        Music = music;
    }
}
