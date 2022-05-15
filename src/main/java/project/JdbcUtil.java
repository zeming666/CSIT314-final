package project;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//import org.json.JSONArray;

//import org.json.JSONObject;

import javax.xml.crypto.Data;



//test
public class JdbcUtil {
    public static Connection connectSql() throws ClassNotFoundException, SQLException {

        // MySQL 8.0
        final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
        final String DB_URL      = "jdbc:mysql://192.168.0.13/CSIT314?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

        final String USER = "william";
        final String PASS = "123456";

        Connection conn = null;
        Statement  stmt = null;

        Class.forName(JDBC_DRIVER);


        System.out.println("connecting...");
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
        return conn;
    }


    //sql select
    //TODO check the password!!! dont forget

    public static Map sqlCusSelect(int userID) throws SQLException, ClassNotFoundException {
        Connection        conn = connectSql();
        String            sql  = "select * from Customer where cusNum = ?;";
        PreparedStatement psmt = conn.prepareStatement(sql);
        psmt.setInt(1, userID);
        ResultSet rs     = psmt.executeQuery();
        Map<String,String> res = new HashMap<String, String>();

        while (rs.next()) {
            res.put("cusNum", String.valueOf(rs.getInt("cusNum")));
            res.put("cusName",rs.getString("cusName"));
            res.put("gender",rs.getString("gender"));
            res.put("cusDOB",rs.getString("cusDOB"));
            res.put("phoneNum",rs.getString("phoneNum"));
            res.put("cusPw",rs.getString("cusPw"));
            res.put("email",rs.getString("email"));
            res.put("vipStart",rs.getString("vipStart"));
            res.put("vipEnd",rs.getString("vipEnd"));
            res.put("plateNum",rs.getString("plateNum"));
            res.put("vehicleModel",rs.getString("vehicleModel"));
        }
        return res;
    }

    public static Map sqlProSelect(int userID) throws SQLException, ClassNotFoundException {
        Connection conn = connectSql();

        String            sql  = "select * from Professional where proNum = ?;";
        PreparedStatement psmt = conn.prepareStatement(sql);
        psmt.setInt(1, userID);
        ResultSet    rs     = psmt.executeQuery();
        Map<String,String> res = new HashMap<String, String>();
        while (rs.next()) {
            res.put("proNum", String.valueOf(rs.getInt("proNum")));
            res.put("proName", rs.getString("proName"));
            res.put("gender", rs.getString("gender"));
            res.put("proDOB", rs.getString("cusDOB"));
            res.put("phoneNum", rs.getString("phoneNum"));
            res.put("proPw", rs.getString("cusPw"));
            res.put("email", rs.getString("email"));
            res.put("pLevel", String.valueOf(rs.getFloat("pLevel")));
            res.put("balance", String.valueOf(rs.getDouble("balance")));
            res.put("location", rs.getString("location"));
        }
        return res;
    }
    public static Map sqlCusLoginSelect(String username) throws SQLException, ClassNotFoundException {
        Connection conn = connectSql();
        System.out.println("name = "+username);
        String            sql  = "select cusPw,cusNum,vipEnd from Customer where cusName = ?;";
        PreparedStatement psmt = conn.prepareStatement(sql);
        psmt.setString(1, username);
        ResultSet    rs     = psmt.executeQuery();
        Map<String,String> res = new HashMap<String, String>();
        while (rs.next()) {
            res.put("cusPw",rs.getString("cusPw"));
            res.put("cusNum",rs.getString("cusNum"));
            res.put("vipEnd",rs.getString("vipEnd"));

        }
        return res;
    }

    public static Map sqlProLoginSelect(String username) throws SQLException, ClassNotFoundException {
        Connection conn = connectSql();

        String            sql  = "select proPw,proNum from Professional where proName = ?;";
        PreparedStatement psmt = conn.prepareStatement(sql);
        psmt.setString(1, username);
        ResultSet    rs     = psmt.executeQuery();
        Map<String,String> res = new HashMap<String, String>();
        while (rs.next()) {
            res.put("proPw", rs.getString("proPw"));
            res.put("proNum",rs.getString("proNum"));

        }
        return res;
    }
    public static Map sqlcurOrderSelect(String oid) throws SQLException, ClassNotFoundException {
        Connection conn = connectSql();

        String            sql  = "select * from cur_orders where cur_orderid = ?;";
        PreparedStatement psmt = conn.prepareStatement(sql);
        psmt.setInt(1, Integer.parseInt(oid));
        ResultSet    rs     = psmt.executeQuery();
        Map<String,String> res = new HashMap<String, String>();
        while (rs.next()) {
            res.put("cur_orderid", rs.getString("cur_orderid"));
            res.put("orderStartDate",rs.getString("orderStartDate"));
            res.put("vehiclePlate", rs.getString("vehiclePlate"));
            res.put("price",rs.getString("price"));
            res.put("c_location", rs.getString("c_location"));
            res.put("issue",rs.getString("issue"));
            res.put("O_cusNum",rs.getString("O_cusNum"));
            res.put("sstate",rs.getString("sstate"));

        }
        return res;
    }
//    public static ArrayList sqlVehicleSelect(int cusNum) throws SQLException, ClassNotFoundException {
//        Connection        conn = connectSql();
//        String            sql  = "select * from VEHICLE where cusNum = ?";
//        PreparedStatement psmt = conn.prepareStatement(sql);
//        psmt.setInt(1, cusNum);
//        ResultSet rs          = psmt.executeQuery();
//        ArrayList vehicleList = new ArrayList<Vehicle>();
//        while (rs.next()) {
//            Vehicle vehicle = new Vehicle(rs.getString("plateNum"), rs.getString("model"));
//            System.out.println(vehicle.getPlateNum());
//            vehicleList.add(vehicle);
//        }
//        return vehicleList;
//    }

