package com.starbucks.dao;

import com.starbucks.model.Ping;
import com.starbucks.persistance.DBConn;

import javax.inject.Inject;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import static javax.jdo.Query.SQL;

public class PingDao {

    private DBConn conn;

    @Inject
    public PingDao(final DBConn conn) {
        this.conn = conn;
    }

    public static final String PING_BASE_QUERY = "SELECT id, data FROM Ping;";

    public Ping getPing() {
        PersistenceManager pm = conn.getPmp();
        Transaction tx = pm.currentTransaction();
        Ping p = null;
        try {
            tx.begin();

            Query q = pm.newQuery(SQL, PING_BASE_QUERY);
            q.setResultClass(Ping.class);
            q.setUnique(true);
            p = (Ping) q.execute();
            tx.commit();
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }

            //pm.close();
        }

        return p;
    }
}
