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
import android.view.View;
import android.widget.EditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import phoenixlib.io.rxfirebase.database.RxDatabase;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    EditText etUsrName;
    EditText etPwd;
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                RxDatabase.query(FirebaseDatabase.getInstance().getReference().child("user"))
                    .subscribe(new Consumer<DataSnapshot>() {
                        @Override public void accept(@NonNull DataSnapshot dataSnapshot)
                            throws Exception {
                            String key = dataSnapshot.getKey();
                        }
                    });
                //RxDatabase.query()
            }
        });
    }
}
