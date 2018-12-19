package com.meizu.code.frame.base.frame.common.log;

import android.text.TextUtils;

public class LoggerFormatStrategy implements FormatStrategy {

    /**
     * Android's max limit for a log entry is ~4076 bytes,
     * so 4000 bytes is used as chunk size since default charset
     * is UTF-8
     */
    private static final int CHUNK_SIZE = 4000;

    /**
     * 起始搜寻方法调用堆栈行数,节省搜索时间
     * The minimum stack trace index, starts at this class after two native calls.
     */
    private static final int MIN_STACK_OFFSET = 5;

    /**
     * Drawing toolbox
     */
    private static final char TOP_LEFT_CORNER = '┌';
    private static final char BOTTOM_LEFT_CORNER = '└';
    private static final char MIDDLE_CORNER = '├';
    private static final char HORIZONTAL_LINE = '│';
    private static final String DOUBLE_DIVIDER = "────────────────────────────────────────────────────────";
    private static final String SINGLE_DIVIDER = "┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄";
    private static final String TOP_BORDER = TOP_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
    private static final String BOTTOM_BORDER = BOTTOM_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
    private static final String MIDDLE_BORDER = MIDDLE_CORNER + SINGLE_DIVIDER + SINGLE_DIVIDER;

    private final int methodCount;
    private final int methodOffset;
    private final boolean showThreadInfo;
    private final boolean showPrettyLine;
    private final ILogTool logTool;
    private final String tag;

    private LoggerFormatStrategy(Builder builder) {
        methodCount = builder.methodCount;
        methodOffset = builder.methodOffset;
        showThreadInfo = builder.showThreadInfo;
        showPrettyLine = builder.showPrettyLine;
        logTool = builder.logTool;
        tag = builder.tag;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public void log(int priority, String onceOnlyTag, String message) {
        String tag = formatTag(onceOnlyTag);

        logTopBorder(priority, tag);
        logHeaderContent(priority, tag, methodCount);

        //get bytes of message with system's default charset (which is UTF-8 for Android)
        byte[] bytes = message.getBytes();
        int length = bytes.length;
        if (length <= CHUNK_SIZE) {
            if (methodCount > 0) {
                logDivider(priority, tag);
            }
            logContent(priority, tag, message);
            logBottomBorder(priority, tag);
            return;
        }
        if (methodCount > 0) {
            logDivider(priority, tag);
        }
        for (int i = 0; i < length; i += CHUNK_SIZE) {
            int count = Math.min(length - i, CHUNK_SIZE);
            //create a new String with system's default charset (which is UTF-8 for Android)
            logContent(priority, tag, new String(bytes, i, count));
        }
        logBottomBorder(priority, tag);
    }

    private void logTopBorder(int logType, String tag) {
        if (showPrettyLine) {
            logChunk(logType, tag, TOP_BORDER);
        }
    }

    @SuppressWarnings("StringBufferReplaceableByString")
    private void logHeaderContent(int logType, String tag, int methodCount) {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        if (showThreadInfo) {
            logChunk(logType, tag, showPrettyLine ? (HORIZONTAL_LINE + " ") : "" + "Thread: " + Thread.currentThread().getName());
            logDivider(logType, tag);
        }

        int stackOffset = getStackOffset(trace) + methodOffset;

        // 防止打印越界, corresponding method count with the current stack may exceeds the stack trace.
        // Trims the count
        if (methodCount + stackOffset > trace.length) {
            methodCount = trace.length - stackOffset - 1;
        }

        for (int i = methodCount; i > 0; i--) {
            int stackIndex = i + stackOffset;
            if (stackIndex >= trace.length) {
                continue;
            }
            StringBuilder builder = new StringBuilder();
            builder.append(getSimpleClassName(trace[stackIndex].getClassName()))
                    .append(".")
                    .append(trace[stackIndex].getMethodName())
                    .append(" ")
                    .append(" (")
                    .append(trace[stackIndex].getFileName())
                    .append(":")
                    .append(trace[stackIndex].getLineNumber())
                    .append(")");
            logChunk(logType, tag, builder.toString());
        }
    }

    private void logBottomBorder(int logType, String tag) {
        if (showPrettyLine) {
            logChunk(logType, tag, BOTTOM_BORDER);
        }
    }

    private void logDivider(int logType, String tag) {
        if (showPrettyLine) {
            logChunk(logType, tag, MIDDLE_BORDER);
        }
    }

    private void logContent(int logType, String tag, String chunk) {
        String[] lines = chunk.split(System.getProperty("line.separator"));
        for (String line : lines) {
            logChunk(logType, tag, showPrettyLine ? (HORIZONTAL_LINE + " ") : "" + line);
        }
    }

    private void logChunk(int priority, String tag, String chunk) {
        switch (priority) {
            case ILogTool.VERBOSE: {
                logTool.v(tag, chunk);
                break;
            }
            case ILogTool.DEBUG: {
                logTool.d(tag, chunk);
                break;
            }
            case ILogTool.INFO: {
                logTool.i(tag, chunk);
                break;
            }
            case ILogTool.WARN: {
                logTool.w(tag, chunk);
                break;
            }
            case ILogTool.ERROR: {
                logTool.e(tag, chunk);
                break;
            }
            case ILogTool.ASSERT: {
                logTool.wtf(tag, chunk);
                break;
            }
            default: {
                logTool.v(tag, chunk);
                break;
            }
        }
    }

    private String getSimpleClassName(String name) {
        int lastIndex = name.lastIndexOf(".");
        return name.substring(lastIndex + 1);
    }

    /**
     * Determines the starting index of the stack trace, after method calls made by this class.
     *
     * @param trace the stack trace
     * @return the stack offset
     */
    private int getStackOffset(StackTraceElement[] trace) {
        for (int i = MIN_STACK_OFFSET; i < trace.length; i++) {
            StackTraceElement e = trace[i];
            String name = e.getClassName();
            if (!name.equals(LoggerPrinter.class.getName()) && !name.equals(Logger.class.getName())) {
                return --i;
            }
        }
        return -1;
    }

    private String formatTag(String tag) {
        if (!TextUtils.isEmpty(tag) && !TextUtils.equals(this.tag, tag)) {
            return this.tag + "-" + tag;
        }
        return this.tag;
    }

    public static class Builder {
        // 打印的附加日志-方法堆栈调用层级显示数目,方便追踪
        private int methodCount = 0;
        // 打印方法起始位置偏移量
        private int methodOffset = 0;
        private boolean showThreadInfo = false;
        private boolean showPrettyLine = false;
        private ILogTool logTool;
        private String tag = "MING-LOGGER";

        private Builder() {
        }

        public Builder methodCount(int val) {
            methodCount = val;
            return this;
        }

        public Builder methodOffset(int val) {
            methodOffset = val;
            return this;
        }

        public Builder showPrettyLine(boolean showPrettyLine) {
            showPrettyLine = showPrettyLine;
            return this;
        }

        public Builder showThreadInfo(boolean val) {
            showThreadInfo = val;
            return this;
        }

        public Builder logTool(ILogTool val) {
            logTool = val;
            return this;
        }

        public Builder tag(String tag) {
            this.tag = tag;
            return this;
        }

        public LoggerFormatStrategy build() {
            if (logTool == null) {
                logTool = new LogToolImpl();
            }
            return new LoggerFormatStrategy(this);
        }
    }
}
