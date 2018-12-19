package com.meizu.code.app.example.blockitem;

import com.meizu.code.frame.base.model.delegate.blockitem.DelegateBlockItem;
import com.meizu.code.app.example.blocklayout.CodeFrameBlockLayout;
import com.meizu.code.greendao.bean.BookBean;

/**
 * item数据
 * <p>
 * Created by mxm on 24/02/18.
 */
public class CodeFrameBlockItem extends DelegateBlockItem<BookBean.BooksBean> {

    private String mTitle;
    private String mImageUrl;

    public CodeFrameBlockItem(BookBean.BooksBean data) {
        super(data);
        mTitle = data.getTitle();
        mImageUrl = data.getImage();
    }

    @Override
    public Class getBlockLayoutClazz() {
        return CodeFrameBlockLayout.class;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getImageUrl() {
        return mImageUrl;
    }
}
