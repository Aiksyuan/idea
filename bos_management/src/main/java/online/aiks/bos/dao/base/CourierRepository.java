package online.aiks.bos.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import online.aiks.bos.domain.base.Courier;
import online.aiks.bos.domain.base.Standard;

public interface CourierRepository extends JpaRepository<Courier, Integer>, JpaSpecificationExecutor<Courier> {
	/**
	 * 属性表达式
	 * 
	 * @param name
	 * @return
	 */

	/* public Standard findByName(String name); */
	public List<Courier> findByName(String name);
	@Query(value = "select id from Standard where name =?")
	public List<Integer> findIdByName(String name);
	@Query("from Standard where id =? and name =?")
	public Standard findByIdAndName1(Integer id, String name);

	@Query("update Courier set deltag=?2 where id=?1")
	@Modifying
	public void updateDeltagById(Integer id, Character deltag);
	// 2）命名占位符
	/*
	 * @Query("from Standard where id =:id and name =:name") public Standard
	 * findByIdAndName2(@Param("name") String name, @Param("id") Integer id); //
	 * 3）JPA占位符:?索引（索引从1开始）
	 * 
	 * @Query("from Standard where id =?2 and name =?1") public Standard
	 * findByIdAndName3(String name, Integer id);
	 */
}
