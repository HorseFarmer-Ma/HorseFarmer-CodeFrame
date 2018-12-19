package com.meizu.code.app.example.blocklayout;

import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.meizu.code.app.R;
import com.meizu.code.app.example.blockitem.CodeFrameBlockItem;
import com.meizu.code.frame.base.frame.glide.GlideApp;
import com.meizu.code.frame.base.model.delegate.blocklayout.DelegateBlockLayout;

/**
 * 布局文件
 * <p>
 * Created by mxm on 24/02/18.
 */
public class CodeFrameBlockLayout extends DelegateBlockLayout<CodeFrameBlockItem> {
    private static final String TAG = "CodeFrameBlockLayout";

    private ViewHolder mViewHolder;

    @Override
    protected View createView(@Nullable ViewGroup rootView) {
        return inflate(R.layout.code_frame_layout, rootView);
    }

    @Override
    protected void updateView(CodeFrameBlockItem item) {
        if (mViewHolder == null) {
            mViewHolder = new ViewHolder(mView);
        }
        String imageUrl = item.getImageUrl();
        if (imageUrl.contains(".gif")) {
            GlideApp.with(getContext())
                    .asGif()
                    .load(item.getImageUrl())
                    .addListener(new RequestListener<GifDrawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .into(mViewHolder.mBookImg);
        } else {
            GlideApp.with(getContext())
                    .asBitmap()
                    .load(item.getImageUrl())
                    .transition(BitmapTransitionOptions.withCrossFade())
                    .into(mViewHolder.mBookImg);
        }
    }

    private static class ViewHolder {
        final ImageView mBookImg;
        ViewHolder(View view) {
            mBookImg = view.findViewById(R.id.book_img);
        }
    }
}
