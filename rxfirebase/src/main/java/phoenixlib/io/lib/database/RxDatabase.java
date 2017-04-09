package phoenixlib.io.lib.database;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.Query;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by yaoda on 21/03/17.
 */

public final class RxDatabase {
    public static Observable<DataSnapshot> query(Query query) {
        return new ValueEventObservable(query);
    }

    public static Single<DataSnapshot> queryOnce(Query query) {
        return new SingleEventObservable(query);
    }

    public static Observable<ValueEvent<DataSnapshot>> queryChild(Query query) {
        return new ChildEventObservable(query);
    }

    public static <T> Observable<ValueEvent<T>> queryForType(Query query, final Class<T> tClass) {
        return new ChildEventTypeObservable<>(query, tClass);
    }
}
