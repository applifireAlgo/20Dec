package com.app.server.service.organization.contactmanagement;
import org.springframework.web.bind.annotation.RestController;
import com.app.config.annotation.Complexity;
import com.app.config.annotation.SourceCodeAuthorClass;
import com.app.server.businessservice.organization.contactmanagement.CoreContactsBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import com.spartan.pluggable.logger.alarms.AppAlarm;
import com.spartan.pluggable.logger.api.LogManagerFactory;
import com.athena.server.pluggable.utils.AppLoggerConstant;
import com.spartan.pluggable.logger.api.LogManager;
import com.app.server.repository.organization.contactmanagement.CoreContactsRepository;
import com.app.shared.organization.contactmanagement.CoreContacts;
import com.athena.server.pluggable.utils.helper.RuntimeLogInfoHelper;
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
@SourceCodeAuthorClass(createdBy = "john.doe", updatedBy = "john.doe", versionNumber = "2", comments = "Service for CoreContacts Transaction table", complexity = Complexity.MEDIUM)
@RequestMapping("/CoreContacts")
public class CoreContactsServiceImpl extends CoreContactsService {

    @Autowired
    private CoreContactsBusinessService corecontactsbz;

    private LogManager Log = LogManagerFactory.getInstance(AppLoggerConstant.LOGGER_ID);

    @Autowired
    private RuntimeLogInfoHelper runtimeLogInfoHelper;

    @Autowired
    private CoreContactsRepository<CoreContacts> coreContactsrepo;

    @RequestMapping(value = "/findAll", consumes = "application/json", method = RequestMethod.GET)
    @Override
    public HttpEntity<ResponseBean> findAll() throws Exception {
        java.util.List<com.app.shared.organization.contactmanagement.CoreContacts> lstcorecontacts = coreContactsrepo.findAll();
        AppAlarm appAlarm = Log.getAlarm("ORGCM124990200");
        ResponseBean responseBean = new ResponseBean(appAlarm);
        responseBean.add("message", String.format(appAlarm.getMessage(), "CoreContacts"));
        responseBean.add("data", lstcorecontacts);
        Log.out.println(appAlarm.getAlarmID(), runtimeLogInfoHelper.getRequestHeaderBean(), "CoreContactsServiceImpl", "findAll", "CoreContacts");
        return new org.springframework.http.ResponseEntity<ResponseBean>(responseBean, HttpStatus.valueOf(appAlarm.getAlarmStatus()));
    }

    @RequestMapping(consumes = "application/json", method = RequestMethod.POST)
    @Override
    public HttpEntity<ResponseBean> save(@RequestBody CoreContacts entity) throws Exception {
        coreContactsrepo.save(entity);
        AppAlarm appAlarm = Log.getAlarm("ORGCM122990200");
        ResponseBean responseBean = new ResponseBean(appAlarm);
        responseBean.add("message", String.format(appAlarm.getMessage(), "CoreContacts"));
        responseBean.add("data", entity);
        Log.out.println(appAlarm.getAlarmID(), runtimeLogInfoHelper.getRequestHeaderBean(), "CoreContactsServiceImpl", "save", "CoreContacts");
        return new org.springframework.http.ResponseEntity<ResponseBean>(responseBean, HttpStatus.valueOf(appAlarm.getAlarmStatus()));
    }

    @RequestMapping(consumes = "application/json", headers = { "isArray" }, method = RequestMethod.POST)
    @Override
    public HttpEntity<ResponseBean> save(@RequestBody List<CoreContacts> entity, @RequestHeader("isArray") boolean request) throws Exception {
        coreContactsrepo.save(entity);
        AppAlarm appAlarm = Log.getAlarm("ORGCM122990200");
        ResponseBean responseBean = new ResponseBean(appAlarm);
        responseBean.add("message", String.format(appAlarm.getMessage(), "CoreContacts"));
        Log.out.println(appAlarm.getAlarmID(), runtimeLogInfoHelper.getRequestHeaderBean(), "CoreContactsServiceImpl", "save", "CoreContacts");
        return new org.springframework.http.ResponseEntity<ResponseBean>(responseBean, HttpStatus.valueOf(appAlarm.getAlarmStatus()));
    }

