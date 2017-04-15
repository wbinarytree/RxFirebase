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

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.reactivex.Observable;

/**
 * Created by phoenix on 2017/4/15.
 */

public class RxAuth {

    public static Observable<AuthResult> signInAnonymously(FirebaseAuth auth) {
        return new TaskObservable<>(auth.signInAnonymously());
    }

    public static Observable<AuthResult> signInWithEmailAndPassword(FirebaseAuth auth, String email, String password) {
        return new TaskObservable<>(auth.signInWithEmailAndPassword(email, password));
    }

    public static Observable<AuthResult> signInWithCredential(FirebaseAuth auth, AuthCredential credential) {
        return new TaskObservable<>(auth.signInWithCredential(credential));
    }

    public static Observable<AuthResult> signInWithCustomToken(FirebaseAuth auth, String token) {
        return new TaskObservable<>(auth.signInWithCustomToken(token));
    }

    public static Observable<FirebaseUser> authState(FirebaseAuth auth) {
        return new AuthStateObservable(auth);
    }

}
