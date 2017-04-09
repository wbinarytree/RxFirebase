package phoenixlib.io.lib.database;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

/**
 * Created by yaoda on 06/04/17.
 */

public class SingleEventObservable extends Single<DataSnapshot> {

    private final Query query;

    SingleEventObservable(final Query query) {
        this.query = query;
    }


    @Override
    protected void subscribeActual(SingleObserver<? super DataSnapshot> observer) {
        Listener listener = new Listener(observer, query);
        query.addListenerForSingleValueEvent(listener);
        observer.onSubscribe(listener);
    }

    static final class Listener implements Disposable, ValueEventListener {
        private final SingleObserver<? super DataSnapshot> observer;
        private final Query query;
        private AtomicBoolean isDisposed = new AtomicBoolean(false);

        Listener(SingleObserver<? super DataSnapshot> observer, Query query) {
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
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (!isDisposed()) {
                observer.onSuccess(dataSnapshot);
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            if (!isDisposed()) {
                observer.onError(databaseError.toException());
            }
        }
    }
}