    @RequestMapping(value = "/{cid}", consumes = "application/json", method = RequestMethod.DELETE)
    @Override
    public HttpEntity<ResponseBean> delete(@PathVariable("cid") String entity) throws Exception {
        coreContactsrepo.delete(entity);
        AppAlarm appAlarm = Log.getAlarm("ORGCM128990200");
        ResponseBean responseBean = new ResponseBean(appAlarm);
        responseBean.add("message", String.format(appAlarm.getMessage(), "CoreContacts"));
        Log.out.println(appAlarm.getAlarmID(), runtimeLogInfoHelper.getRequestHeaderBean(), "CoreContactsServiceImpl", "delete", "CoreContacts");
        return new org.springframework.http.ResponseEntity<ResponseBean>(responseBean, HttpStatus.valueOf(appAlarm.getAlarmStatus()));
    }

    @RequestMapping(consumes = "application/json", method = RequestMethod.PUT)
    @Override
    public HttpEntity<ResponseBean> update(@RequestBody CoreContacts entity) throws Exception {
        corecontactsbz.update(entity);
        AppAlarm appAlarm = Log.getAlarm("ORGCM121990200");
        ResponseBean responseBean = new ResponseBean(appAlarm);
        responseBean.add("message", String.format(appAlarm.getMessage(), "CoreContacts"));
        responseBean.add("data", entity._getPrimarykey());
        Log.out.println(appAlarm.getAlarmID(), runtimeLogInfoHelper.getRequestHeaderBean(), "CoreContactsServiceImpl", "update", "CoreContacts");
        return new org.springframework.http.ResponseEntity<ResponseBean>(responseBean, HttpStatus.valueOf(appAlarm.getAlarmStatus()));
    }

    @RequestMapping(consumes = "application/json", headers = { "isArray" }, method = RequestMethod.PUT)
    @Override
    public HttpEntity<ResponseBean> update(@RequestBody List<CoreContacts> entity, @RequestHeader("isArray") boolean request) throws Exception {
        corecontactsbz.update(entity);
        AppAlarm appAlarm = Log.getAlarm("ORGCM121990200");
        ResponseBean responseBean = new ResponseBean(appAlarm);
        responseBean.add("message", String.format(appAlarm.getMessage(), "CoreContacts"));
        Log.out.println(appAlarm.getAlarmID(), runtimeLogInfoHelper.getRequestHeaderBean(), "CoreContactsServiceImpl", "update", "CoreContacts");
        return new org.springframework.http.ResponseEntity<ResponseBean>(responseBean, HttpStatus.valueOf(appAlarm.getAlarmStatus()));
    }

    @RequestMapping(value = "/findByTitleId", method = RequestMethod.POST)
    @Override
    public HttpEntity<ResponseBean> findByTitleId(@RequestBody FindByBean findByBean) throws Exception {
        org.springframework.http.HttpStatus httpStatus = org.springframework.http.HttpStatus.OK;
        List<com.app.shared.organization.contactmanagement.CoreContacts> lstcorecontacts = coreContactsrepo.findByTitleId((java.lang.String) findByBean.getFindKey());
        AppAlarm appAlarm = Log.getAlarm("ORGCM124990200");
        com.athena.server.pluggable.utils.bean.ResponseBean responseBean = new ResponseBean(appAlarm);
        responseBean.add("message", String.format(appAlarm.getMessage(), "CoreContacts"));
        responseBean.add("data", lstcorecontacts);
        Log.out.println("ORGCM124990200", runtimeLogInfoHelper.getRequestHeaderBean(), "CoreContactsServiceImpl", "save", "CoreContacts");
        return new org.springframework.http.ResponseEntity<ResponseBean>(responseBean, HttpStatus.valueOf(appAlarm.getAlarmStatus()));
    }

