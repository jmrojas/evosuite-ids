package org.evosuite.runtime.javaee.db;

import org.evosuite.runtime.javaee.javax.persistence.EvoEntityManagerFactory;
import org.hibernate.internal.SessionImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Class used to setup the in memory database.
 *
 * <p>
 *     See https://objectpartners.com/2010/11/09/unit-testing-your-persistence-tier-code/
 * </p>
 *
 * Created by Andrea Arcuri on 31/05/15.
 */
public class DBManager {

    private static final Logger logger = LoggerFactory.getLogger(DBManager.class);

    /**
     * JPA unit hardcoded in persistence.xml
     */
    private static final String EVOSUITE_DB = "EvoSuiteDB";


    private static final DBManager singleton = new DBManager();

    private EvoEntityManagerFactory factory;
    private EntityManager em;

    private DBManager(){
        //TODO inside any DB call should be not instrumentation. although partially handled in
        //     getPackagesShouldNotBeInstrumented, should still disable/enable in method wrappers.
        //     Maybe not needed during test generation, but likely in runtime when JUnit are run in isolation,
        //     unless we do full shading

        factory = new EvoEntityManagerFactory();
        em = factory.createEntityManager();
    }

    public static DBManager getInstance(){
        return singleton;
    }

    public EntityManagerFactory getDefaultFactory(){
        return factory;
    }

    public EntityManager getCurrentEntityManager(){
        return em;
    }

    public void createNewEntityManager(){
        em = factory.createEntityManager();
    }

    public boolean clearDatabase() {
        try {
            //code adapted from https://objectpartners.com/2010/11/09/unit-testing-your-persistence-tier-code/

            Connection c = ((SessionImpl) em.getDelegate()).connection();
            Statement s = c.createStatement();
            s.execute("SET DATABASE REFERENTIAL INTEGRITY FALSE");
            Set<String> tables = new LinkedHashSet<>();
            ResultSet rs = s.executeQuery("select table_name " +
                    "from INFORMATION_SCHEMA.system_tables " +
                    "where table_type='TABLE' and table_schem='PUBLIC'");
            while (rs.next()) {
                if (!rs.getString(1).startsWith("DUAL_")) {
                    tables.add(rs.getString(1));
                }
            }
            rs.close();
            for (String table : tables) {
                String delete = "DELETE FROM " + table;
                s.executeUpdate(delete);
                logger.debug("SQL executed: "+delete);
            }
            s.execute("SET DATABASE REFERENTIAL INTEGRITY TRUE");
            s.close();
            return true;
        } catch (Exception e){
            logger.error("Failed to clear database: "+e.toString(),e);
            return false;
        }
    }

    /**
     * Be sure the database is ready to use.
     * This means for example rolling back any activate transaction and delete all tables
     */
    public void initDB(){
        factory.clearAllEntityManagers();
        if(!factory.isOpen()){
            /*
                this maybe could happen if "close" is called in the SUT.
                note: initializing a factory seems quite expensive, and this is the
                reason why we try here to reuse it instead of creating a new one
                at each new test case run
             */
            factory = new EvoEntityManagerFactory();
        }
        createNewEntityManager();
        clearDatabase();
    }
}