    public static String sqlOrderSelect(String userNum) throws SQLException, ClassNotFoundException {
        Connection        conn   = connectSql();
        String            sql    = "select * from ORDER where userID = ?";
        PreparedStatement psmt   = conn.prepareStatement(sql);
        psmt.setString(1, userNum);
        ResultSet  rs     = psmt.executeQuery();
        String res = "";
        while (rs.next()) {
            res = rs.getString(1);
        }
        return res;
    }


    //sql insert customer
    public static void sqlCusInsert(String username, String password, String DOB, String email, String phoneNum, String gender,String plateNum,String model) throws SQLException, ClassNotFoundException {
        Connection con = connectSql();
        String     sql = "";
        sql = "insert into CUSTOMER (cusPw,cusName,cusDOB,phoneNum,vipStart,vipEnd,email,gender,plateNum,vehicleModel) values (?,?,?,?,?,?,?,?,?,?);";
        PreparedStatement psmt = con.prepareStatement(sql);
        psmt.setString(1, password);
        psmt.setString(2, username);
        psmt.setString(3, DOB);
        psmt.setString(4, phoneNum);
        psmt.setString(5, "2022-01-01");
        psmt.setString(6, "2022-01-01");
        psmt.setString(7, email);
        psmt.setString(8, gender);
        psmt.setString(9, plateNum);
        psmt.setString(10, model);
        psmt.execute();
        con.close();
    }

    //sql insert professional
    public static void sqlProInsert(String username, String password, String DOB, String email, String phoneNum, String gender, String location) throws SQLException, ClassNotFoundException {
        Connection        con  = connectSql();
        String            sql  = "insert into PROFESSIONAL (proPw,proName,proDOB,phoneNum,pLevel,balance,location,email,gender) values (?,?,?,?,?,?,?,?,?);";
        PreparedStatement psmt = con.prepareStatement(sql);
        psmt.setString(1, password);
        psmt.setString(2, username);
        psmt.setString(3, DOB);
        psmt.setString(4, phoneNum);
        psmt.setFloat(5, 5);
        psmt.setFloat(6, 0);
        psmt.setString(7, location);
        psmt.setString(8, email);
        psmt.setString(9, gender);
        psmt.execute();
        con.close();
    }
    public static void sqlCurrOrderInsert(String orderStartDate, String vehiclePlate, float price, String c_location, String issue, String O_cusNum) throws SQLException, ClassNotFoundException {
        Connection        con  = connectSql();
        String state = "waiting";
        String            sql  = "insert into cur_orders (orderStartDate,vehiclePlate,price,c_location,issue,O_cusNum,sstate) values (?,?,?,?,?,?,?);";
        PreparedStatement psmt = con.prepareStatement(sql);
        psmt.setString(1, orderStartDate);
        psmt.setString(2, vehiclePlate);
        psmt.setFloat(3, price);
        psmt.setString(4, c_location);
        psmt.setString(5, issue);
        psmt.setString(6, O_cusNum);
        psmt.setString(7, state);
        psmt.execute();
        con.close();
    }

