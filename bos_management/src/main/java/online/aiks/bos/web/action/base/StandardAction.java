package online.aiks.bos.web.action.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import online.aiks.bos.domain.base.Standard;
import online.aiks.bos.service.base.StandardService;
import online.aiks.bos.web.action.common.BaseAction;
@Controller
@ParentPackage("json-default")
@Scope("prototype")
@Namespace("/")
public class StandardAction extends BaseAction<Standard> {
	private static final long serialVersionUID = 1L;

	@Autowired
	private StandardService standardService;

	@Action(value = "standard_save_update", results = {@Result(name = "success", type = "redirect", location = "/pages/base/standard.html")})
	public String saveOrUpdate() {
		standardService.saveOrUpdateStandard(model);
		return SUCCESS;
	}

	@Action(value = "standard_listPage", results = {@Result(name = "json", type = "json")})
	public String listPage() {
		PageRequest pageable = new PageRequest(page - 1, rows);
		Page<Standard> pageResponse = standardService.findStandardListPage(pageable);
		Map<Object, Object> resultMap = new HashMap<>();
		resultMap.put("total", pageResponse.getTotalElements());
		resultMap.put("rows", pageResponse.getContent());
		ActionContext.getContext().getValueStack().push(resultMap);
		return JSON;
	}

	@Action(value = "standard_findAll", results = {@Result(name = "json", type = "json")})
	public String findAll() {
		List<Standard> slist = standardService.findAll();
		ServletActionContext.getContext().getValueStack().push(slist);
		return JSON;
	}

}
