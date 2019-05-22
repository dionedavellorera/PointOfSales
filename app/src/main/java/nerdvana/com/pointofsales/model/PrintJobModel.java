package nerdvana.com.pointofsales.model;

import android.os.AsyncTask;

public class PrintJobModel {
    private String taskName;
    private AsyncTask asyncTask;

    public PrintJobModel(String taskName, AsyncTask asyncTask) {
        this.taskName = taskName;
        this.asyncTask = asyncTask;
    }

    public String getTaskName() {
        return taskName;
    }

    public AsyncTask getAsyncTask() {
        return asyncTask;
    }
}
