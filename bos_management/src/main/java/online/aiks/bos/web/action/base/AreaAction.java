package online.aiks.bos.web.action.base;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import com.opensymphony.xwork2.ActionContext;

import online.aiks.bos.domain.base.Area;
import online.aiks.bos.service.base.AreaService;
import online.aiks.bos.web.action.common.BaseAction;
@Controller
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/")
public class AreaAction extends BaseAction<Area> {
	@Autowired
	private AreaService areaService;
	private static final long serialVersionUID = 1L;
	private File upload;// 文件
	private String uploadFileName;// 文件名字
	private String uploadContentType;// 文件类型
	public void setUpload(File upload) {
		this.upload = upload;
	}
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	@Action(value = "area_importData", results = {@Result(name = "json", type = "json")})
	public String importData() {
		System.out.println(upload);
		ArrayList<Area> areaList = new ArrayList<>();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook(new FileInputStream(upload));
			HSSFSheet sheetAt = hssfWorkbook.getSheetAt(0);
			for (Row row : sheetAt) {
				if (row.getRowNum() == 0) {
					continue;
				}
				Area area = new Area();
				String id = row.getCell(0).getStringCellValue();
				String province = row.getCell(1).getStringCellValue();
				String city = row.getCell(2).getStringCellValue();
				String district = row.getCell(3).getStringCellValue();
				String postcode = row.getCell(4).getStringCellValue();

				area.setId(id);
				area.setProvince(province);
				area.setCity(city);
				area.setDistrict(district);
				area.setPostcode(postcode);

				String provinceStr = StringUtils.substring(province, 0, -1);// 北京市,>>>北京
				String cityStr = StringUtils.substring(city, 0, -1);
				String districtStr = StringUtils.substring(district, 0, -1);

				String shortcode = PinyinHelper.getShortPinyin(provinceStr + cityStr + district).toUpperCase();
				String citycode = PinyinHelper.convertToPinyinString(cityStr, "", PinyinFormat.WITHOUT_TONE);
				area.setCitycode(citycode);
				area.setShortcode(shortcode);

				areaList.add(area);

			}
			areaService.saveArea(areaList);
			map.put("result", true);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", false);
		}
		ActionContext.getContext().getValueStack().push(map);
		return JSON;

	}

}
