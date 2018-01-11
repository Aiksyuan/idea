package online.aiks.bos.web.action.base;

import java.util.Collection;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import online.aiks.bos.domain.base.FixedArea;
import online.aiks.bos.service.base.FixedAreaService;
import online.aiks.bos.web.action.common.BaseAction;
import online.aiks.crm.domain.Customer;
@Controller
@ParentPackage("json-default")
@Scope("prototype")
@Namespace("/")
public class FixedAreaAction extends BaseAction<FixedArea> {
	private static final long serialVersionUID = 1L;
	private String[] customerIds;

	public void setCustomerIds(String[] customerIds) {
		this.customerIds = customerIds;
	}
	@Autowired
	private FixedAreaService fixAreaService;
	@Action(value = "fixedArea_save_update", results = {
		@Result(name = "success", type = "redirect", location = "/pages/base/fixed_area.html")})
	public String saveOrUpdate() {
		fixAreaService.saveOrUpdate(model);
		return SUCCESS;
	}

	//
	/**
	 * 说明：
	 * 
	 * @return
	 * @author 上海.AIKS future
	 * @time：2018年1月9日 上午11:00:57
	 */
	@Action(value = "fixedArea_pageQuery", results = {@Result(name = "json", type = "json")})
	public String pageList() {
		System.out.println("定区分页");
		PageRequest pageRequest = new PageRequest(page - 1, rows);
		Specification<FixedArea> specification = new Specification<FixedArea>() {

			@Override
			public Predicate toPredicate(Root<FixedArea> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// TODO Auto-generated method stub

				Predicate conjunction = cb.conjunction();
				Predicate disjunction = cb.disjunction();

				if (StringUtils.isNotBlank(model.getId())) {
					conjunction.getExpressions().add(cb.equal(root.get("id").as(String.class), model.getId()));
				}
				if (StringUtils.isNotBlank(model.getCompany())) {
					conjunction.getExpressions()
						.add(cb.like(root.get("company").as(String.class), "%" + model.getCompany() + "%"));
				}

				return conjunction;
			}
		};
		Page<FixedArea> pageResponse = fixAreaService.pagequery(specification, pageRequest);
		pushPageDataToValuestackRoot(pageResponse);
		return JSON;
	}
	@Action(value = "fixedArea_listCustomerListNoFixedAreaId", results = {@Result(name = "json", type = "json")})
	public String listCustomerListNoFixedAreaId() {
		// 远程调用RESTful接口
		Collection<? extends Customer> customerList1 = WebClient
			.create("http://localhost:9002/crm_management/services/customerservice/customers").path("/nofixedarea")
			.accept(MediaType.APPLICATION_JSON).getCollection(Customer.class);

		System.out.println(customerList1);
		// 压入root栈顶
		ActionContext.getContext().getValueStack().push(customerList1);

		return JSON;

	}
	@Action(value = "fixedArea_listCustomerListByFixedAreaId", results = {@Result(name = "json", type = "json")})
	public String listCustomerListByFixedAreaId() {
		// 远程调用Restful接口
		Collection<? extends Customer> customerList = WebClient
			.create("http://localhost:9002/crm_management/services/customerservice/customers").path("/fixedareaId")
			.path("/" + model.getId()).type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
			.getCollection(Customer.class);
		ActionContext.getContext().getValueStack().push(customerList);
		return JSON;
	}
	@Action(value = "fixedArea_associationCustomersToFixedArea", results = {
		@Result(type = "redirect", location = "/pages/base/fixed_area.html")})
	public String associatonCustomersToFixedArea() {
		// 将客户数据重新组装为逗号分隔
		String cIds = StringUtils.join(customerIds, ",");
		// 远程调用,RESTFul接口
		WebClient.create("http://localhost:9002/crm_management/services/customerservice/customers").path("/fixedareaId")
			.path("/" + model.getId()).path("/" + cIds).type(MediaType.APPLICATION_JSON).put(null);
		return SUCCESS;

	}

}
