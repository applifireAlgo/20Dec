package com.app.server.repository.appbasicsetup.usermanagement;import java.util.List;


public interface ArtPasswordAlgorithmRepository<T>  {
	 public List<T> findAll() throws Exception;

	    public T save(T entity) throws Exception;

	    public List<T> save(List<T> entity) throws Exception;

	    public void delete(String id) throws Exception;

	    public void update(T entity) throws Exception;

	    public void update(List<T> entity) throws Exception;

		public T findById(String id) throws Exception;

}
	
