package com.app.server.service.appbasicsetup.userrolemanagement;
import org.springframework.web.bind.annotation.RestController;
import com.app.config.annotation.Complexity;
import com.app.config.annotation.SourceCodeAuthorClass;
import org.springframework.http.HttpStatus;
import com.spartan.pluggable.logger.alarms.AppAlarm;
import com.spartan.pluggable.logger.api.LogManagerFactory;
import com.athena.server.pluggable.utils.AppLoggerConstant;
import com.spartan.pluggable.logger.api.LogManager;
import com.app.server.repository.appbasicsetup.userrolemanagement.UserRoleBridgeRepository;
import com.app.shared.appbasicsetup.userrolemanagement.UserRoleBridge;
import com.athena.server.pluggable.utils.helper.RuntimeLogInfoHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import com.athena.server.pluggable.utils.bean.ResponseBean;
import java.lang.Override;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.List;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PathVariable;
import com.athena.server.pluggable.utils.bean.FindByBean;

@RestController
@SourceCodeAuthorClass(createdBy = "john.doe", updatedBy = "john.doe", versionNumber = "2", comments = "Service for UserRoleBridge Transaction table", complexity = Complexity.MEDIUM)
@RequestMapping("/UserRoleBridge")
public class UserRoleBridgeServiceImpl extends UserRoleBridgeService {

    private LogManager Log = LogManagerFactory.getInstance(AppLoggerConstant.LOGGER_ID);

    @Autowired
    private RuntimeLogInfoHelper runtimeLogInfoHelper;

    @Autowired
    private UserRoleBridgeRepository<UserRoleBridge> userRoleBridgerepo;

    @RequestMapping(value = "/findAll", consumes = "application/json", method = RequestMethod.GET)
    @Override
    public HttpEntity<ResponseBean> findAll() throws Exception {
        java.util.List<com.app.shared.appbasicsetup.userrolemanagement.UserRoleBridge> lstuserrolebridge = userRoleBridgerepo.findAll();
        AppAlarm appAlarm = Log.getAlarm("ABSRM224990200");
        ResponseBean responseBean = new ResponseBean(appAlarm);
        responseBean.add("message", String.format(appAlarm.getMessage(), "UserRoleBridge"));
        responseBean.add("data", lstuserrolebridge);
        Log.out.println(appAlarm.getAlarmID(), runtimeLogInfoHelper.getRequestHeaderBean(), "UserRoleBridgeServiceImpl", "findAll", "UserRoleBridge");
        return new org.springframework.http.ResponseEntity<ResponseBean>(responseBean, HttpStatus.valueOf(appAlarm.getAlarmStatus()));
    }

    @RequestMapping(consumes = "application/json", method = RequestMethod.POST)
    @Override
    public HttpEntity<ResponseBean> save(@RequestBody UserRoleBridge entity) throws Exception {
        userRoleBridgerepo.save(entity);
        AppAlarm appAlarm = Log.getAlarm("ABSRM323900200");
        ResponseBean responseBean = new ResponseBean(appAlarm);
        responseBean.add("message", String.format(appAlarm.getMessage(), "UserRoleBridge"));
        responseBean.add("data", entity);
        Log.out.println(appAlarm.getAlarmID(), runtimeLogInfoHelper.getRequestHeaderBean(), "UserRoleBridgeServiceImpl", "save", "UserRoleBridge");
        return new org.springframework.http.ResponseEntity<ResponseBean>(responseBean, HttpStatus.valueOf(appAlarm.getAlarmStatus()));
    }

    @RequestMapping(consumes = "application/json", headers = { "isArray" }, method = RequestMethod.POST)
    @Override
    public HttpEntity<ResponseBean> save(@RequestBody List<UserRoleBridge> entity, @RequestHeader("isArray") boolean request) throws Exception {
        userRoleBridgerepo.save(entity);
        AppAlarm appAlarm = Log.getAlarm("ABSRM323900200");
        ResponseBean responseBean = new ResponseBean(appAlarm);
        responseBean.add("message", String.format(appAlarm.getMessage(), "UserRoleBridge"));
        Log.out.println(appAlarm.getAlarmID(), runtimeLogInfoHelper.getRequestHeaderBean(), "UserRoleBridgeServiceImpl", "save", "UserRoleBridge");
        return new org.springframework.http.ResponseEntity<ResponseBean>(responseBean, HttpStatus.valueOf(appAlarm.getAlarmStatus()));
    }

    @RequestMapping(value = "/{cid}", consumes = "application/json", method = RequestMethod.DELETE)
    @Override
    public HttpEntity<ResponseBean> delete(@PathVariable("cid") String entity) throws Exception {
        userRoleBridgerepo.delete(entity);
        AppAlarm appAlarm = Log.getAlarm("ABSRM328900200");
        ResponseBean responseBean = new ResponseBean(appAlarm);
        responseBean.add("message", String.format(appAlarm.getMessage(), "UserRoleBridge"));
        Log.out.println(appAlarm.getAlarmID(), runtimeLogInfoHelper.getRequestHeaderBean(), "UserRoleBridgeServiceImpl", "delete", "UserRoleBridge");
        return new org.springframework.http.ResponseEntity<ResponseBean>(responseBean, HttpStatus.valueOf(appAlarm.getAlarmStatus()));
    }

