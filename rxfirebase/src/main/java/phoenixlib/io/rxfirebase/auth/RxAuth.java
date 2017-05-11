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
import io.reactivex.Observable;
import java.util.concurrent.Callable;

/**
 * Static factory methods for creating {@linkplain Observable observables}  for {@code FirebaseAuth}
 * actions.
 */

public class RxAuth {

    private RxAuth() {
        //empty constructor
    }

    /**
     * Create an observable which emits {@link AuthResult} for {@link FirebaseAuth#signInAnonymously()}.
     * <p>
     * <em>Warning:</em> The created observable keeps a strong reference to {@code FirebaseAuth}.
     * UnSubscribe
     * to free this reference.
     *
     * @param auth the {@link FirebaseAuth} instance
     * @return {@link Observable<AuthResult>} to received the result of
     * {@link FirebaseAuth#signInAnonymously()}, Call onNext() when task success,
     * onError when task failed and onComplete() when task completed.
     */
    public static Observable<AuthResult> signInAnonymously(final FirebaseAuth auth) {
        Callable<Task<AuthResult>> c = new Callable<Task<AuthResult>>() {
            @Override public Task<AuthResult> call() throws Exception {
                return auth.signInAnonymously();
            }
        };
        return new TaskObservable<>(c);
    }

    /**
     * Create an observable which emits {@link AuthResult} for
     * {@link FirebaseAuth#signInWithEmailAndPassword(String, String)}.
     * <p>
     * <em>Warning:</em> The created observable keeps a strong reference to {@code FirebaseAuth}.
     * UnSubscribe
     * to free this reference.
     *
     * @param auth the {@link FirebaseAuth} instance
     * @param email email String
     * @param password password String
     * @return {@link Observable<AuthResult>} to received the result of
     * {@link FirebaseAuth#signInWithEmailAndPassword(String, String)}
     * Call onNext() when task success, onError when task failed and onComplete() when task
     * completed.
     */
    public static Observable<AuthResult> signInWithEmailAndPassword(final FirebaseAuth auth,
        final String email, final String password) {
        Callable<Task<AuthResult>> c = new Callable<Task<AuthResult>>() {
            @Override public Task<AuthResult> call() throws Exception {
                return auth.signInWithEmailAndPassword(email, password);
            }
        };
        return new TaskObservable<>(c);
    }

    /**
     * Create an observable which emits {@link AuthResult} for {@link FirebaseAuth#signInWithCredential(AuthCredential)}.
     *
     * <p>
     * <em>Warning:</em> The created observable keeps a strong reference to {@code FirebaseAuth}.
     * UnSubscribe
     * to free this reference.
     *
     * @param auth the {@link FirebaseAuth} instance
     * @param credential {@link AuthCredential} instance use for sign in
     * @return {@link Observable<AuthResult>} to received the result of
     * {@link FirebaseAuth#signInWithCredential(AuthCredential)}, Call onNext() when task
     * success, onError when task failed and onComplete() when task completed.
     */
    public static Observable<AuthResult> signInWithCredential(final FirebaseAuth auth,
        final AuthCredential credential) {
        Callable<Task<AuthResult>> c = new Callable<Task<AuthResult>>() {
            @Override public Task<AuthResult> call() throws Exception {
                return auth.signInWithCredential(credential);
            }
        };
        return new TaskObservable<>(c);
    }

    /**
     * Create an observable which emits {@link AuthResult} for {@link FirebaseAuth#signInWithCustomToken(String)}}
     * <p>
     * <em>Warning:</em> The created observable keeps a strong reference to {@code FirebaseAuth}.
     * UnSubscribe
     * to free this reference.
     *
     * @param auth the {@link FirebaseAuth} instance
     * @param token String token use for sign in
     * @return {@link Observable<AuthResult>} to received the result of
     * {@link FirebaseAuth#signInWithCustomToken(String)} Call onNext() when task
     * success, onError when task failed and onComplete() when task completed.
     */
    public static Observable<AuthResult> signInWithCustomToken(final FirebaseAuth auth,
        final String token) {
        Callable<Task<AuthResult>> c = new Callable<Task<AuthResult>>() {
            @Override public Task<AuthResult> call() throws Exception {
                return auth.signInWithCustomToken(token);
            }
        };
        return new TaskObservable<>(c);
    }

    /**
     * Create an observable which emits {@link FirebaseUser} for {@link
     * FirebaseAuth#addAuthStateListener(FirebaseAuth.AuthStateListener)}
     * <p>
     * <em>Warning:</em> The created observable keeps a strong reference to {@code FirebaseAuth}.
     * UnSubscribe
     * to free this reference.
     *
     * @param auth the {@link FirebaseAuth} instance
     * @return {@link Observable<FirebaseUser>} to received the result of
     * {@link FirebaseAuth#addAuthStateListener(FirebaseAuth.AuthStateListener)} Call onNext() when
     * user successfully sign in, onError when Exception occurred and onComplete() when user sign
     * out.
     */
    public static Observable<FirebaseUser> authState(FirebaseAuth auth) {
        return new AuthStateObservable(auth);
    }

    /**
     * Create an observable which emits {@link AuthResult} for
     * {@link FirebaseAuth#createUserWithEmailAndPassword(String, String)} to Create user
     * <p>
     * <em>Warning:</em> The created observable keeps a strong reference to {@code FirebaseAuth}.
     * UnSubscribe
     * to free this reference.
     *
     * @param auth the {@link FirebaseAuth} instance
     * @param email String email used for creating user
     * @param password String password used for creating user
     * @return {@link Observable<AuthResult>} to received the result of
     * {@link FirebaseAuth#createUserWithEmailAndPassword(String, String)} Call onNext() when task
     * success, onError when task failed and onComplete() when task completed.
     */
    public static Observable<AuthResult> createUser(final FirebaseAuth auth, final String email,
        final String password) {
        Callable<Task<AuthResult>> c = new Callable<Task<AuthResult>>() {
            @Override public Task<AuthResult> call() throws Exception {
                return auth.createUserWithEmailAndPassword(email, password);
            }
        };
        return new TaskObservable<>(c);
    }
}
