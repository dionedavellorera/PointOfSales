package nerdvana.com.pointofsales;

import android.os.AsyncTask;

import nerdvana.com.pointofsales.entities.CurrentTransactionEntity;

public class DbInit extends AsyncTask<Void, Void, Void> {
    @Override
    protected Void doInBackground(Void... voids) {
        CurrentTransactionEntity.findById(CurrentTransactionEntity.class, (long) 1);
        return null;
    }
}
