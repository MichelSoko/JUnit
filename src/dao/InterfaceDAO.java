package dao;

import java.util.List;

public interface InterfaceDAO<T> {
	
	// la classe DAO fournit les opérations CRUD (Créer, Lire, Mettre à jour, Supprimer)
	
	public boolean add(T t) throws Exception;
	public boolean update(T t) throws Exception;
	public boolean delete(T t) throws Exception;
	
	public List<T> getAll() throws Exception;
	public T getById(int id) throws Exception;
	
	public boolean exist(T t) throws Exception;
	
}
