import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AutomovelDAO {

    public void inserir(Automoveis automovel) throws SQLException {
        String sql = "INSERT INTO Automoveis (modelo, marca, ano) VALUES (?, ?, ?)";
        try (Connection conn = Conexao.Abrirconexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, automovel.getModelo());
            stmt.setString(2, automovel.getMarca());
            stmt.setInt(3, automovel.getAno());
            stmt.executeUpdate();
        }
    }

    public List<Automoveis> listar() throws SQLException {
        String sql = "SELECT * FROM automoveis";
        try (Connection conn = Conexao.Abrirconexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            List<Automoveis> automoveis = new ArrayList<>();
            while (rs.next()) {
                Automoveis automovel = new Automoveis();
                automovel.setId(rs.getInt("id"));
                automovel.setModelo(rs.getString("modelo"));
                automovel.setMarca(rs.getString("marca"));
                automovel.setAno(rs.getInt("ano"));
                automoveis.add(automovel);
            }
            return automoveis;
        }
    }

    public void atualizar(Automoveis automovel) throws SQLException {
        String sql = "UPDATE automoveis SET modelo = ?, marca = ?, ano = ? WHERE id = ?";
        try (Connection conn = Conexao.Abrirconexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, automovel.getModelo());
            stmt.setString(2, automovel.getMarca());
            stmt.setInt(3, automovel.getAno());
            stmt.setInt(4, automovel.getId());
            stmt.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM automoveis WHERE id = ?";
        try (Connection conn = Conexao.Abrirconexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public List<String> listarMarcas() throws SQLException {
        String sql = "SELECT DISTINCT marca FROM automoveis";
        try (Connection conn = Conexao.Abrirconexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            List<String> marcas = new ArrayList<>();
            while (rs.next()) {
                marcas.add(rs.getString("marca"));
            }
            return marcas;
        }
    }
    public List<String> listarModelos() throws SQLException {
        String sql = "SELECT DISTINCT modelo FROM automoveis";
        try (Connection conn = Conexao.Abrirconexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            List<String> modelos = new ArrayList<>();
            while (rs.next()) {
            	modelos.add(rs.getString("modelo"));
            }
            return modelos;
        }
    }
    public Automoveis buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM automoveis WHERE id = ?";
        Automoveis automovel = null;

        try (Connection conn = Conexao.Abrirconexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) { 
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    automovel = new Automoveis();
                    automovel.setId(rs.getInt("id"));
                    automovel.setModelo(rs.getString("modelo"));
                    automovel.setMarca(rs.getString("marca"));
                    automovel.setAno(rs.getInt("ano"));
                }
            }
        }

        return automovel;
    }

    public List<Automoveis> consultarPorFiltros(String modelo, String marca) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT * FROM automoveis WHERE 1=1");
        List<Object> parametros = new ArrayList<>();

        if (modelo != null && !modelo.equalsIgnoreCase("Todas")) {
            sql.append(" AND modelo = ?");
            parametros.add(modelo);
        }

        if (marca != null && !marca.equalsIgnoreCase("Todas")) {
            sql.append(" AND LOWER(marca) = ?");
            parametros.add(marca.toLowerCase());
        }

        try (Connection conn = Conexao.Abrirconexao();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < parametros.size(); i++) {
                stmt.setObject(i + 1, parametros.get(i));
            }

            try (ResultSet rs = stmt.executeQuery()) {
                List<Automoveis> automoveis = new ArrayList<>();
                while (rs.next()) {
                    Automoveis automovel = new Automoveis();
                    automovel.setId(rs.getInt("id"));
                    automovel.setModelo(rs.getString("modelo"));
                    automovel.setMarca(rs.getString("marca"));
                    automovel.setAno(rs.getInt("ano"));
                    automoveis.add(automovel);
                }
                return automoveis;
            }
        }
    }
}
