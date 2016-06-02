package com.app.server.repository.appbasicsetup.userrolemanagement;
import java.util.List;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.athena.server.pluggable.utils.helper.ResourceFactoryManagerHelper;


@Repository
@Transactional
public class MenuRepositoryImpl implements MenuRepository 
{
	@Autowired
	private ResourceFactoryManagerHelper emfResource;

	// @Override
	public List<String> getHeaderByUser(String userId, Integer appType) throws Exception {
		try {
			//menu.appType =:appType
			javax.persistence.EntityManager entityManager = emfResource.getResource();
			/*String JPQL = "SELECT menu.menuTreeId FROM AppMenus menu, UserRoleBridge userRole, RoleMenuBridge roleMenu WHERE menu.appType =:appType AND menu.systemInfo.activeStatus =:activeStatus AND menu.menuHead =:headMenu AND"
					+ " menu.menuId=roleMenu.menuId AND roleMenu.roles.roleId = userRole.roleId AND userRole.userId =:userId order by menu.menuTreeId";*/
			String JPQL = "SELECT menu.menuTreeId FROM AppMenus menu WHERE menu.systemInfo.activeStatus =:activeStatus AND menu.menuHead =:headMenu AND menu.appType =:appType AND "
					+ " menu.menuId IN(SELECT DISTINCT(roleMenu.menuId) FROM RoleMenuBridge roleMenu, UserRoleBridge userRole WHERE roleMenu.roles.roleId = userRole.roleId AND userRole.userId =:userId) order by menu.menuTreeId";
			Query query = entityManager.createQuery(JPQL);
			query.setParameter("appType", appType);
			query.setParameter("userId", userId);
			query.setParameter("headMenu", true);
			query.setParameter("activeStatus", 1);
			List<String> menu = query.getResultList();
			return menu;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}

	}

	// @Override	
	public List<Object[]> getSubMenus(String header, String userId, Integer appType) throws Exception {
		try {
			// SELECT menu FROM AwsUserMenu userMenu , AwsMenu menu WHERE
			// menu.menuId = userMenu.menuId AND menu.activeStatus
			// =:activeStatus AND userMenu.userId =:userId AND menu.menuTreeId
			// LIKE :header order by menu.menuTreeId
			javax.persistence.EntityManager entityManager = emfResource.getResource();
			//String JPQL = "SELECT menu.menuId, menu.menuTreeId, menu.menuLabel, menu.menuAction, menu.menuIcon, menu.entityAudit.createdBy, menu.menuCommands, menu.refObjectId FROM  AppMenus menu, RoleMenuBridge roleMenu ,UserRoleBridge userRole  WHERE menu.appType =:appType AND menu.menuId = roleMenu.menuId AND  menu.systemInfo.activeStatus =:activeStatus AND roleMenu.roles.roleId=userRole.roleId AND userRole.userId=:userId AND menu.menuTreeId LIKE :header order by menu.menuTreeId";
			String JPQL = "SELECT menu.menuId, menu.menuTreeId, menu.menuLabel, menu.menuAction, menu.menuIcon, menu.entityAudit.createdBy, menu.menuCommands, menu.refObjectId, menu.autoSave FROM  AppMenus menu"
					+ " WHERE menu.systemInfo.activeStatus =:activeStatus AND menu.appType =:appType AND menu.menuTreeId LIKE :header "
					+ "AND menu.menuId IN(SELECT DISTINCT(roleMenu.menuId) FROM RoleMenuBridge roleMenu, UserRoleBridge userRole WHERE roleMenu.roles.roleId = userRole.roleId AND userRole.userId =:userId) order by menu.menuTreeId";
			Query query = entityManager.createQuery(JPQL);
			query.setParameter("appType", appType);
			query.setParameter("header", header + "%");
			query.setParameter("activeStatus", 1);
			query.setParameter("userId", userId);
			List<Object[]> result = query.getResultList();
			return result;

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}
	
	/** DAO CALL WILL GET THE ALL MENUS INDEPENDENT TO APP TYPE */
	@Override
	public List<String> getHeaderByUser(String userId) throws Exception {
		try {
			javax.persistence.EntityManager entityManager = emfResource.getResource();
			String JPQL = "SELECT menu.menuTreeId FROM AppMenus menu WHERE menu.systemInfo.activeStatus =:activeStatus AND menu.menuHead =:headMenu AND "
					+ " menu.menuId IN(SELECT DISTINCT(roleMenu.menuId) FROM RoleMenuBridge roleMenu, UserRoleBridge userRole WHERE roleMenu.roles.roleId = userRole.roleId AND userRole.userId =:userId) order by menu.menuTreeId";
			Query query = entityManager.createQuery(JPQL);
			query.setParameter("userId", userId);
			query.setParameter("headMenu", true);
			query.setParameter("activeStatus", 1);
			List<String> menu = query.getResultList();
			return menu;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}
	
	/** DAO CALL WILL GET THE ALL SUB MENUS INDEPENDENT TO APP TYPE */
	@Override
	public List<Object[]> getSubMenus(String header, String userId) throws Exception {
		try {
			javax.persistence.EntityManager entityManager = emfResource.getResource();
			String JPQL = "SELECT menu.menuId, menu.menuTreeId, menu.menuLabel, menu.menuAction, menu.menuIcon, menu.entityAudit.createdBy, menu.menuCommands, menu.refObjectId, menu.autoSave, menu.appType FROM  AppMenus menu"
					+ " WHERE menu.systemInfo.activeStatus =:activeStatus AND menu.menuTreeId LIKE :header "
					+ "AND menu.menuId IN(SELECT DISTINCT(roleMenu.menuId) FROM RoleMenuBridge roleMenu, UserRoleBridge userRole WHERE roleMenu.roles.roleId = userRole.roleId AND userRole.userId =:userId) order by menu.menuTreeId";
			Query query = entityManager.createQuery(JPQL);
			query.setParameter("header", header + "%");
			query.setParameter("activeStatus", 1);
			query.setParameter("userId", userId);
			List<Object[]> result = query.getResultList();
			return result;

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}
}
