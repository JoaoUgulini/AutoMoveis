import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaPrincipal extends JFrame {
    private JPanel contentPane;
    private JMenuBar menuBar;
    private JMenuItem mntmNewMenuItem;
    private JMenuItem mntmNewMenuItem_1;
    private JMenuItem mntmNewMenuItem_2;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        // Configura o LookAndFeel Nimbus, disponível nativamente no Java
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Falha ao carregar Nimbus LookAndFeel!");
        }

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    TelaPrincipal frame = new TelaPrincipal();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public TelaPrincipal() {
        // Configurações da janela principal
        setTitle("Bem-Vindo à Concessionária Katchau");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 595, 440);

        // Configurações da barra de menu
        this.menuBar = new JMenuBar();
        setJMenuBar(this.menuBar);

        this.mntmNewMenuItem = new JMenuItem("Cadastro Veículos");
        this.mntmNewMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                trocaTela(new TelaCadastro());
            }
        });
        this.menuBar.add(this.mntmNewMenuItem);

        this.mntmNewMenuItem_1 = new JMenuItem("Consultar Veículos");
        this.mntmNewMenuItem_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                trocaTela(new TelaConsultar());
            }
        });
        this.menuBar.add(this.mntmNewMenuItem_1);

        this.mntmNewMenuItem_2 = new JMenuItem("Alterar e Excluir Veículos");
        this.mntmNewMenuItem_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                trocaTela(new TelaAlterarExcluir());
            }
        });
        this.menuBar.add(this.mntmNewMenuItem_2);


        this.contentPane = new JPanel();
        this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(this.contentPane);

        JLabel welcomeLabel = new JLabel("Bem-Vindo à Concessionária Katchau!", SwingConstants.CENTER);
        welcomeLabel.setFont(welcomeLabel.getFont().deriveFont(18f)); 
        this.contentPane.add(welcomeLabel, BorderLayout.CENTER);
    }

  
    private void trocaTela(JPanel panel) {
        this.contentPane.removeAll();
        this.contentPane.add(panel, BorderLayout.CENTER);
        this.contentPane.revalidate();
        this.contentPane.repaint();
    }
}