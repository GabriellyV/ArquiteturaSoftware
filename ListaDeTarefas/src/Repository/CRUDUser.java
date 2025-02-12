package Repository;

import Models.User;

import java.sql.*;

public class CRUDUser {

    private final BancoDeDados banco = new BancoDeDados();

    public void create(User usuario) {
        try (Connection con = banco.getConexao()) {

            PreparedStatement ps = con.prepareStatement("INSERT INTO tb_user " +
                    "(nome, email, senha) values (?, ?, ?)");

            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getSenha());

            ps.execute();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public User readOne(int id) {
        try (Connection con = banco.getConexao()) {

            PreparedStatement ps = con.prepareStatement("SELECT * FROM tb_user WHERE id = ?");
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("id");
                String nome = rs.getString("nome");
                String email = rs.getString("email");
                String senha = rs.getString("senha");

                return new User(userId, nome, email, senha);
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        throw new RuntimeException("Usuário não encontrado.");
    }

    public User readByEmail(String email) {
        try (Connection con = banco.getConexao()) {

            PreparedStatement ps = con.prepareStatement("SELECT * FROM tb_user WHERE email = ?");
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("id");
                String nome = rs.getString("nome");
                String emailDb = rs.getString("email");
                String senha = rs.getString("senha");

                return new User(userId, nome, emailDb, senha);
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        throw new RuntimeException("Usuário não encontrado.");
    }

    public void update(User usuario) {
        try (Connection con = banco.getConexao()) {

            PreparedStatement ps = con.prepareStatement("UPDATE tb_user SET " +
                    "nome = ?, email = ?, senha = ? WHERE id = ?");

            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getSenha());
            ps.setInt(4, usuario.getId());

            ps.execute();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void delete(int id) {
        try (Connection con = banco.getConexao()) {

            PreparedStatement ps = con.prepareStatement("DELETE FROM tb_user WHERE id = ?");
            ps.setInt(1, id);
            ps.execute();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}


