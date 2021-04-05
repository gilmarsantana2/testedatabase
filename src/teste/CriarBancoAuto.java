package teste;

import teste.database.ConnectionDB;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class CriarBancoAuto {

    /*private final String sql = "CREATE TABLE teste (\n" +
            "    id        INTEGER      PRIMARY KEY AUTOINCREMENT,\n" +
            "    nome      VARCHAR (50) NOT NULL,\n" +
            "    sobrenome VARCHAR (50) DEFAULT NULL\n" +
            ");";*/
    private final String sql = "CREATE TABLE `testedb`.`teste` ( `id` INT NOT NULL AUTO_INCREMENT , `nome` VARCHAR(50) NOT NULL , `sobrenome` VARCHAR(50) NOT NULL , PRIMARY KEY (`id`)) ENGINE = InnoDB;";

    private String[] stataments;

    public boolean executeCreation() {
        stataments = sql.split(";|;\\s");

        ConnectionDB con = new ConnectionDB();
        Statement currentStatement = null;

        for (String comand : stataments) {
            try {
                // Execute statement
                currentStatement = con.getConnection().createStatement();
                currentStatement.execute(comand.concat(";"));

            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            } finally {
                // Release resources
                if (currentStatement != null) {
                    try {
                        con.getConnection().close();
                        currentStatement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                currentStatement = null;
            }
        }
        return true;
    }


    public void executeSqlScript(File inputFile) {

        // Delimiter
        String delimiter = ";";

        // Create scanner
        Scanner scanner;
        try {
            scanner = new Scanner(inputFile).useDelimiter(delimiter);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
            return;
        }
        ConnectionDB con = new ConnectionDB();
        // Loop through the SQL file statements
        Statement currentStatement = null;
        while (scanner.hasNext()) {

            // Get statement
            String rawStatement = scanner.next() + delimiter;
            System.out.println(rawStatement);
            try {
                // Execute statement
                currentStatement = con.getConnection().createStatement();
                currentStatement.execute(rawStatement);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                // Release resources
                if (currentStatement != null) {
                    try {
                        currentStatement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                currentStatement = null;
            }
        }
    }
}
