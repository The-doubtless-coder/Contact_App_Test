package contact.registry.webservice.contact_registry_crud_apis_rest.repository;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DBUtil {
    private static final DataSource datasource;
    private static final String JNDI_LOOKUP_SERVICE = "java:comp/env/jdbc/contactsDB";

    static {
        try{
            Context ctx = new InitialContext();
            Object lookup = ctx.lookup(JNDI_LOOKUP_SERVICE);
            if(lookup!=null){
                datasource = (DataSource) lookup;
            }else {
                throw new Exception("Database connection error");
            }
        }catch (Exception e){
            throw new ExceptionInInitializerError("ERROR " + e.getMessage());
        }
    }

    public static DataSource getDataSource() {
        return datasource;
    }

}
