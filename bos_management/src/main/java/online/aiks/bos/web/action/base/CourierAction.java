package online.aiks.bos.web.action.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
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

import online.aiks.bos.domain.base.Courier;
import online.aiks.bos.domain.base.Standard;
import online.aiks.bos.service.base.CourierService;
import online.aiks.bos.web.action.common.BaseAction;
@Controller
@ParentPackage("json-default")
@Scope("prototype")
@Namespace("/")
public class CourierAction extends BaseAction<Courier> {
	@Autowired
	private CourierService courierService;
	private static final long serialVersionUID = 1L;
	@Action(value = "courier_save_update", results = {@Result(name = "success", type = "redirect", location = "/pages/base/courier.html")})
	public String saveOrUpdate() {
		courierService.saveOrUpdate(model);
		return SUCCESS;

	}

	@Action(value = "courier_listpage", results = {@Result(name = "json", type = "json")})
	public String listPage() {
		/*
		 * PageRequest pageable = new PageRequest(page - 1, rows); Page<Courier>
		 * pageResponse = courierService.findStandardListPage(pageable);
		 * Map<Object, Object> resultMap = new HashMap<>();
		 * resultMap.put("total", pageResponse.getTotalElements());
		 * resultMap.put("rows", pageResponse.getContent());
		 * ActionContext.getContext().getValueStack().push(resultMap);
		 */
		// 请求分页bean的参数
		PageRequest pageable = new PageRequest(page - 1, rows);
		// 业务规范,条件对象
		Specification<Courier> spec = new Specification<Courier>() {

			/*
			 * 参数1:根(主)查询对象 参数2:简单查询条件构造对象(jpa的criteria)
			 * 参数3:复杂查询条件构造对象(jpa的criteria) 返回值:Predicate,最终的条件对象
			 */
			@Override
			public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> andPredicate = new ArrayList<>();
				List<Predicate> orPredicate = new ArrayList<>();
				// 目标:拼接业务条件对象
				if (StringUtils.isNotBlank(model.getCourierNum())) {
					Predicate p = cb.equal(root.get("courierNum").as(String.class), model.getCourierNum());
					andPredicate.add(p);
				}

				// 所属单位
				if (StringUtils.isNotBlank(model.getCompany())) {
					Predicate p = cb.like(root.get("company").as(String.class), "%" + model.getCompany() + "%");
					andPredicate.add(p);
				}
				// 类型
				if (StringUtils.isNotBlank(model.getType())) {
					Predicate p = cb.like(root.get("type").as(String.class), "%" + model.getType() + "%");
					andPredicate.add(p);
				}

				// 多表
				// 收派标准
				if (model.getStandard() != null) {
					// 对象连接
					// 参数1:连接的属性
					// 参数2:连接的类型
					// Join standardJoin =
					// root.join(root.getModel().getSingularAttribute("Standard",
					// Standard.class), JoinType.INNER);
					Join<Courier, Standard> standardJoin = root.join(root.getModel().getSingularAttribute("standard", Standard.class), JoinType.INNER);
					// 收派标准的名字
					if (StringUtils.isNotBlank(model.getStandard().getId().toString())) {
						// name是连接对象的属性
						Predicate p = cb.like(standardJoin.get("id").as(String.class), "%" + model.getStandard().getId() + "%");
						andPredicate.add(p);

					}
					// --单表
					// 工号

				}
				Predicate andP = cb.and(andPredicate.toArray(new Predicate[0]));

				return andP;
			}
		};
		Page<Courier> pageResponse = courierService.findCourierListPage(spec, pageable);
		pushPageDataToValuestackRoot(pageResponse);

		return JSON;
	}

	@Action(value = "courier_deleteBath", results = {@Result(name = "json", type = "json")})
	public String deleteBath() {
		HashMap<String, Object> resultMap = new HashMap<>();
		try {
			courierService.deleteBath(ids);
			resultMap.put("result", true);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("result", false);
		}
		ServletActionContext.getContext().getValueStack().push(resultMap);
		return JSON;
	}

}