    @RequestMapping(consumes = "application/json", method = RequestMethod.PUT)
    @Override
    public HttpEntity<ResponseBean> update(@RequestBody UserRoleBridge entity) throws Exception {
        userRoleBridgerepo.update(entity);
        AppAlarm appAlarm = Log.getAlarm("ABSRM221990200");
        ResponseBean responseBean = new ResponseBean(appAlarm);
        responseBean.add("message", String.format(appAlarm.getMessage(), "UserRoleBridge"));
        responseBean.add("data", entity._getPrimarykey());
        Log.out.println(appAlarm.getAlarmID(), runtimeLogInfoHelper.getRequestHeaderBean(), "UserRoleBridgeServiceImpl", "update", "UserRoleBridge");
        return new org.springframework.http.ResponseEntity<ResponseBean>(responseBean, HttpStatus.valueOf(appAlarm.getAlarmStatus()));
    }

    @RequestMapping(consumes = "application/json", headers = { "isArray" }, method = RequestMethod.PUT)
    @Override
    public HttpEntity<ResponseBean> update(@RequestBody List<UserRoleBridge> entity, @RequestHeader("isArray") boolean request) throws Exception {
        userRoleBridgerepo.update(entity);
        AppAlarm appAlarm = Log.getAlarm("ABSRM221990200");
        ResponseBean responseBean = new ResponseBean(appAlarm);
        responseBean.add("message", String.format(appAlarm.getMessage(), "UserRoleBridge"));
        Log.out.println(appAlarm.getAlarmID(), runtimeLogInfoHelper.getRequestHeaderBean(), "UserRoleBridgeServiceImpl", "update", "UserRoleBridge");
        return new org.springframework.http.ResponseEntity<ResponseBean>(responseBean, HttpStatus.valueOf(appAlarm.getAlarmStatus()));
    }

    @RequestMapping(value = "/findByRoleId", method = RequestMethod.POST)
    @Override
    public HttpEntity<ResponseBean> findByRoleId(@RequestBody FindByBean findByBean) throws Exception {
        org.springframework.http.HttpStatus httpStatus = org.springframework.http.HttpStatus.OK;
        List<com.app.shared.appbasicsetup.userrolemanagement.UserRoleBridge> lstuserrolebridge = userRoleBridgerepo.findByRoleId((java.lang.String) findByBean.getFindKey());
        AppAlarm appAlarm = Log.getAlarm("ABSRM224990200");
        com.athena.server.pluggable.utils.bean.ResponseBean responseBean = new ResponseBean(appAlarm);
        responseBean.add("message", String.format(appAlarm.getMessage(), "UserRoleBridge"));
        responseBean.add("data", lstuserrolebridge);
        Log.out.println("ABSRM224990200", runtimeLogInfoHelper.getRequestHeaderBean(), "UserRoleBridgeServiceImpl", "save", "UserRoleBridge");
        return new org.springframework.http.ResponseEntity<ResponseBean>(responseBean, HttpStatus.valueOf(appAlarm.getAlarmStatus()));
    }

    @RequestMapping(value = "/findByUserId", method = RequestMethod.POST)
    @Override
    public HttpEntity<ResponseBean> findByUserId(@RequestBody FindByBean findByBean) throws Exception {
        org.springframework.http.HttpStatus httpStatus = org.springframework.http.HttpStatus.OK;
        List<com.app.shared.appbasicsetup.userrolemanagement.UserRoleBridge> lstuserrolebridge = userRoleBridgerepo.findByUserId((java.lang.String) findByBean.getFindKey());
        AppAlarm appAlarm = Log.getAlarm("ABSRM224990200");
        com.athena.server.pluggable.utils.bean.ResponseBean responseBean = new ResponseBean(appAlarm);
        responseBean.add("message", String.format(appAlarm.getMessage(), "UserRoleBridge"));
        responseBean.add("data", lstuserrolebridge);
        Log.out.println("ABSRM224990200", runtimeLogInfoHelper.getRequestHeaderBean(), "UserRoleBridgeServiceImpl", "save", "UserRoleBridge");
        return new org.springframework.http.ResponseEntity<ResponseBean>(responseBean, HttpStatus.valueOf(appAlarm.getAlarmStatus()));
    }

    @RequestMapping(value = "/findById", method = RequestMethod.POST)
    @Override
    public HttpEntity<ResponseBean> findById(@RequestBody FindByBean findByBean) throws Exception {
        org.springframework.http.HttpStatus httpStatus = org.springframework.http.HttpStatus.OK;
        com.app.shared.appbasicsetup.userrolemanagement.UserRoleBridge lstuserrolebridge = userRoleBridgerepo.findById((java.lang.String) findByBean.getFindKey());
        AppAlarm appAlarm = Log.getAlarm("ABSRM224990200");
        com.athena.server.pluggable.utils.bean.ResponseBean responseBean = new ResponseBean(appAlarm);
        responseBean.add("message", String.format(appAlarm.getMessage(), "UserRoleBridge"));
        responseBean.add("data", lstuserrolebridge);
        Log.out.println("ABSRM224990200", runtimeLogInfoHelper.getRequestHeaderBean(), "UserRoleBridgeServiceImpl", "save", "UserRoleBridge");
        return new org.springframework.http.ResponseEntity<ResponseBean>(responseBean, HttpStatus.valueOf(appAlarm.getAlarmStatus()));
    }
}
