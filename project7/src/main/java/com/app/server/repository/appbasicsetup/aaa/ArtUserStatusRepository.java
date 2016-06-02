package com.app.server.repository.appbasicsetup.aaa;
import com.app.shared.appbasicsetup.aaa.ArtUserLastStatus;

import java.util.List;


public interface ArtUserStatusRepository {

	public ArtUserLastStatus findById(String id) throws Exception;
	
	public void save(ArtUserLastStatus userLastStatus) throws Exception;

	public void update(ArtUserLastStatus userLastStatus) throws Exception;
	
	public void delete(String id) throws Exception;

	public List<ArtUserLastStatus> findByUserId(String userId) throws Exception;

	public List<ArtUserLastStatus> findByUserMenuId(String userId, String menuId) throws Exception;
}
