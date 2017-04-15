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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.functions.ObjectHelper;

/**
 * Created by phoenix on 2017/4/15.
 */

class AuthStateObservable extends Observable<FirebaseUser> {

    private final FirebaseAuth auth;

    AuthStateObservable(FirebaseAuth auth) {
        ObjectHelper.requireNonNull(auth, "Null FirebaseAuth Received");
        this.auth = auth;
    }

    @Override
    protected void subscribeActual(Observer<? super FirebaseUser> observer) {
        ObjectHelper.requireNonNull(observer, "Null Observer Received");
        AuthListener listener = new AuthListener(observer, auth);
        observer.onSubscribe(listener);
        auth.addAuthStateListener(listener);
    }

    private static class AuthListener implements Disposable, FirebaseAuth.AuthStateListener {

        private final Observer<? super FirebaseUser> observer;
        private final FirebaseAuth auth;
        private final AtomicBoolean isDispose = new AtomicBoolean(false);

        AuthListener(Observer<? super FirebaseUser> observer, FirebaseAuth auth) {

            this.observer = observer;
            this.auth = auth;
        }


        @Override
        public void dispose() {
            isDispose.compareAndSet(false,true);
            auth.removeAuthStateListener(this);
        }

        @Override
        public boolean isDisposed() {
            return isDispose.get();
        }

        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            if (isDisposed()) {
                FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                if (currentUser != null) {
                    observer.onNext(currentUser);
                }
            }
        }
    }
}
