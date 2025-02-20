package bancario.projeto.persistencia;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import bancario.projeto.model.Cliente;


public class PersistenciaCliente {
	
	private static final String FILE_PATH = "clientes.dat";
		
	public static void salvarClientes(ArrayList<Cliente> clientes) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(clientes);
            System.out.println("Dados salvos com sucesso.");
        } catch (IOException e) {
            System.err.println("Erro ao salvar os dados: " + e.getMessage());
        }
    }
	
	@SuppressWarnings("unchecked")
	public static ArrayList<Cliente> carregarClientes() {
		ArrayList<Cliente> clientes = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            clientes = (ArrayList<Cliente>) ois.readObject(); 
            System.out.println("Dados carregados com sucesso!");
        } catch (FileNotFoundException e) {
            System.out.println("Nenhum dado encontrado. Um novo arquivo será criado ao salvar.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar os dados: " + e.getMessage());
        }
        return clientes;
	}
	
		
}