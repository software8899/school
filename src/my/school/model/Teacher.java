package my.school.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * Teacher model.
 * 
 */

@SuppressWarnings("serial")
public class Teacher extends Model<Teacher> {

	private School school;

	private Role role;

	public static final Teacher dao = new Teacher();

	/**
	 * 根据学校ID所有该学校所有的教师信息
	 * 
	 * @return
	 */
	public List<Teacher> getTeachers(int sid) {
		return Teacher.dao.find("select * from teacher where sid = ?", sid);
	}

	/**
	 * 根据学校ID获取该学校所有的班主任信息
	 * 
	 * @return
	 */
	public List<Teacher> getHeadTeachers(int sid) {
		return Teacher.dao.find("select * from teacher where rid = 3 and status = 0 and sid = ?",
				sid);
	}

	/**
	 * 根据学校ID获取该学校所有的任课老师信息
	 * 
	 * @return
	 */
	public List<Teacher> getNormalTeachers(int sid) {
		return Teacher.dao.find("select * from teacher where rid = 4 and status = 0 and sid = ?",
				sid);
	}

	/**
	 * 根据班级ID获取班主任信息
	 * 
	 * @param cid
	 * @return
	 */
	public Teacher getTeacherByClassId(int cid) {
		Class clazz = Class.dao.findById(cid);
		return clazz.getTeacher();
	}

	/**
	 * 根据uuid,name,identity来获取教师信息
	 * 
	 * @param uuid
	 * @param name
	 * @param identity
	 * @return
	 */
	public Teacher getTeacher(String uuid, String name, String identity) {
		return Teacher.dao.findFirst(
				"select * from teacher where uuid = ? and name = ? and identity = ?", uuid, name,
				identity);
	}

	/**
	 * 获取班级信息
	 * 
	 * @return
	 */
	public Class getClazz() {

		return Class.dao.findFirst("select * from class where tid = ?", get("id"));

	}

	/**
	 * 分页处理
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<Teacher> paginate(int pageNumber, int pageSize) {
		return paginate(pageNumber, pageSize, "select *", "from teacher order by id desc");
	}

	public School getSchool() {

		if (school == null) {
			school = School.dao.findById(get("sid"));
		}
		System.out.println("school.name = " + school.getStr("name"));
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	/**
	 * 获取该教师的角色
	 * 
	 * @return
	 */
	public Role getRole() {

		if (role == null) {
			role = Role.dao.findById(get("rid"));
		}

		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
