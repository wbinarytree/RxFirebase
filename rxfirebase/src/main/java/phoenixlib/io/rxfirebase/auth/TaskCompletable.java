/*
 * Copyright 2017 WBinaryTree
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package phoenixlib.io.rxfirebase.auth;

import android.support.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by yaoda on 11/05/17.
 */

class TaskCompletable extends Completable {
    private final Callable<Task<Void>> callable;

    TaskCompletable(Callable<Task<Void>> callable) {
        ObjectHelper.requireNonNull(callable, "Null Callable Received");
        this.callable = callable;
    }

    @Override protected void subscribeActual(CompletableObserver observer) {
        ObjectHelper.requireNonNull(observer, "Null Observer Received");
        TaskListener taskListener = new TaskListener(observer);
        observer.onSubscribe(taskListener);
        Task task;
        try {
            task = callable.call().addOnCompleteListener(taskListener);
            ObjectHelper.requireNonNull(task, "Null Task Received");
        } catch (Exception e) {
            if (!taskListener.isDisposed()) {
                observer.onError(e);
            }
        }
    }

    private static class TaskListener implements OnCompleteListener<Void>, Disposable {

        private final CompletableObserver observer;

        private final AtomicBoolean isDispose = new AtomicBoolean(false);

        TaskListener(CompletableObserver observer) {
            this.observer = observer;
        }

        @Override public void dispose() {
            isDispose.compareAndSet(false, true);
        }

        @Override public boolean isDisposed() {
            return isDispose.get();
        }

        @Override public void onComplete(@NonNull Task task) {
            if (!isDisposed()) {
                if (task.isSuccessful()) {
                    observer.onComplete();
                } else {
                    try {
                        observer.onError(task.getException());
                    } catch (Exception e) {
                        observer.onError(new CompositeException(e));
                    }
                }
            }
        }
    }
}
