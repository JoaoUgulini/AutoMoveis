import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class TelaCadastro extends JPanel {
	private JLabel lblNewLabel;
	private JTextField textField;
	private JLabel lblNewLabel_1;
	private JTextField textField_1;
	private JLabel lblNewLabel_2;
	private JTextField textField_2;
	private JButton btnNewButton;
	private JLabel lblNewLabel_3;

	/**
	 * Create the panel.
	 */
	public TelaCadastro() {
		setLayout(new MigLayout("", "[grow][][grow][grow]", "[grow][][][][grow][][grow]"));
		
		this.lblNewLabel_3 = new JLabel("Cadastrar Veiculos");
		this.lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		this.lblNewLabel_3.setForeground(new Color(0, 0, 0));
		this.lblNewLabel_3.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 20));
		add(this.lblNewLabel_3, "cell 1 0 2 1,growx");
		
		this.lblNewLabel = new JLabel("Modelo: ");
		this.lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 15));
		add(this.lblNewLabel, "cell 1 1");
		
		this.textField = new JTextField();
		this.textField.setFont(new Font("Arial", Font.PLAIN, 15));
		add(this.textField, "cell 2 1,growx");
		this.textField.setColumns(10);
		
		this.lblNewLabel_1 = new JLabel("Marca: ");
		this.lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		this.lblNewLabel_1.setFont(new Font("Arial", Font.PLAIN, 15));
		add(this.lblNewLabel_1, "cell 1 2,alignx trailing");
		
		this.textField_1 = new JTextField();
		this.textField_1.setFont(new Font("Arial", Font.PLAIN, 15));
		add(this.textField_1, "cell 2 2,growx");
		this.textField_1.setColumns(10);
		
		this.lblNewLabel_2 = new JLabel("Ano: ");
		this.lblNewLabel_2.setHorizontalAlignment(SwingConstants.RIGHT);
		this.lblNewLabel_2.setFont(new Font("Arial", Font.PLAIN, 15));
		add(this.lblNewLabel_2, "cell 1 3,alignx trailing");
		
		this.textField_2 = new JTextField();
		this.textField_2.setFont(new Font("Arial", Font.PLAIN, 15));
		add(this.textField_2, "cell 2 3,growx");
		this.textField_2.setColumns(10);
		
		this.btnNewButton = new JButton("Cadastrar Veiculos");
		this.btnNewButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        try {
		            String modelo = textField.getText();
		            String marca = textField_1.getText();
		            int ano = Integer.parseInt(textField_2.getText());

		            if (modelo.isEmpty() || marca.isEmpty()) {
		                JOptionPane.showMessageDialog(null, "Modelo e Marca são obrigatrios!");
		                return;
		            }

		            Automoveis automovel = new Automoveis(modelo, marca, ano);
		            AutomovelDAO dao = new AutomovelDAO();
		            dao.inserir(automovel);

		            JOptionPane.showMessageDialog(null, "Veiculo cadastrado com sucesso!");
		            textField.setText("");
		            textField_1.setText("");
		            textField_2.setText("");
		        } catch (NumberFormatException ex) {
		            JOptionPane.showMessageDialog(null, "Ano deve ser um numero valido!");
		        } catch (SQLException ex) {
		            JOptionPane.showMessageDialog(null, "Erro ao cadastrar veiculo: " + ex.getMessage());
		        }
		    }
		});
		add(this.btnNewButton, "cell 1 5 2 1,grow");

	}
}
