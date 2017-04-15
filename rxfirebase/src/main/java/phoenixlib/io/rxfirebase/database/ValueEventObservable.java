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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.functions.ObjectHelper;

/**
 * Created by yaoda on 21/03/17.
 */

class ValueEventObservable extends Observable<DataSnapshot> {

    private final Query query;

    ValueEventObservable(final Query query) {
        ObjectHelper.requireNonNull(query, "Null query Received");
        this.query = query;
    }

    @Override
    protected void subscribeActual(Observer<? super DataSnapshot> observer) {
        ObjectHelper.requireNonNull(observer, "Null Observer Received");
        Listener listener = new Listener(observer, query);
        observer.onSubscribe(listener);
        query.addListenerForSingleValueEvent(listener);

    }

    static final class Listener implements Disposable, ValueEventListener {
        private final Observer<? super DataSnapshot> observer;
        private final Query query;
        private AtomicBoolean isDisposed = new AtomicBoolean(false);

        Listener(Observer<? super DataSnapshot> observer, Query query) {
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
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (!isDisposed()) {
                observer.onNext(dataSnapshot);
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            if (!isDisposed()) {
                observer.onError(databaseError.toException());
            }
        }
    }
}
