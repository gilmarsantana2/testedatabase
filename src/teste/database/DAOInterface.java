package teste.database;

import java.util.ArrayList;

public interface DAOInterface<T> {

    public int incluir(T model);
    public boolean excluir(int id);
    public boolean alterar(T model);
    public T selectById(int id);
    public T selectByName(String name);
    public ArrayList<T> selectAll();
}
