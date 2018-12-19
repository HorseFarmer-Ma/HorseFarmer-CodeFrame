package com.meizu.code.frame.base.frame.common.log;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.meizu.code.frame.utils.CodeFrameStaticUtils;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

class LoggerPrinter implements Printer {

    /**
     * Provides one-time used tag for the log message
     */
    private final ThreadLocal<String> mLocalTag = new ThreadLocal<>();
    private FormatStrategy mFormatStrategy;
    private boolean mIsLogEnable = true;

    public LoggerPrinter() {
        mFormatStrategy = LoggerFormatStrategy.newBuilder().build();
    }

    @Override
    public Printer t(String tag) {
        if (tag != null) {
            mLocalTag.set(tag);
        }
        return this;
    }

    @Override
    public void d(String message, Object... args) {
        log(ILogTool.DEBUG, null, message, args);
    }

    @Override
    public void d(Object object) {
        log(ILogTool.DEBUG, null, CodeFrameStaticUtils.toString(object));
    }

    @Override
    public void e(String message, Object... args) {
        e(null, message, args);
    }

    @Override
    public void e(Throwable throwable, String message, Object... args) {
        log(ILogTool.ERROR, throwable, message, args);
    }

    @Override
    public void w(String message, Object... args) {
        log(ILogTool.WARN, null, message, args);
    }

    @Override
    public void i(String message, Object... args) {
        log(ILogTool.INFO, null, message, args);
    }

    @Override
    public void v(String message, Object... args) {
        log(ILogTool.VERBOSE, null, message, args);
    }

    @Override
    public void wtf(String message, Object... args) {
        log(ILogTool.ASSERT, null, message, args);
    }

    @Override
    public void json(String json) {
        if (TextUtils.isEmpty(json)) {
            d("Empty/Null json content");
            return;
        }
        json = json.trim();
        try {
            JSONObject jsonObject = JSONObject.parseObject(json);
            String message = jsonObject.toJSONString();
            d(message);
        } catch (Exception e) {
            d(json);
        }
    }

    @Override
    public void xml(String xml) {
        if (TextUtils.isEmpty(xml)) {
            d("Empty/Null xml content");
            return;
        }
        try {
            Source xmlInput = new StreamSource(new StringReader(xml));
            StreamResult xmlOutput = new StreamResult(new StringWriter());
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(xmlInput, xmlOutput);
            d(xmlOutput.getWriter().toString().replaceFirst(">", ">\n"));
        } catch (TransformerException e) {
            e("Invalid xml");
        }
    }

    @Override
    public synchronized void log(int priority, String tag, String message, Throwable throwable) {
        if (throwable != null && message != null) {
            message += " : " + Log.getStackTraceString(throwable);
        }
        if (throwable != null && message == null) {
            message = Log.getStackTraceString(throwable);
        }
        if (TextUtils.isEmpty(message)) {
            message = "Empty/NULL log message";
        }

        if (mFormatStrategy != null) {
            mFormatStrategy.log(priority, tag, message);
        }
    }

    @Override
    public void addFormatStrategy(FormatStrategy formatStrategy) {
        mFormatStrategy = formatStrategy;
    }

    @Override
    public void setLogEnable(boolean isLogEnable) {
        mIsLogEnable = isLogEnable;
    }

    @Override
    public boolean isLogEnable() {
        return mIsLogEnable;
    }

    /**
     * This method is synchronized in order to avoid messy of logs' order.
     */
    private synchronized void log(int priority, Throwable throwable, String msg, Object... args) {
        if (!mIsLogEnable) return;
        String tag = getTag();
        String message = createMessage(msg, args);
        log(priority, tag, message, throwable);
    }

    /**
     * 保存在ThreadLocal中,每次取出后清除,只使用一次
     *
     * @return the appropriate tag based on local or global
     */
    private String getTag() {
        String tag = mLocalTag.get();
        if (tag != null) {
            mLocalTag.remove();
            return tag;
        }
        return null;
    }

    private String createMessage(String message, Object... args) {
        return args == null || args.length == 0 ? message : String.format(message, args);
    }
}