    //sql insert vehicle
    public static void sqlVehInsert(int userID,String plateNum,String model ) throws SQLException, ClassNotFoundException {
        Connection con = connectSql();
        String            sql         = "insert into VEHICLE (userID,plateNum,model) values (?,?,?)";
        PreparedStatement psmt        = con.prepareStatement(sql);
        psmt.setInt(1, userID);
        psmt.setString(2, plateNum);
        psmt.setString(3, model);
        con.close();
    }

//    //sql insert order
//    public static void sqlOrdInsert(String x) throws SQLException, ClassNotFoundException {
//        Connection con = connectSql();
//
//        String            sql            = "insert into ORDERS (orderID,orderStartDate,customerID,price,vehiclePlate,location,issue,professional,orderEndDate,review,rating,payCardNum,payType) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
//        PreparedStatement psmt           = con.prepareStatement(sql);
//        int               columnOfSql    = 1;
//        psmt.setString(columnOfSql, orderID);
//        psmt.setString(columnOfSql++, orderStartDate);
//        psmt.setInt(columnOfSql++, customerID.getUserID());
//        psmt.setDouble(columnOfSql++, price);
//        psmt.setString(columnOfSql++, vehiclePlate.getPlateNum());
//        psmt.setString(columnOfSql++, location);
//        psmt.setString(columnOfSql++, issue);
//        psmt.setInt(columnOfSql++, professional.getUserID());
//        psmt.setString(columnOfSql++, orderEndDate);
//        psmt.setString(columnOfSql++, review);
//        psmt.setFloat(columnOfSql++, rating);
//        psmt.setString(columnOfSql++, payCardNum);
//        psmt.setString(columnOfSql++, String.valueOf(payType));
//
//        con.close();
//    }

    //sql update customer
    public static void updateCustomer(Map<String, String> map) throws SQLException, ClassNotFoundException {
        Connection con = connectSql();
        int    userID   = Integer.parseInt(map.get("uid"));
        String userName = map.get("cusName");
        String gender   = map.get("gender");
        String DOB      = map.get("DOB");
        String phoneNum = map.get("phoneNum");
        String password = map.get("password");
        String email    = map.get("email");
        String plateNum = map.get("plateNum");
        String vehicleModel = map.get("vehicleModel");
        String sql = "" +
                     "update CUSTOMER " +
                     "set cusName=?,gender=?,cusDOB=?,phoneNum=?,password=?,email=?,plateNum=?,vehicleModel=?," +
                     "where userID=?";
        //预编译sql语句
        PreparedStatement psmt        = con.prepareStatement(sql);
        //先对应SQL语句，给SQL语句传递参数
        psmt.setInt(9, userID);
        psmt.setString(1, userName);
        psmt.setString(2, gender);
        psmt.setString(3, DOB);
        psmt.setString(4, phoneNum);
        psmt.setString(5, password);
        psmt.setString(6, email);
        psmt.setString(7, plateNum);
        psmt.setString(8, vehicleModel);
        //执行SQL语句
        psmt.execute();
    }

    //sql update Professional
    public static void updateProfessional(Map<String,String> map) throws SQLException, ClassNotFoundException {
        Connection con = connectSql();
        int    userID   = Integer.parseInt(map.get("uid"));
        String userName = map.get("userName");
        String gender   = map.get("gender");
        String DOB      = map.get("DOB");
        String phoneNum = map.get("phoneNum");
        String password = map.get("password");
        String email    = map.get("email");
        String location = map.get("location");
        String sql = "" +
                     "update CUSTOMER " +
                     "set proName=?,gender=?,proDOB=?,phoneNum=?,proPw=?,email=?,location=?" +
                     "where proNum=?";

        PreparedStatement psmt        = con.prepareStatement(sql);

        psmt.setInt(8, userID);
        psmt.setString(1, userName);
        psmt.setString(2, gender);
        psmt.setString(3, DOB);
        psmt.setString(4, phoneNum);
        psmt.setString(5, password);
        psmt.setString(6, email);
        psmt.setString(7, location);

        psmt.execute();
    }

    //sql delete
//    public boolean sqlDeleteVehicle(JSONObject ob) throws SQLException, ClassNotFoundException {
//        Connection        conn     = connectSql();
//        int               cusID    = ob.getInt("cusID");
//        String            plantNum = ob.getString("plantNum");
//        String            sql      = "delete * from VEHICLE where cusID = ? and plantNum = ?";
//        PreparedStatement psmt     = conn.prepareStatement(sql);
//        psmt.setInt(1, cusID);
//        psmt.setString(2, plantNum);
//        return psmt.execute();
//    }


    //TODO
    //sql语句查询最后一个已存在的用户ID
    public int getNewID() throws SQLException, ClassNotFoundException {
        Connection        conn  = connectSql();
        String            sql   = "select MAX(cusID) from CUSTOMER;";
        PreparedStatement psmt  = conn.prepareStatement(sql);
        ResultSet         rs    = psmt.executeQuery();
        int               newID = rs.getInt("cusID");
        newID++;
        return newID;
    }

    public static java.sql.Date toSqlData(String data) {
        SimpleDateFormat sdf   = new SimpleDateFormat("yyyy-MM-dd");
        java.sql.Date    sdate = null; //初始化
        try {
            java.util.Date udate = sdf.parse(data);
            sdate = new java.sql.Date(udate.getTime()); //2013-01-14
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdate;
    }

}
