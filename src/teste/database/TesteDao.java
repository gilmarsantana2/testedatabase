package teste.database;

import teste.model.TesteModel;
import java.util.ArrayList;

public class TesteDao extends ConnectionDB implements DAOInterface<TesteModel> {

    private final String tableName = "teste";

    @Override
    public int incluir(TesteModel model) {
        return insertSQL("INSERT INTO " + tableName +
                "(nome, sobrenome) VALUES ("
                + "'" + model.getNome() + "', "
                + "'" + model.getSobrenome() + "');"
        );
    }

    @Override
    public boolean excluir(int id) {
        return executarUpdateDeleteSQL("DELETE FROM " + tableName + " WHERE id = '" + id + "'");
    }

    @Override
    public boolean alterar(TesteModel model) {
        return this.executarUpdateDeleteSQL("UPDATE " + tableName + " SET "
                + "nome = '" + model.getNome() + "',"
                + "sobrenome = '" + model.getSobrenome() + "',"
                + " WHERE id = '" + model.getId() + "';"
        );
    }

    @Override
    public TesteModel selectById(int id) {
        TesteModel model = new TesteModel();
        try {
            this.executarSQL("SELECT "
                    + "id, nome, sobrenome, "
                    + "FROM " + tableName + " WHERE id = '" + id + "';");
            while (this.getResultSet().next()) {
                model.setId(this.getResultSet().getInt(1));
                model.setNome(this.getResultSet().getString(2));
                model.setSobrenome(this.getResultSet().getString(3));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.close();
        }
        return model;
    }

    @Override
    public TesteModel selectByName(String name) {
        TesteModel model = new TesteModel();
        try {
            this.executarSQL("SELECT "
                    + "id, nome, sobrenome, "
                    + "FROM " + tableName + " WHERE nome = '" + name + "';");
            while (this.getResultSet().next()) {
                model.setId(this.getResultSet().getInt(1));
                model.setNome(this.getResultSet().getString(2));
                model.setSobrenome(this.getResultSet().getString(3));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.close();
        }
        return model;
    }

    @Override
    public ArrayList<TesteModel> selectAll() {
        ArrayList<TesteModel> lista = new ArrayList<>();
        TesteModel model;
        try {
            this.executarSQL("select * from " + tableName + ";");
            while (this.getResultSet().next()) {
                model = new TesteModel();
                model.setId(this.getResultSet().getInt(1));
                model.setNome(this.getResultSet().getString(2));
                model.setSobrenome(this.getResultSet().getString(3));
                lista.add(model);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.close();
        }
        return lista;
    }
}
