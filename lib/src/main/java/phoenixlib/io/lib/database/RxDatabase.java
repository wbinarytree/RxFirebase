package phoenixlib.io.lib.database;

import com.google.firebase.database.Query;

import io.reactivex.Observable;

/**
 * Created by yaoda on 21/03/17.
 */

public final class RxDatabase {
    public static <T extends FirebaseKey> Observable<T> limitToFirstOnce(Class<T> tClass, Query query) {

        return new FirebaseObservable<>(tClass, query);
    }

}
