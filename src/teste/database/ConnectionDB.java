package teste.database;

import java.io.File;
import java.sql.*;

public class ConnectionDB {
    private Connection con = null;
    private Statement statement = null;
    private ResultSet resultSet = null;

    public final String SCHEMA = "jdbc:mariadb://";
    //public final String SCHEMA = "jdbc:hsqldb:file:";
   // private final String SCHEMA = "jdbc:sqlite:";
    public final String HOST = "192.168.1.40:3306";
    //private static final String HOST = "db" + File.separator;
    private final String DATABASE = "testedb";
    public final String SQL_URI = SCHEMA + HOST + "/" + DATABASE;
    private String usuario = "root";
    private String senha = "megusta3815";
    private boolean sucess = false;

    public Connection getConnection() {
        try {
            //Class.forName("org.hsqldb.jdbc.JDBCDriver");
            //Class.forName("org.sqlite.JDBC");
            Class.forName("org.mariadb.jdbc.Driver");
            return DriverManager.getConnection(SQL_URI, usuario, senha);
            //"jdbc:mariadb://localhost:3306/DB?user=root&password=myPassword"
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Erro de conexão: " + e.getMessage());
            return null;
        }
    }

    public void createEmptyDatabase() {
        try {
            /*Class.forName("org.hsqldb.jdbc.JDBCDriver");
            DriverManager.getConnection(SCHEMA + file + "/" + DATABASE, usuario, senha);
            //"jdbc:mariadb://localhost:3306/DB?user=root&password=myPassword"
            */
            Class.forName("org.mariadb.jdbc.Driver");
            DriverManager.getConnection(SQL_URI + "?createDatabaseIfNotExist=true&serverTimeZone=UTC", usuario, senha);

            //Class.forName("org.sqlite.JDBC");
            //DriverManager.getConnection(SCHEMA + HOST + DATABASE);
            System.out.println("Banco criado com sucesso");
            sucess = true;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Erro de conexão: " + e.getMessage());
            //e.printStackTrace();
            sucess = false;
        } finally {
            close();
        }
    }

    /*
     * Execute Query retorna um ResultSet
     * Ideal para SQL tipo Select
     * */
    protected boolean executarSQL(String pSQL) {
        try {
            con = getConnection();
            statement = con.createStatement();
            resultSet = statement.executeQuery(pSQL);
            return true;
        } catch (SQLException e) {
            e.getMessage();
            return false;
        }
    }

    protected boolean executarUpdateDeleteSQL(String pSQL) {
        try {
            con = getConnection();
            statement = con.createStatement();
            statement.executeUpdate(pSQL);
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }finally {
            close();
        }
    }

    protected int insertSQL(String pSQL) {
        try {
            con = getConnection();
            statement = con.createStatement();
            return statement.executeUpdate(pSQL);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }finally {
            close();
        }
    }

    protected ResultSet getResultSet() {
        return resultSet;
    }

    public String writeResultSet() throws SQLException {
        String object = "";
        // ResultSet is initially before the first data set
        while (resultSet.next()) {

            int id = resultSet.getInt(1);
            String nome = resultSet.getString(2);
            String valor = resultSet.getString(3);

            object += "id: " + id + "  nome: " + nome + " valor: " + valor + "\n";

        }
        return object;
    }

    // You need to close the resultSet
    protected void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean getSucess() {
        return sucess;
    }

}
