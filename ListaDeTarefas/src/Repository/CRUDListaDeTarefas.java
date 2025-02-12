package Repository;
import Models.ListaTarefas;
import Models.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CRUDListaDeTarefas {

    private final BancoDeDados banco = new BancoDeDados();

    public void create(ListaTarefas tarefa) {
        try (Connection con = banco.getConexao()) {

            PreparedStatement ps = con.prepareStatement("INSERT INTO tb_lista_tarefas " +
                    "(titulo, descricao, status, prioridade, id_usuario) " +
                    "values (?, ?, ?, ?, ?)");

            ps.setString(1, tarefa.getTitulo());
            ps.setString(2, tarefa.getDescricao());
            ps.setString(3, tarefa.getStatus());
            ps.setString(4, tarefa.getPrioridade());
            ps.setInt(5, tarefa.getUser().getId());  // Assumindo que a tarefa tem um usuário

            ps.execute();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public ListaTarefas readOne(int id) {
        try (Connection con = banco.getConexao()) {

            PreparedStatement ps = con.prepareStatement("SELECT * FROM tb_lista_tarefas WHERE id = ?");
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int tarefaId = rs.getInt("id");
                String titulo = rs.getString("titulo");
                String descricao = rs.getString("descricao");
                String status = rs.getString("status");
                String prioridade = rs.getString("prioridade");
                int idUsuario = rs.getInt("id_usuario");

                CRUDUser crudUser = new CRUDUser();
                User usuario = crudUser.readOne(idUsuario);

                return new ListaTarefas(tarefaId, titulo, descricao, status, prioridade, usuario);
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        throw new RuntimeException("Tarefa não encontrada.");
    }

    public List<ListaTarefas> readAll() {
        try (Connection con = banco.getConexao()) {

            PreparedStatement ps = con.prepareStatement("SELECT * FROM tb_lista_tarefas");
            ResultSet rs = ps.executeQuery();
            List<ListaTarefas> tarefas = new ArrayList<>();

            while (rs.next()) {
                int id = rs.getInt("id");
                String titulo = rs.getString("titulo");
                String descricao = rs.getString("descricao");
                String status = rs.getString("status");
                String prioridade = rs.getString("prioridade");
                int idUsuario = rs.getInt("id_usuario");

                CRUDUser crudUser = new CRUDUser();
                User usuario = crudUser.readOne(idUsuario);

                tarefas.add(new ListaTarefas(id, titulo, descricao, status, prioridade, usuario));
            }

            return tarefas;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        throw new RuntimeException("Erro ao listar tarefas.");
    }

    public void update(ListaTarefas tarefa) {
        try (Connection con = banco.getConexao()) {

            PreparedStatement ps = con.prepareStatement("UPDATE tb_lista_tarefas SET " +
                    "titulo = ?, descricao = ?, status = ?, prioridade = ?, id_usuario = ? " +
                    "WHERE id = ?");

            ps.setString(1, tarefa.getTitulo());
            ps.setString(2, tarefa.getDescricao());
            ps.setString(3, tarefa.getStatus());
            ps.setString(4, tarefa.getPrioridade());
            ps.setInt(5, tarefa.getUser().getId());
            ps.setInt(6, tarefa.getId());

            ps.execute();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void delete(int id) {
        try (Connection con = banco.getConexao()) {

            PreparedStatement ps = con.prepareStatement("DELETE FROM tb_lista_tarefas WHERE id = ?");
            ps.setInt(1, id);
            ps.execute();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}

