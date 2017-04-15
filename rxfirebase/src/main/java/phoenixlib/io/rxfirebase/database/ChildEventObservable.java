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

package phoenixlib.io.rxfirebase.database;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;

import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.functions.ObjectHelper;

/**
 * Created by yaoda on 06/04/17.
 */

class ChildEventObservable extends Observable<ValueEvent<DataSnapshot>> {

    private final Query query;

    ChildEventObservable(@NonNull final Query query) {
        ObjectHelper.requireNonNull(query, "Null query Received");
        this.query = query;
    }

    @Override
    protected void subscribeActual(@NonNull Observer<? super ValueEvent<DataSnapshot>> observer) {
        ObjectHelper.requireNonNull(observer, "Null Observer Received");
        Listener listener = new Listener(observer, query);
        observer.onSubscribe(listener);
        query.addChildEventListener(listener);

    }

    private static final class Listener implements Disposable, ChildEventListener {
        private final Observer<? super ValueEvent<DataSnapshot>> observer;
        private final Query query;
        private final AtomicBoolean isDisposed = new AtomicBoolean(false);

        Listener(Observer<? super ValueEvent<DataSnapshot>> observer, Query query) {
            this.observer = observer;
            this.query = query;
        }


        @Override
        public void dispose() {
            isDisposed.compareAndSet(false, true);
            query.removeEventListener(this);
        }

        @Override
        public boolean isDisposed() {
            return isDisposed.get();
        }

        @Override
        public void onChildAdded(DataSnapshot ds, String s) {
            if (!isDisposed()) {
                  ValueEvent<DataSnapshot> t = new ValueEvent<>(EventType.ADD, ds, ds.getKey());
                observer.onNext(t);
            }
        }

        @Override
        public void onChildChanged(DataSnapshot ds, String s) {
            if (!isDisposed()) {
                ValueEvent<DataSnapshot> t = new ValueEvent<>(EventType.UPDATE, ds, ds.getKey());
                observer.onNext(t);
            }
        }

        @Override
        public void onChildRemoved(DataSnapshot ds) {
            if (!isDisposed()) {
                ValueEvent<DataSnapshot> t = new ValueEvent<>(EventType.DELETE, ds, ds.getKey());
                observer.onNext(t);
            }

        }

        @Override
        public void onChildMoved(DataSnapshot ds, String s) {
            if (!isDisposed()) {
                ValueEvent<DataSnapshot> t = new ValueEvent<>(EventType.MOVE, ds, ds.getKey());
                observer.onNext(t);
            }
        }

        @Override
        public void onCancelled(DatabaseError error) {
            if (isDisposed()) {
                observer.onError(error.toException());
            }
        }
    }
}
