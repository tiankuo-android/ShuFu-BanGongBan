package com.wfkj.android.smartoffice.util.task;

public interface BackgroundWork<T> {
    T doInBackground() throws Exception;
}
