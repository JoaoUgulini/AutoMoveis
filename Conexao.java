import java.sql.Connection;
import java.sql.DriverManager;


public class Conexao {
    public static Connection Abrirconexao(){
        Connection con=null;
        try{
            Class.forName("org.postgresql.Driver").newInstance();
            String URL="jdbc:postgresql://localhost:5432/Automoveis";
            String usuario="postgres";
            String senha="ciet";
            con=DriverManager.getConnection(URL,usuario,senha);
            System.out.println("Conexao realizada");
        }catch(Exception ex){
            System.out.println("Erro : "+ex.getMessage());
        }
        return con;

    }
}