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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;


/**
 * Created by phoenix on 2017/4/14.
 */

final class DataSnapshotMapper {

    private static <T> T parser(Class<T> tClass, DataSnapshot ds) {
        if (ds.exists()) {
            T value = ds.getValue(tClass);
            ObjectHelper.requireNonNull(value, "Null Value From DataSnapShot");
            return value;
        } else {
            return null;
        }
    }

    private static <T> List<T> parserList(Class<T> tClass, DataSnapshot dataSnapshot) {
        List<T> actual = new ArrayList<>((int) dataSnapshot.getChildrenCount());
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            actual.add(parser(tClass, ds));
        }
        return actual;
    }

    private static <T> Map<String, T> parserMap(Class<T> tClass, DataSnapshot dataSnapshot) {
        LinkedHashMap<String, T> items = new LinkedHashMap<>();
        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
            items.put(childSnapshot.getKey(), parser(tClass, childSnapshot));
        }
        return items;
    }

    private static <T> T parserGeneric(GenericTypeIndicator<T> genericTypeIndicator, DataSnapshot dataSnapshot) {
        if (dataSnapshot.exists()) {
            T value = dataSnapshot.getValue(genericTypeIndicator);
            ObjectHelper.requireNonNull(value, "Null Value From DataSnapShot");
            return value;
        } else {
            return null;
        }
    }


    static <T> Function<DataSnapshot, T> value(final Class<T> tClass) {
        ObjectHelper.requireNonNull(tClass,"Null Type Receive");
        return new Function<DataSnapshot, T>() {
            @Override
            public T apply(@NonNull DataSnapshot dataSnapshot) throws Exception {
                return DataSnapshotMapper.parser(tClass, dataSnapshot);
            }
        };
    }

    static <T> Function<DataSnapshot, List<T>> list(final Class<T> tClass) {
        ObjectHelper.requireNonNull(tClass,"Null Type Receive");
        return new Function<DataSnapshot, List<T>>() {
            @Override
            public List<T> apply(@NonNull DataSnapshot dataSnapshot) throws Exception {
                return DataSnapshotMapper.parserList(tClass, dataSnapshot);
            }
        };
    }

    static <T> Function<DataSnapshot, Map<String, T>> map(final Class<T> tClass) {
        ObjectHelper.requireNonNull(tClass,"Null Type Receive");
        return new Function<DataSnapshot, Map<String, T>>() {
            @Override
            public Map<String, T> apply(@NonNull DataSnapshot dataSnapshot) throws Exception {
                return DataSnapshotMapper.parserMap(tClass, dataSnapshot);
            }
        };
    }

    static <T> Function<DataSnapshot, T> generic(final GenericTypeIndicator<T> indicator) {
        ObjectHelper.requireNonNull(indicator,"Null Type Receive");
        return new Function<DataSnapshot, T>() {
            @Override
            public T apply(@NonNull DataSnapshot dataSnapshot) throws Exception {
                return parserGeneric(indicator, dataSnapshot);
            }
        };
    }
}
