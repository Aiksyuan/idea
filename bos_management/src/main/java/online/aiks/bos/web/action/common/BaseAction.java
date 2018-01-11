package online.aiks.bos.web.action.common;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.springframework.data.domain.Page;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

//通用action
public class BaseAction<T> extends ActionSupport implements ModelDriven<T> {
	private static final long serialVersionUID = 1L;
	public final String JSON = "json";
	public final String REDIRECT = "redirect";

	// 声明一个数据模型对象
	protected T model;
	@Override
	public T getModel() {
		return model;
	}
	protected Integer page;
	protected Integer rows;

	protected String ids;

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	// 默认构造器
	public BaseAction() {
		// 在默认构造器中初始化数据模型对象
		// 获取子类的父类BaseAction<T>的类型
		// Type是类型的祖宗，class和ParameterizedType都是其子类型
		Type superType = this.getClass().getGenericSuperclass();
		// 强转为泛型化类型
		ParameterizedType parameterizedType = (ParameterizedType) superType;
		// 获取T泛型的具体类型
		Class<T> modelClass = (Class<T>) parameterizedType.getActualTypeArguments()[0];

		// 实例化数据模型
		try {
			model = modelClass.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

	}

	public void pushPageDataToValuestackRoot(Page<T> pageResponse) {
		Map<String, Object> map = new HashMap<>();
		map.put("total", pageResponse.getTotalElements());
		map.put("rows", pageResponse.getContent());
		ServletActionContext.getContext().getValueStack().push(map);

	}

}
