
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import net.miginfocom.swing.MigLayout;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class TelaConsultar extends JPanel {
    private JComboBox<String> cbMarca, cbModelo;
    private JTable tabelaResultados;
    private JButton btnConsultar;
    private AutomovelDAO automovelDAO;

    public TelaConsultar() {
        automovelDAO = new AutomovelDAO();
        setLayout(new MigLayout("", "[][grow][][grow]", "[][][grow][]"));

        JLabel lblTitulo = new JLabel("Consultar Veículos");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        add(lblTitulo, "cell 0 0 4 1,alignx center");

        JLabel lblModelo = new JLabel("Modelo:");
        add(lblModelo, "cell 0 1,alignx trailing");

        cbModelo = new JComboBox<>();
        add(cbModelo, "cell 1 1,growx");

        JLabel lblMarca = new JLabel("Marca:");
        add(lblMarca, "cell 2 1,alignx left");

        cbMarca = new JComboBox<>();
        add(cbMarca, "cell 3 1,growx");

        JScrollPane scrollPane = new JScrollPane();
        add(scrollPane, "cell 0 2 4 1,grow");

        tabelaResultados = new JTable();
        tabelaResultados.setModel(new DefaultTableModel(new Object[][]{}, new String[]{"Modelo", "Marca", "Ano"}));
        scrollPane.setViewportView(tabelaResultados);

        btnConsultar = new JButton("Consultar");
        btnConsultar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarConsulta();
            }
        });
        add(btnConsultar, "cell 0 3 4 1,alignx center");

        preencherComboBoxModelos();
        preencherComboBoxMarcas();
    }

    private void preencherComboBoxModelos() {
        try {
            cbModelo.addItem("Todas");
            List<String> modelos = automovelDAO.listarModelos();
            for (String modelo : modelos) {
                cbModelo.addItem(modelo);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar modelos: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void preencherComboBoxMarcas() {
        try {
            cbMarca.addItem("Todas");
            List<String> marcas = automovelDAO.listarMarcas();
            for (String marca : marcas) {
                cbMarca.addItem(marca);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar marcas: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void realizarConsulta() {
        String modelo = (String) cbModelo.getSelectedItem();
        String marca = (String) cbMarca.getSelectedItem();

        try {
            List<Automoveis> automoveis = automovelDAO.consultarPorFiltros(modelo, marca);
            DefaultTableModel tableModel = (DefaultTableModel) tabelaResultados.getModel();
            tableModel.setRowCount(0);

            for (Automoveis automovel : automoveis) {
                tableModel.addRow(new Object[]{
                        automovel.getModelo(),
                        automovel.getMarca(),
                        automovel.getAno()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao consultar automóveis: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
