package bancario.projeto.persistencia;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import bancario.projeto.model.Cliente;

public class PersistenciaCliente {
	
	private ArrayList<Cliente> clientes;
	private static final String FILE_PATH = "clientes.dat";
	
	public PersistenciaCliente() {
		clientes = new ArrayList<>();
		clientes = carregarClientes();
	}
	
	
	//--------------------------------------------------------------------------------
	
	
	public void adicionarCliente(Cliente c) {
		if(clientes.contains(c)) {
			System.out.println("Cliente já cadastrado");
		} else {
			clientes.add(c);
			System.out.println("Cliente cadastrado com sucesso");
		}
	}
	
	public void removerCliente(Cliente c) {
		for (int i = 0; i < clientes.size(); i++) {
		    Cliente cliente = clientes.get(i);
		    if (cliente.getCpf().equals(c.getCpf())) {
		        clientes.remove(i);
		        System.out.println("Cliente removido com sucesso");
		        return;
		    }
		}
		System.out.println("Este cliente não existe");
	}
	
	public Cliente localizarClientePorCpf(String cpf) {
	    for (int i = 0; i < clientes.size(); i++) {
	        Cliente cliente = clientes.get(i); 
	        if (cliente.getCpf().equals(cpf)) {
	            return cliente; 
	        }
	    }
	    return null; 
	}

	public void atualizarCliente(Cliente c) {
		if(clientes.contains(c)) {
			int index = clientes.indexOf(c);
			clientes.set(index, c);
			System.out.println("Cliente foi atualizada");
		} else {
			System.out.println("Cliente não encontrado");
		}
	}
	
	
	//-------------------------------------------------------------------------
	
	
	public ArrayList<Cliente> listarClientes() {
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado");
        }
        return clientes;
    }
	
	public void salvarClientes() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(clientes);
            System.out.println("Dados salvos com sucesso.");
        } catch (IOException e) {
            System.err.println("Erro ao salvar os dados: " + e.getMessage());
        }
    }
	
	@SuppressWarnings("unchecked")
	private ArrayList<Cliente> carregarClientes() {
		File file = new File(FILE_PATH);
		if (!file.exists()) {
			return new ArrayList<>();
		}
		 
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
			return (ArrayList<Cliente>) ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			System.err.println("Erro ao carregar os dados: " + e.getMessage());
			return new ArrayList<>();
		}
	}
	 
		
}