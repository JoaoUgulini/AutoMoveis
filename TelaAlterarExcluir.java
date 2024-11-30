import javax.swing.*;
import net.miginfocom.swing.MigLayout;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class TelaAlterarExcluir extends JPanel {
    private JLabel lblTitulo;
    private JComboBox<String> comboBox;
    private JLabel lblId, lblModelo, lblMarca, lblAno;
    private JTextField textFieldId, textFieldModelo, textFieldMarca, textFieldAno;
    private JButton btnAlterar, btnExcluir;
    private AutomovelDAO automovelDAO;

    public TelaAlterarExcluir() {
        automovelDAO = new AutomovelDAO();
        setLayout(new MigLayout("", "[grow][grow][grow]", "[][grow][][grow][][][][][][][grow]"));

        this.lblTitulo = new JLabel("Alterar/Excluir Veículos");
        this.lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        this.lblTitulo.setForeground(Color.BLACK);
        this.lblTitulo.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 20));
        add(this.lblTitulo, "flowy,cell 1 0,growx");

        this.comboBox = new JComboBox<>();
        add(this.comboBox, "cell 1 2,growx");
        this.comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carregarDadosSelecionados();
            }
        });

        this.lblId = new JLabel("ID:");
        this.lblId.setFont(new Font("Arial", Font.PLAIN, 15));
        add(this.lblId, "cell 0 4,alignx trailing");

        this.textFieldId = new JTextField();
        this.textFieldId.setFont(new Font("Arial", Font.PLAIN, 15));
        this.textFieldId.setEditable(false);
        add(this.textFieldId, "cell 1 4,growx");
        this.textFieldId.setColumns(10);

        this.lblModelo = new JLabel("Modelo:");
        this.lblModelo.setFont(new Font("Arial", Font.PLAIN, 15));
        add(this.lblModelo, "cell 0 5,alignx trailing");

        this.textFieldModelo = new JTextField();
        this.textFieldModelo.setFont(new Font("Arial", Font.PLAIN, 15));
        add(this.textFieldModelo, "cell 1 5,growx");
        this.textFieldModelo.setColumns(10);

        this.lblMarca = new JLabel("Marca:");
        this.lblMarca.setFont(new Font("Arial", Font.PLAIN, 15));
        add(this.lblMarca, "cell 0 6,alignx trailing");

        this.textFieldMarca = new JTextField();
        this.textFieldMarca.setFont(new Font("Arial", Font.PLAIN, 15));
        this.textFieldMarca.setColumns(10);
        add(this.textFieldMarca, "cell 1 6,growx");

        this.lblAno = new JLabel("Ano:");
        this.lblAno.setFont(new Font("Arial", Font.PLAIN, 15));
        add(this.lblAno, "cell 0 7,alignx trailing");

        this.textFieldAno = new JTextField();
        this.textFieldAno.setFont(new Font("Arial", Font.PLAIN, 15));
        add(this.textFieldAno, "cell 1 7,growx");
        this.textFieldAno.setColumns(10);

        this.btnAlterar = new JButton("Alterar");
        this.btnAlterar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                alterarVeiculo();
            }
        });
        add(this.btnAlterar, "flowx,cell 1 9,growx");

        this.btnExcluir = new JButton("Excluir");
        this.btnExcluir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                excluirVeiculo();
            }
        });
        add(this.btnExcluir, "cell 1 9,growx");

        carregarComboBox();
    }

    private void carregarComboBox() {
        try {
            List<Automoveis> automoveis = automovelDAO.listar();
            for (Automoveis automovel : automoveis) {
                comboBox.addItem(automovel.getId() + " - " + automovel.getModelo());
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar veículos: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarDadosSelecionados() {
        String selecionado = (String) comboBox.getSelectedItem();
        if (selecionado != null) {
            try {
                int id = Integer.parseInt(selecionado.split(" - ")[0]);
                Automoveis automovel = automovelDAO.buscarPorId(id);

                if (automovel != null) {
                    textFieldId.setText(String.valueOf(automovel.getId()));
                    textFieldModelo.setText(automovel.getModelo());
                    textFieldMarca.setText(automovel.getMarca());
                    textFieldAno.setText(String.valueOf(automovel.getAno()));
                }
            } catch (SQLException | NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Erro ao carregar dados do veículo: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void alterarVeiculo() {
        try {
            int id = Integer.parseInt(textFieldId.getText());
            String modelo = textFieldModelo.getText();
            String marca = textFieldMarca.getText();
            int ano = Integer.parseInt(textFieldAno.getText());

            Automoveis automovel = new Automoveis();
            automovel.setId(id);
            automovel.setModelo(modelo);
            automovel.setMarca(marca);
            automovel.setAno(ano);

            automovelDAO.atualizar(automovel);
            JOptionPane.showMessageDialog(this, "Veículo atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException | NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar veículo: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirVeiculo() {
        try {
            int id = Integer.parseInt(textFieldId.getText());
            automovelDAO.excluir(id);
            JOptionPane.showMessageDialog(this, "Veículo excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            comboBox.removeAllItems();
            carregarComboBox();
        } catch (SQLException | NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir veículo: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
