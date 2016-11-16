package com.smallow.weixin.message.resp;

/**
 * Created by smallow on 16/8/19.
 */
public class VoiceMessage extends BaseMessage {
    // 语音
    private com.smallow.weixin.message.resp.Voice Voice;

    public com.smallow.weixin.message.resp.Voice getVoice() {
        return Voice;
    }

    public void setVoice(com.smallow.weixin.message.resp.Voice voice) {
        Voice = voice;
    }
}
