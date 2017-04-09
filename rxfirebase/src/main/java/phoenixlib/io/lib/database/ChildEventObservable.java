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

public class ChildEventObservable extends Observable<ValueEvent<DataSnapshot>> {

    private final Query query;

    ChildEventObservable(final Query query) {
        this.query = query;
    }

    @Override
    protected void subscribeActual(Observer<? super ValueEvent<DataSnapshot>> observer) {
        Listener listener = new Listener(observer, query);
        query.addChildEventListener(listener);
        observer.onSubscribe(listener);
    }

    static final class Listener implements Disposable, ChildEventListener {
        private final Observer<? super ValueEvent<DataSnapshot>> observer;
        private final Query query;
        private AtomicBoolean isDisposed = new AtomicBoolean(false);

        Listener(Observer<? super ValueEvent<DataSnapshot>> observer, Query query) {
            this.observer = observer;
            this.query = query;
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
                ValueEvent<DataSnapshot> t = new ValueEvent<>(EventType.TYPE_ADD, dataSnapshot);
                t.setKey(dataSnapshot.getKey());
                observer.onNext(t);
            }
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            if (!isDisposed()) {
                ValueEvent<DataSnapshot> t = new ValueEvent<>(EventType.TYPE_UPDATE, dataSnapshot);
                t.setKey(dataSnapshot.getKey());
                observer.onNext(t);
            }
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            if (!isDisposed()) {
                ValueEvent<DataSnapshot> t = new ValueEvent<>(EventType.TYPE_DELETE, dataSnapshot);
                t.setKey(dataSnapshot.getKey());
                observer.onNext(t);
            }
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            if (!isDisposed()) {
                ValueEvent<DataSnapshot> t = new ValueEvent<>(EventType.TYPE_ADD, dataSnapshot);
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
