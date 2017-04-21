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

package phoenixlib.io.rxfirebase;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import phoenixlib.io.rxfirebase.auth.RxAuth;
import phoenixlib.io.rxfirebase.auth.RxUser;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    EditText etUsrName;
    EditText etPwd;
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxAuth.createUser(auth, "11wangyaoda@gmail.com", "123456789")
                      .flatMap(new Function<AuthResult, ObservableSource<AuthResult>>() {
                          @Override
                          public ObservableSource<AuthResult> apply(@NonNull AuthResult authResult) throws Exception {
                              return RxUser.updateUsername(authResult.getUser(), "W_BinaryTree")
                                           .andThen(Observable.just(authResult));
                          }
                      })
                      .map(new Function<AuthResult, String>() {
                          @Override
                          public String apply(@NonNull AuthResult authResult) throws Exception {
                              return authResult.getUser().getUid() + " \n username:"+authResult.getUser()
                                                                               .getDisplayName();
                          }
                      })

                      .startWith("Starting")
                      .onErrorReturn(new Function<Throwable, String>() {
                          @Override
                          public String apply(@NonNull Throwable throwable) throws Exception {
                              return throwable.getMessage();
                          }
                      })
                      .subscribe(new Consumer<String>() {
                          @Override
                          public void accept(@NonNull String s) throws Exception {
                              Log.d(TAG, "accept: " + s);
                          }
                      }, new Consumer<Throwable>() {
                          @Override
                          public void accept(@NonNull Throwable throwable) throws Exception {
                              Log.d(TAG, "accept: " + throwable.getMessage());
                          }
                      }, new Action() {
                          @Override
                          public void run() throws Exception {
                              Log.d(TAG, "Success");
                          }
                      });
            }
        });
    }
}
