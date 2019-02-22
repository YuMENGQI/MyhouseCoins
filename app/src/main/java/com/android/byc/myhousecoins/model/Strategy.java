package com.android.byc.myhousecoins.model;

import android.content.Context;

import io.reactivex.functions.Consumer;

/**
 * @author yu
 * @version 1.0
 * @date 2019/2/20 14:00
 * @description
 */
public class Strategy {
    private CharSequence buttonTitle;
    private Consumer<Context> consumer;

    public Strategy(CharSequence buttonTitle, Consumer<Context> consumer) {
        this.buttonTitle = buttonTitle;
        this.consumer = consumer;
    }

    public CharSequence getButtonTitle() {
        return buttonTitle;
    }

    public void setButtonTitle(CharSequence buttonTitle) {
        this.buttonTitle = buttonTitle;
    }

    public Consumer<Context> getConsumer() {
        return consumer;
    }

    public void setConsumer(Consumer<Context> consumer) {
        this.consumer = consumer;
    }
    public void act(Context context) {
        try {
            consumer.accept(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
