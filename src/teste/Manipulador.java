package teste;

import teste.database.ConnectionDB;

import java.io.*;
import java.util.Properties;

public class Manipulador {

    public static Properties getProp() throws NullPointerException {
        Properties props = new Properties();
        FileInputStream file;
        try {
            file = new FileInputStream("properties" + File.separator + "config.properties");
            props.load(file);
            return props;
        } catch (FileNotFoundException e) {
            System.out.println("Criando properties..............");
            createProperties();
            return getProp();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void resetConfigs(){
        createProperties();
        TesteDB.main(new String[1]);
    }

    private static void createProperties() {
        File local = new File(".");
        String path = "";
        try {
            path = local.getCanonicalPath() + File.separator;
        } catch (IOException e) {
            e.printStackTrace();
        }
        File configs = new File(path + "properties");
        File db = new File(path + "db");
        if (!configs.exists() && !db.exists()) {
            try {
                configs.mkdir();
                db.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }

        try {
            var properties = new FileWriter(path + "properties" + File.separator + "config.properties");
            var text = new PrintWriter(properties);
            text.println("banco = true");
            text.println("path = " + path);
            text.println("db = " + path + "db" + File.separator);
            boolean ok = createDatabase();
            if (ok) {
                text.close();
                System.out.println("Sucesso....");
            } else {
                System.out.println("Falha");
                return;
            }
        } catch (IOException e) {
            System.out.println("Erro ao Criar arquivos: " + e.getMessage());
        }
    }

    private static boolean createDatabase() {
        ConnectionDB conectar = new ConnectionDB();
        conectar.createEmptyDatabase();
        if (conectar.getSucess()) {
            CriarBancoAuto criarBanco = new CriarBancoAuto();
            return criarBanco.executeCreation();
        } else {
            return false;
        }
    }
}
