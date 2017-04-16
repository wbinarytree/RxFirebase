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

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Callable;

import io.reactivex.Observable;

/**
 * Created by phoenix on 2017/4/15.
 */

public class RxAuth {

    public static Observable<AuthResult> signInAnonymously(final FirebaseAuth auth) {
        return new TaskObservable<>(new Callable<Task<AuthResult>>() {
            @Override
            public Task<AuthResult> call() throws Exception {
                return auth.signInAnonymously();
            }
        });
    }

    public static Observable<AuthResult> signInWithEmailAndPassword(final FirebaseAuth auth, final String email, final String password) {
        return new TaskObservable<>(new Callable<Task<AuthResult>>() {
            @Override
            public Task<AuthResult> call() throws Exception {
                return auth.signInWithEmailAndPassword(email, password);
            }
        });
    }

    public static Observable<AuthResult> signInWithCredential(final FirebaseAuth auth, final AuthCredential credential) {
        return new TaskObservable<>(new Callable<Task<AuthResult>>() {
            @Override
            public Task<AuthResult> call() throws Exception {
                return auth.signInWithCredential(credential);
            }
        });
    }

    public static Observable<AuthResult> signInWithCustomToken(final FirebaseAuth auth, final String token) {
        return new TaskObservable<>(new Callable<Task<AuthResult>>() {
            @Override
            public Task<AuthResult> call() throws Exception {
                return auth.signInWithCustomToken(token);
            }
        });
    }

    public static Observable<FirebaseUser> authState(FirebaseAuth auth) {
        return new AuthStateObservable(auth);
    }

    public static Observable<AuthResult> createUser(final FirebaseAuth auth, final String email, final String password) {
        return new TaskObservable<>(new Callable<Task<AuthResult>>() {
            @Override
            public Task<AuthResult> call() throws Exception {
                return auth.createUserWithEmailAndPassword(email, password);
            }
        });
    }

}
