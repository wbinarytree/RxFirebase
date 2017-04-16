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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * Created by phoenix on 2017/4/16.
 */

public class RxUser {
    public static Completable updateUsername(final FirebaseUser user, final String username) {
        return new TaskObservable<>(new Callable<Task<Void>>() {
            @Override
            public Task<Void> call() throws Exception {
                return user.updateProfile(new UserProfileChangeRequest.Builder().setDisplayName(username)
                                                                                .build());
            }
        }).ignoreElements();
    }

    public static Completable updateProfile(final FirebaseUser user, final UserProfileChangeRequest profile) {
        return new TaskObservable<>(new Callable<Task<Void>>() {
            @Override
            public Task<Void> call() throws Exception {
                return user.updateProfile(profile);
            }
        }).ignoreElements();
    }

    public static Observable<GetTokenResult> getToken(final FirebaseUser user, final boolean refresh) {
        return new TaskObservable<>(new Callable<Task<GetTokenResult>>() {
            @Override
            public Task<GetTokenResult> call() throws Exception {
                return user.getToken(refresh);
            }
        });
    }

}
