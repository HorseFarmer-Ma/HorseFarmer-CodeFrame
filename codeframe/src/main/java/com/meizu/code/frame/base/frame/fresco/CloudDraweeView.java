/*
 * ************************************************************
 * Class：CloudDraweeView.java  Module：codeframe  Project：CodeFrame
 *
 * Current modified time：2018-11-26 16:07:21
 * Last modified time：2018-11-26 14:28:15
 *
 * Author：maxueming
 *
 * Copyright (c) 2017 - 2018
 * ***********************************************************
 */

package com.meizu.code.frame.base.frame.fresco;

/*
 * This file provided by Facebook is for non-commercial testing and evaluation
 * purposes only.  Facebook reserves all rights not expressly granted.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * FACEBOOK BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;

import com.facebook.drawee.controller.AbstractDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.SimpleDraweeControllerBuilder;
import com.facebook.drawee.view.SimpleDraweeView;

public class CloudDraweeView extends SimpleDraweeView {
    static {
        FrescoManager.getInstance(); // Ensure the freso has been inited before user image widget
    }

    private ControllerListener<Object> mListener;
    private Drawable mPlaceHolder;

    public CloudDraweeView(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
        init();
    }

    public CloudDraweeView(Context context) {
        super(context);
        init();
    }

    public CloudDraweeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CloudDraweeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private boolean isSuccess = false;

    public boolean getIsSuccess() {
        return this.isSuccess;
    }

    private void init() {
        mListener = new BaseControllerListener<Object>() {
            @Override
            public void onSubmit(String id, Object callerContext) {
            }

            @Override
            public void onFinalImageSet(String id, Object imageInfo, Animatable animatable) {
                isSuccess = true;
                if (mLoadListener != null) {
                    mLoadListener.onSuccess(imageInfo);
                }
            }

            @Override
            public void onFailure(String id, Throwable throwable) {
                isSuccess = false;
                if (mLoadListener != null) {
                    mLoadListener.onFailure(throwable);
                }
            }

            @Override
            public void onRelease(String id) {
            }
        };
    }

    @Override
    public void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public void setImageURI(Uri uri, Object callerContext) {
        SimpleDraweeControllerBuilder controllerBuilder = getControllerBuilder()
                .setUri(uri)
                .setCallerContext(callerContext)
                .setOldController(getController());
        if (controllerBuilder instanceof AbstractDraweeControllerBuilder) {
            ((AbstractDraweeControllerBuilder<?, ?, ?, ?>) controllerBuilder)
                    .setControllerListener(mListener);
        }
        setController(controllerBuilder.build());
    }

    public ControllerListener<Object> getListener() {
        return mListener;
    }

    public void setPlaceHolder(Drawable placeHolder) {
        this.mPlaceHolder = placeHolder;
        GenericDraweeHierarchy gdh = getHierarchy();
        gdh.setPlaceholderImage(placeHolder);
    }

    public Drawable getPlaceHolder() {
        return this.mPlaceHolder;
    }

    private int mRoundBorderWidth;
    private int mRoundBorderColor;

    public void setRoundBorder(int roundBorderColor, int roundBorderWidth) {
        mRoundBorderWidth = roundBorderWidth;
        mRoundBorderColor = roundBorderColor;
        updateRoundBorderColorWidth();
    }

    private void updateRoundBorderColorWidth() {
        if (mRoundBorderWidth > 0) {
            RoundingParams roundingParams = getHierarchy().getRoundingParams();
            if (roundingParams != null) {
                roundingParams.setBorder(mRoundBorderColor, mRoundBorderWidth);
                getHierarchy().setRoundingParams(roundingParams);
            }
        }
    }

    private OnImageLoadListener mLoadListener;

    public void setLoadListener(OnImageLoadListener loadListener) {
        mLoadListener = loadListener;
    }

    public interface OnImageLoadListener {
        void onSuccess(Object imageInfo);

        void onFailure(Throwable throwable);
    }
}
