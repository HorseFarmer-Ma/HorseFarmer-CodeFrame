package com.meizu.code.frame.base.frame.common.log;

interface ILogTool {

    int VERBOSE = 1;
    int DEBUG = 2;
    int INFO = 3;
    int WARN = 4;
    int ERROR = 5;
    int ASSERT = 6;

    void d(String tag, String message);

    void e(String tag, String message);

    void w(String tag, String message);

    void i(String tag, String message);

    void v(String tag, String message);

    void wtf(String tag, String message);
}