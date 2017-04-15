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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.functions.ObjectHelper;

/**
 * Created by phoenix on 2017/4/15.
 */

final class TaskObservable<T> extends Observable<T> {


    private final Callable<Task<T>> callable;

    TaskObservable(Callable<Task<T>> callable) {
        ObjectHelper.requireNonNull(callable, "Null Callable Received");
        this.callable = callable;
    }

    @Override
    protected void subscribeActual(Observer<? super T> observer) {
        ObjectHelper.requireNonNull(observer, "Null Observer Received");
        Task<T> task;
        try {
            task = callable.call();
            ObjectHelper.requireNonNull(task, "Null Task Received");
        } catch (Exception e) {
            observer.onError(e);
            return;
        }
        TaskListener<T> taskListener = new TaskListener<>(observer);
        observer.onSubscribe(taskListener);
        task.addOnFailureListener(taskListener);
        task.addOnSuccessListener(taskListener);
        task.addOnCompleteListener(taskListener);
    }

    private static class TaskListener<T> implements OnCompleteListener<T>, Disposable, OnFailureListener, OnSuccessListener<T> {


        private final Observer<? super T> observer;
        private final AtomicBoolean isDispose = new AtomicBoolean(false);

        TaskListener(Observer<? super T> observer) {
            this.observer = observer;
        }

        @Override
        public void dispose() {
            isDispose.compareAndSet(false, true);
        }

        @Override
        public boolean isDisposed() {
            return isDispose.get();
        }

        @Override
        public void onComplete(@NonNull Task<T> task) {
            if (!isDisposed()) {
                observer.onComplete();
            }
        }

        @Override
        public void onFailure(@NonNull Exception e) {
            if (!isDisposed()) {
                observer.onError(e);
            }
        }

        @Override
        public void onSuccess(T o) {
            if (!isDisposed()) {
                observer.onNext(o);
            }

        }
    }
}
