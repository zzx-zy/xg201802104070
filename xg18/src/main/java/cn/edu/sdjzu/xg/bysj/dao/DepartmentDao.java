package cn.edu.sdjzu.xg.bysj.dao;

import cn.edu.sdjzu.xg.bysj.domain.Department;
import cn.edu.sdjzu.xg.bysj.domain.School;
import cn.edu.sdjzu.xg.bysj.service.SchoolService;
import util.JdbcHelper;

import java.sql.*;
import java.util.Collection;
import java.util.TreeSet;

public final class DepartmentDao {
	//创建私有的静态的DepartmentDao对象
	private static DepartmentDao departmentDao = new DepartmentDao();
	//创建私有的静态的集合departments
	private static Collection<Department> departments = new TreeSet<Department>();
	//定义私有的构造器
	private DepartmentDao(){}
	//定义getInstance()方法,返回departmentDao指向对象
	public static DepartmentDao getInstance(){
		return departmentDao;
	}
	//定义findAll()方法,返回departments集合
	public static Collection<Department> findAll() throws SQLException{
		departments.clear();
		Connection connection = JdbcHelper.getConn();
		//在该连接上创建语句盒子对象
		Statement stmt = connection.createStatement();
		//执行SQL查询语句并获得结果集对象
		ResultSet resultSet = stmt.executeQuery("select * from Department");
		School school = null;
		while (resultSet.next()){
			school = SchoolService.getInstance().find(resultSet.getInt("school_id"));
			Department department = new Department(resultSet.getInt("id"),resultSet.getString("description"),resultSet.getString("no"),resultSet.getString("remarks"),school);
			departments.add(department);
		}
		return departments;
	}
	//定义find()方法
	public Department find(Integer id) throws SQLException{
		Connection connection = JdbcHelper.getConn();
		String updateDepartment_sql = "SELECT * FROM Department where id = ?";
		PreparedStatement pstmt = connection.prepareStatement(updateDepartment_sql);
		pstmt.setInt(1,id);
		ResultSet resultSet = pstmt.executeQuery();
		Department department = null;
		if (resultSet.next()) {
			department = new Department(resultSet.getInt("id"), resultSet.getString("description"), resultSet.getString("no"), resultSet.getString("remarks"), SchoolService.getInstance().find(resultSet.getInt("school_id")));
		}
		JdbcHelper.close(pstmt,connection);
		return department;
	}
	//通过向集合中添加department，实现添加功能
	public boolean add(Department department) throws SQLException {
		Connection connection = JdbcHelper.getConn();
		//创建sql语句
		String addDepartment_sql="Insert into Department (no,description,remarks,school_id) values (?,?,?,?)";
		//在该连接上创建预编译语句对象
		PreparedStatement pstmt =connection.prepareStatement(addDepartment_sql);
		//为预编译参数赋值
		pstmt.setString(1, department.getNo());
		pstmt.setString(2, department.getDescription());
		pstmt.setString(3, department.getRemarks());
		pstmt.setInt(4,department.getSchool().getId());
		departments.add(department);
		//执行预编译对象的excuteUpdate方法，获取添加的记录行数
		int affectedRowNum=pstmt.executeUpdate();
		//显示添加的记录的行数
		System.out.println("添加了"+affectedRowNum+"条记录");
		JdbcHelper.close(pstmt,connection);
		return affectedRowNum > 0;
	}
	//定义delete()方法
	public boolean delete(Department department) throws SQLException{
		Connection connection = JdbcHelper.getConn();
		//创建sql语句
		String deleteDepartment_sql="Delete from Department where id=? ";
		//在该连接上创建预编译语句对象
		PreparedStatement pstmt =connection.prepareStatement(deleteDepartment_sql);
		//为预编译参数赋值
		pstmt.setInt(1,department.getId());
		//执行预编译对象的excuteUpdate方法
		int affectedRowNum=pstmt.executeUpdate();
		//显示删除的记录的行数
		System.out.println("删除了"+affectedRowNum+"条记录");
		JdbcHelper.close(pstmt,connection);
		return true ;
	}
	public boolean update(Department department) throws ClassNotFoundException,SQLException{
		Connection connection = JdbcHelper.getConn();
		//写sql语句
		String updateDepartment_sql = " update department set description=?,no=?,remarks=? where id=?";
		//在该连接上创建预编译语句对象
		PreparedStatement preparedStatement = connection.prepareStatement(updateDepartment_sql);
		//为预编译参数赋值
		preparedStatement.setString(1,department.getDescription());
		preparedStatement.setString(2,department.getNo());
		preparedStatement.setString(3,department.getRemarks());
		preparedStatement.setInt(4,department.getId());
		//执行预编译语句，获取改变记录行数并赋值给affectedRowNum
		int affectedRows = preparedStatement.executeUpdate();
		System.out.println("更新了" + affectedRows + "条记录");
		//关闭资源
		JdbcHelper.close(preparedStatement,connection);
		return affectedRows>0;
	}
	public Collection<Department> findAllBySchool(Integer schoolId) throws SQLException {
		Collection findAllBySchool = new TreeSet<Department>();
		Connection connection = JdbcHelper.getConn();
		PreparedStatement preparedStatement =
				connection.prepareStatement(
						"SELECT * FROM Department where school_id=?");
		preparedStatement.setInt(1,schoolId);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()){
			findAllBySchool.add(new Department(
					resultSet.getInt("id"),
					resultSet.getString("description"),
					resultSet.getString("no"),
					resultSet.getString("remarks"),
					SchoolService.getInstance().find(resultSet.getInt("school_id"))
			));
		}
		//关闭资源
		JdbcHelper.close(resultSet,preparedStatement,connection);
		return findAllBySchool;
	}
}