    @RequestMapping(value = "/findByNativeLanguageCode", method = RequestMethod.POST)
    @Override
    public HttpEntity<ResponseBean> findByNativeLanguageCode(@RequestBody FindByBean findByBean) throws Exception {
        org.springframework.http.HttpStatus httpStatus = org.springframework.http.HttpStatus.OK;
        List<com.app.shared.organization.contactmanagement.CoreContacts> lstcorecontacts = coreContactsrepo.findByNativeLanguageCode((java.lang.String) findByBean.getFindKey());
        AppAlarm appAlarm = Log.getAlarm("ORGCM124990200");
        com.athena.server.pluggable.utils.bean.ResponseBean responseBean = new ResponseBean(appAlarm);
        responseBean.add("message", String.format(appAlarm.getMessage(), "CoreContacts"));
        responseBean.add("data", lstcorecontacts);
        Log.out.println("ORGCM124990200", runtimeLogInfoHelper.getRequestHeaderBean(), "CoreContactsServiceImpl", "save", "CoreContacts");
        return new org.springframework.http.ResponseEntity<ResponseBean>(responseBean, HttpStatus.valueOf(appAlarm.getAlarmStatus()));
    }

    @RequestMapping(value = "/findByGenderId", method = RequestMethod.POST)
    @Override
    public HttpEntity<ResponseBean> findByGenderId(@RequestBody FindByBean findByBean) throws Exception {
        org.springframework.http.HttpStatus httpStatus = org.springframework.http.HttpStatus.OK;
        List<com.app.shared.organization.contactmanagement.CoreContacts> lstcorecontacts = coreContactsrepo.findByGenderId((java.lang.String) findByBean.getFindKey());
        AppAlarm appAlarm = Log.getAlarm("ORGCM124990200");
        com.athena.server.pluggable.utils.bean.ResponseBean responseBean = new ResponseBean(appAlarm);
        responseBean.add("message", String.format(appAlarm.getMessage(), "CoreContacts"));
        responseBean.add("data", lstcorecontacts);
        Log.out.println("ORGCM124990200", runtimeLogInfoHelper.getRequestHeaderBean(), "CoreContactsServiceImpl", "save", "CoreContacts");
        return new org.springframework.http.ResponseEntity<ResponseBean>(responseBean, HttpStatus.valueOf(appAlarm.getAlarmStatus()));
    }

    @RequestMapping(value = "/findByTimeZoneId", method = RequestMethod.POST)
    @Override
    public HttpEntity<ResponseBean> findByTimeZoneId(@RequestBody FindByBean findByBean) throws Exception {
        org.springframework.http.HttpStatus httpStatus = org.springframework.http.HttpStatus.OK;
        List<com.app.shared.organization.contactmanagement.CoreContacts> lstcorecontacts = coreContactsrepo.findByTimeZoneId((java.lang.String) findByBean.getFindKey());
        AppAlarm appAlarm = Log.getAlarm("ORGCM124990200");
        com.athena.server.pluggable.utils.bean.ResponseBean responseBean = new ResponseBean(appAlarm);
        responseBean.add("message", String.format(appAlarm.getMessage(), "CoreContacts"));
        responseBean.add("data", lstcorecontacts);
        Log.out.println("ORGCM124990200", runtimeLogInfoHelper.getRequestHeaderBean(), "CoreContactsServiceImpl", "save", "CoreContacts");
        return new org.springframework.http.ResponseEntity<ResponseBean>(responseBean, HttpStatus.valueOf(appAlarm.getAlarmStatus()));
    }

    @RequestMapping(value = "/findById", method = RequestMethod.POST)
    @Override
    public HttpEntity<ResponseBean> findById(@RequestBody FindByBean findByBean) throws Exception {
        org.springframework.http.HttpStatus httpStatus = org.springframework.http.HttpStatus.OK;
        com.app.shared.organization.contactmanagement.CoreContacts lstcorecontacts = coreContactsrepo.findById((java.lang.String) findByBean.getFindKey());
        AppAlarm appAlarm = Log.getAlarm("ORGCM124990200");
        com.athena.server.pluggable.utils.bean.ResponseBean responseBean = new ResponseBean(appAlarm);
        responseBean.add("message", String.format(appAlarm.getMessage(), "CoreContacts"));
        responseBean.add("data", lstcorecontacts);
        Log.out.println("ORGCM124990200", runtimeLogInfoHelper.getRequestHeaderBean(), "CoreContactsServiceImpl", "save", "CoreContacts");
        return new org.springframework.http.ResponseEntity<ResponseBean>(responseBean, HttpStatus.valueOf(appAlarm.getAlarmStatus()));
    }
}
