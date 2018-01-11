package online.aiks.bos.dao.base;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import online.aiks.bos.domain.base.Standard;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class StandardRepositoryTest {
	@Autowired
	private StandardRepository standardRepository;
	/**
	 * 保存
	 */
	// 保存测试
	@Test
	public void testSave() {
		Standard standard = new Standard();
		// standard.setId(id);
		standard.setName("小件体积货物2");
		standardRepository.save(standard);
	}

	// 所有列表查询测试
	@Test
	public void testFindAll() {
		List<Standard> list = standardRepository.findAll();
		System.out.println(list);
	}
	@Test
	public void testFindOne() {
		Standard one = standardRepository.findOne(1);
		System.out.println(one);

	}
	@Test
	public void testFindByName() {
		List<Standard> findByName = standardRepository.findByName("小件体积货物");
		System.out.println(findByName);

	}
	@Test
	public void testFindIdByName() {
		List<Integer> findByName = standardRepository.findIdByName("小件体积货物");
		System.out.println(findByName);

	}
	@Test
	public void findByIdAndName1() {
		Standard findByIdAndName1 = standardRepository.findByIdAndName1(1, "小件体积货物");
		System.out.println(findByIdAndName1);
	}
}
