/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.background.sync;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.AsyncTask;

public class WaterReminderFirebaseJobService extends com.firebase.jobdispatcher.JobService{
    // DONE (3) WaterReminderFirebaseJobService should extend from JobService
    // DONE (4) Override onStartJob
    private AsyncTask mBackgroundTask;

    @Override
    public boolean onStartJob(final com.firebase.jobdispatcher.JobParameters jobParameters) {
        // DONE (5) By default, jobs are executed on the main thread, so make an anonymous class extending
        //  AsyncTask called mBackgroundTask.
        mBackgroundTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                ReminderTasks.executeTask(WaterReminderFirebaseJobService.this,
                        ReminderTasks.ACTION_ISSUE_CHARGING_REMINDER_NOTIFICATION);
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                jobFinished(jobParameters,false);
                super.onPostExecute(o);
            }
        };
        // DONE (6) Override doInBackground
        // DONE (7) Use ReminderTasks to execute the new charging reminder task you made, use
        // this service as the context (WaterReminderFirebaseJobService.this) and return null
        // when finished.
        // DONE (8) Override onPostExecute and called jobFinished. Pass the job parameters
        // and false to jobFinished. This will inform the JobManager that your job is done
        // and that you do not want to reschedule the job.

        // DONE (9) Execute the AsyncTask
        mBackgroundTask.execute();
        // DONE (10) Return true
        return true;
    }

    @Override
    public boolean onStopJob(com.firebase.jobdispatcher.JobParameters jobParameters) {
        // DONE (12) If mBackgroundTask is valid, cancel it
        if(null != mBackgroundTask){mBackgroundTask.cancel(true);}
        // DONE (13) Return true to signify the job should be retried
        return true;
    }
    // DONE (11) Override onStopJob
}
