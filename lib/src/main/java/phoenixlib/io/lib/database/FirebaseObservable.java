package phoenixlib.io.lib.database;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by yaoda on 21/03/17.
 */

final class FirebaseObservable<T extends FirebaseKey> extends Observable<T> {


    private final Class<T> tClass;
    private final Query query;

    FirebaseObservable(final Class<T> tClass, final Query query) {
        this.tClass = tClass;
        this.query = query;

    }

    @Override
    protected void subscribeActual(Observer<? super T> observer) {
        Listener listener = new Listener<>(observer, query, tClass);
        query.addListenerForSingleValueEvent(listener);
        observer.onSubscribe(listener);
    }

    static final class Listener<T extends FirebaseKey> implements Disposable, ValueEventListener {
        private final Observer<? super T> observer;
        private final Query query;
        private Class<T> tClass;

        Listener(Observer<? super T> observer, Query query, Class<T> tClass) {
            this.observer = observer;
            this.query = query;
            this.tClass = tClass;
        }


        @Override
        public void dispose() {
            query.removeEventListener(this);
        }

        @Override
        public boolean isDisposed() {
            return false;
        }


        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            try {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    T value = data.getValue(tClass);
                    value.setKey(data.getKey());
                    observer.onNext(value);
                }
                observer.onComplete();
            } catch (Exception e) {
                observer.onError(e);
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            observer.onError(new Exception(databaseError.getMessage()));
        }
    }

}
