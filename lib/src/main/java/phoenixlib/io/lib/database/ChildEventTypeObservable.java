package phoenixlib.io.lib.database;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;

import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by yaoda on 06/04/17.
 */

public class ChildEventTypeObservable<T> extends Observable<ValueEvent<T>> {

    private final Query query;
    private Class<T> tClass;

    ChildEventTypeObservable(final Query query, Class<T> tClass) {
        this.query = query;
        this.tClass = tClass;
    }

    @Override
    protected void subscribeActual(Observer<? super ValueEvent<T>> observer) {
        Listener<T> listener = new Listener<>(observer, query, tClass);
        query.addChildEventListener(listener);
        observer.onSubscribe(listener);
    }

    static final class Listener<T> implements Disposable, ChildEventListener {
        private final Observer<? super ValueEvent<T>> observer;
        private final Query query;
        private AtomicBoolean isDisposed = new AtomicBoolean(false);
        private Class<T> tClass;

        Listener(Observer<? super ValueEvent<T>> observer, Query query, Class<T> tClass) {
            this.observer = observer;
            this.query = query;
            this.tClass = tClass;
        }


        @Override
        public void dispose() {
            isDisposed.compareAndSet(false, true);
            query.removeEventListener(this);
        }

        @Override
        public boolean isDisposed() {
            return isDisposed.get();
        }

        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            if (!isDisposed()) {
                ValueEvent<T> t = new ValueEvent<>(EventType.TYPE_ADD, dataSnapshot.getValue(tClass));
                t.setKey(dataSnapshot.getKey());
                observer.onNext(t);
            }
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            if (!isDisposed()) {
                ValueEvent<T> t = new ValueEvent<>(EventType.TYPE_UPDATE, dataSnapshot.getValue(tClass));
                t.setKey(dataSnapshot.getKey());
                observer.onNext(t);
            }
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            if (!isDisposed()) {
                ValueEvent<T> t = new ValueEvent<>(EventType.TYPE_DELETE, dataSnapshot.getValue(tClass));
                t.setKey(dataSnapshot.getKey());
                observer.onNext(t);
            }
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            if (!isDisposed()) {
                ValueEvent<T> t = new ValueEvent<>(EventType.TYPE_ADD, dataSnapshot.getValue(tClass));
                t.setKey(dataSnapshot.getKey());
                observer.onNext(t);
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            if (isDisposed()) {
                observer.onError(databaseError.toException());
            }
        }
    }
}
