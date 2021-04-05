package teste;

import teste.database.TesteDao;
import teste.model.TesteModel;

public class TesteDB {
    static int count = 0;
    public static void main(String[] args) {
        try {
            if (Manipulador.getProp().getProperty("banco").equals("true")) {
                TesteDao dao = new TesteDao();
                TesteModel novo = new TesteModel();
                novo.setNome("Gilmar");
                novo.setSobrenome("Silva");
                dao.incluir(novo);
                for (TesteModel teste : dao.selectAll()) {
                    System.out.println(teste.toString());
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            System.out.println("Resetando..");
            if (count < 1) {
                count++;
                Manipulador.resetConfigs();
            } else {
                System.out.println("Error...");
            }
        }
    }
}
