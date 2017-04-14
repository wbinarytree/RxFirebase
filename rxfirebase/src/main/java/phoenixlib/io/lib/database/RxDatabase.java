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

package phoenixlib.io.lib.database;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

import static phoenixlib.io.lib.database.DataSnapshotMapper.generic;
import static phoenixlib.io.lib.database.DataSnapshotMapper.list;
import static phoenixlib.io.lib.database.DataSnapshotMapper.map;
import static phoenixlib.io.lib.database.DataSnapshotMapper.value;

/**
 * Created by yaoda on 21/03/17.
 */

public final class RxDatabase {
    public static Observable<DataSnapshot> query(Query query) {
        return new ValueEventObservable(query);
    }

    public static <T> Observable<T> query(Query query, Class<T> tClass) {
        return new ValueEventObservable(query).map(value(tClass));
    }

    public static <T> Observable<T> query(Query query, GenericTypeIndicator<T> indicator) {
        return new ValueEventObservable(query).map(generic(indicator));
    }

    public static <T> Observable<List<T>> queryList(Query query, Class<T> tClass) {
        return new ValueEventObservable(query).map(list(tClass));
    }

    public static <T> Observable<Map<String, T>> queryMap(Query query, Class<T> tClass) {
        return new ValueEventObservable(query).map(map(tClass));
    }

    public static Single<DataSnapshot> queryOnce(Query query) {
        return new SingleEventObservable(query);
    }

    public static <T> Single<T> queryOnce(Query query, Class<T> tClass) {
        return new SingleEventObservable(query).map(value(tClass));
    }

    public static <T> Single<T> queryOnce(Query query, GenericTypeIndicator<T> indicator) {
        return new SingleEventObservable(query).map(generic(indicator));
    }

    public static <T> Single<List<T>> queryOnceList(Query query, Class<T> tClass) {
        return new SingleEventObservable(query).map(list(tClass));
    }

    public static <T> Single<Map<String, T>> queryOnceMap(Query query, Class<T> tClass) {
        return new SingleEventObservable(query).map(map(tClass));
    }

    public static Observable<ValueEvent<DataSnapshot>> childEvent(Query query) {
        return new ChildEventObservable(query);
    }

    public static <T> Observable<ValueEvent<T>> childEvent(Query query, final Class<T> tClass) {
        return new ChildEventObservable(query).map(new Function<ValueEvent<DataSnapshot>, ValueEvent<T>>() {
            @Override
            public ValueEvent<T> apply(@NonNull ValueEvent<DataSnapshot> event) throws Exception {
                return new ValueEvent<>(event.getType(), value(tClass).apply(event.getValue()), event
                        .getKey());
            }
        });
    }

}
