package com.thingsboard.home.tts;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;


/**
 * @author 作者：张祥 on 2018/2/1 0001.
 *         邮箱：847874028@qq.com
 *         版本：v1.0
 *         功能：
 */

public class TtsOutput {

    Context context;
    private SpeechSynthesizer speechSynthesizer;
    // 默认发音人
    private String voicer = "masanye";

    /**
     * 引擎类型
     */
    private String mEngineType = SpeechConstant.TYPE_CLOUD;

    public TtsOutput(Context context) {
        super();
        this.context = context;
    }
    public SpeechSynthesizer init(InitListener initListener) {
        // 初始化合成对象
        speechSynthesizer = SpeechSynthesizer.createSynthesizer(context, initListener);

        return speechSynthesizer;
    }

    public void startPlaySuond(String soundText, SynthesizerListener synthesizerListener) {
        setParam(speechSynthesizer);
        // 设置参数
        int code = speechSynthesizer.startSpeaking(soundText, synthesizerListener);
        if (code != ErrorCode.SUCCESS) {
            Log.e("Tts", "语音合成失败,错误码: " + code);
        }
    }
    /**
     * 参数设置
     *
     * @return
     */
    private void setParam(SpeechSynthesizer speechSynthesizer) {
        // 清空参数
        speechSynthesizer.setParameter(SpeechConstant.PARAMS, null);
        // 根据合成引擎设置相应参数
        if (mEngineType.equals(SpeechConstant.TYPE_CLOUD)) {
            speechSynthesizer.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
            // 设置在线合成发音人
            speechSynthesizer.setParameter(SpeechConstant.VOICE_NAME, voicer);
            //设置合成语速
            speechSynthesizer.setParameter(SpeechConstant.SPEED, "50");
            //设置合成音调
            speechSynthesizer.setParameter(SpeechConstant.PITCH, "50");
            //设置合成音量
            speechSynthesizer.setParameter(SpeechConstant.VOLUME, "50");
        } else {
            speechSynthesizer.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_LOCAL);
            // 设置本地合成发音人 voicer为空，默认通过语记界面指定发音人。
            speechSynthesizer.setParameter(SpeechConstant.VOICE_NAME, "");
            /**
             * TODO 本地合成不设置语速、音调、音量，默认使用语记设置
             * 开发者如需自定义参数，请参考在线合成参数设置
             */
        }
        //设置播放器音频流类型
        speechSynthesizer.setParameter(SpeechConstant.STREAM_TYPE, "3");
        // 设置播放合成音频打断音乐播放，默认为true
        speechSynthesizer.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        speechSynthesizer.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        speechSynthesizer.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/tts.wav");
    }

}
