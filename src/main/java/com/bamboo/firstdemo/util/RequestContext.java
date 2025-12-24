package com.bamboo.firstdemo.util;

public class RequestContext {
    private static final ThreadLocal<Long> REQUEST_ID_HOLDER = new ThreadLocal<>();

    public static void setRequestId(Long requestId) {
        REQUEST_ID_HOLDER.set(requestId);
    }

    public static Long getRequestId() {
        return REQUEST_ID_HOLDER.get();
    }

    public static void clear() {
        REQUEST_ID_HOLDER.remove();
    }
}