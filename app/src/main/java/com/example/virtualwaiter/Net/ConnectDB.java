package com.example.virtualwaiter.Net;

import static com.example.virtualwaiter.Net.StaticData.IP;
import static com.example.virtualwaiter.Net.StaticData.DB_NAME;
import static com.example.virtualwaiter.Net.StaticData.LANGUAGE;
import static com.example.virtualwaiter.Net.StaticData.MENU;

import android.util.Log;

import com.example.virtualwaiter.CommonClasses.Dish;
import com.example.virtualwaiter.CommonClasses.Drink;
import com.example.virtualwaiter.CommonClasses.Food;
import com.example.virtualwaiter.CommonClasses.Menu;
import com.example.virtualwaiter.CommonClasses.Order;
import com.example.virtualwaiter.CommonClasses.OrderItem;
import com.example.virtualwaiter.CommonClasses.Table;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ConnectDB {

    private static Connection con;
    private static Statement state;


    public static String initConnection(){
        String status;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con= DriverManager.getConnection(
                    "jdbc:mysql://"+IP+":3306/"+DB_NAME,"root","");
            state = con.createStatement();
            status = "ok";
        }
        catch (Exception e){
            Log.d("ConnectDB connection error" , e.toString());
            status = "error";
        }
        return status;
    }


    public static Map<String, String> login(String username, String password, Boolean table){

        Map<String, String> info = new HashMap<>();
        try{
            ResultSet s = state.executeQuery("SELECT EXISTS(SELECT id FROM worker WHERE login='"+username+"' AND password='"+password+"') AS loggedIn;");
            if(s.next()){
                if(s.getInt("loggedIn") == 1){
                    info.put("status", "success");
                    if(s.getInt(1) == 1){
                        ResultSet worker = state.executeQuery("SELECT type,id FROM worker WHERE login='"+username+"'");
                        if(worker.next()){
                            info.put("login" ,  "true");
                            if (table) {
                                info.put("type", "table");

                            }
                            else {
                                info.put("type",worker.getString("type"));
                                info.put("id", worker.getInt("id")+"");
                            }
                        }
                        else {
                            info.put("login", "true");
                            if (table) {
                                info.put("type", "table");
                            }
                            else {
                                info.put("type", "unknown ");
                            }
                        }
                    }
                    else{
                        info.put("login" ,  "false");
                        info.put("type" ,  "unknown ");
                    }
                }
                else{
                    info.put("status", "unknown user");
                    info.put("login" ,  "false");
                    info.put("id" ,  "-1");
                    info.put("type" ,  "unknown ");
                }
            }
            else{
                info.put("status" ,"unknown user");
                info.put("login" ,  "false");
                info.put("id" ,  "-1");
                info.put("type" ,  "unknown ");
            }


        }
        catch (Exception e){
            Log.d("ConnectDB","Connection Failed " + e.toString());
            info.put("status", "Error");
            info.put("error", e.toString());
        }
        return info;
    }


    public static ArrayList<Table> getFreeTables() {
        ArrayList<Table> freeTables = new ArrayList<>();
        try{
            ResultSet rs = state.executeQuery("SELECT id, numberOfSeats FROM `table` WHERE `waiterId` IS NULL");
            while (rs.next()) {
                Table temp = new Table(rs.getInt("id"), rs.getInt("numberOfSeats"));
                freeTables.add(temp);
            }
        }
        catch(Exception e){
            Log.d("Error stoliki", e.toString());
        }

        return freeTables;
    }

//    public static void setWaiterToTable(Integer waiterId) throws SQLException {
//        state.executeQuery("UPDATE Table ON WorkerId = " + waiterId);
//    }

    public static Map<String, ArrayList<Object>> getWaitersList() {

        ArrayList<Object> waitersID = new ArrayList<>();
        ArrayList<Object> waitersName = new ArrayList<>();
        ArrayList<Object> status = new ArrayList<>();
        HashMap<String, ArrayList<Object>> waiters = new HashMap<>();

        try{
            ResultSet rs = state.executeQuery("SELECT id, name FROM worker WHERE type = 'waiter'");
            while (rs.next()) {
                waitersID.add(rs.getInt("id"));
                waitersName.add(rs.getString("name"));
            }
            waiters.put("id", waitersID);
            waiters.put("name", waitersName);
            status.add("success");
            waiters.put("status", status);
            return  waiters;
        }
        catch(Exception e){
            Log.d("Error kelnerzy", e.toString());
            status.add("error");
            waiters.put("status", status);
            return waiters;
        }

    }

    public static String getFood() {

        MENU = new Menu();
        try{
            ResultSet rs = state.executeQuery("SELECT * FROM food");
            while (rs.next()) {

                if(rs.getString("type").equals("dish")){
                    Dish d = new Dish(rs.getString("name_" + LANGUAGE),
                            rs.getString("photoName"),
                            rs.getDouble("price"),
                            rs.getString("description_" + LANGUAGE),
                            rs.getInt("isVegan"),
                            rs.getInt("isGlutenFree"),
                            rs.getInt("id"));
                    MENU.addDish(d);
                }
                else{
                    Drink d = new Drink(rs.getString("name_" + LANGUAGE),
                            rs.getString("photoName"),
                            rs.getDouble("price"),
                            rs.getInt("isAlcoholic"),
                            rs.getInt("id"));
                    MENU.addDrink(d);
                }
            }

            return  "success";
        }
        catch(Exception e){
            Log.d("Error food", e.toString());
            return "error";
        }

    }


    public static String placeOrder() {


        try{
            DecimalFormat df = new DecimalFormat("0.00");
            double total = StaticData.ORDER.getTotal().doubleValue();
            java.sql.Timestamp now = new java.sql.Timestamp(new Date().getTime());


            int tableId = StaticData.ORDER.getTable().getTableId();
            int waiterId = StaticData.ORDER.getTable().getWaiterId();

            String quertUpdate = "UPDATE `table` SET WaiterId = "+waiterId+" WHERE `table`.id = "+tableId+";";
            state.executeUpdate(quertUpdate);
            String queryInser = "" +
                    "INSERT INTO `order` " +
                    "(id, totalPrice, status, splitBillTo, date, Tableid, WaiterId)" +
                    " VALUES (NULL,"+total+", 'placed', NULL,'"+now+"', "+tableId+", "+waiterId+");";


            Log.d("query", queryInser);
            state.executeUpdate(queryInser);
            ResultSet rs = state.executeQuery("SELECT id FROM `order` WHERE tableid ="+tableId+" ORDER BY id DESC LIMIT 1;");
            int orderId = 0;
            while (rs.next()) {
                orderId = rs.getInt("id");

            }
            StaticData.ORDER.setId(orderId);
            Log.d("order id" , orderId + " ");

            for(OrderItem item : StaticData.ORDER.getList()){
                state.executeUpdate("INSERT INTO `order item` (Orderid, Foodid, amount) " +
                        "VALUES ("+orderId+","+item.getFoodId()+","+item.getAmount()+");");
            }


            return  "success";
        }
        catch(Exception e){
            Log.d("Error place order", e.toString());
            return "error";
        }

    }


    public static String getOrderStatus() {

        try{
            Log.d("Test", "Poszło");
            ResultSet rs = state.executeQuery("SELECT status FROM `order` WHERE id ="+StaticData.ORDER.getId()+"");
            String status = null;
            while (rs.next()) {
                status = rs.getString("status");
            }

            return  status;
        }
        catch(Exception e){
            Log.d("Error place order", e.toString());
            return "error";
        }

    }



    public static  String getOrder(String type){



        try{
            ArrayList<Order> tempOrderList = new ArrayList<>();
            String query = "SELECT * FROM `order` WHERE";

            if(type.equals("current")){
                StaticData.CURRENT_ORDERS_COPY = new ArrayList<>();
                StaticData.CURRENT_ORDERS_COPY.addAll(StaticData.CURRENT_ORDERS);
            }


            switch (StaticData.WORKER_TYPE){
                case "waiter":
                    query += " Waiterid = " + StaticData.WORKER_ID;
                    if(type.equals("current")){
                        query +=  " AND status NOT IN ('paid', 'canceled') AND DATE(`date`) = CURDATE()";
                    }
                    else{
                        query +=  " AND status IN ('paid', 'canceled') AND DATE(`date`) = CURDATE()";
                    }
                    break;

                case "chef":

                    if(type.equals("current")){
                        query +=  " status IN ('placed','received' ,'in progress') AND DATE(`date`) = CURDATE()";
                    }
                    else if(type.equals("closed")){
                        query +=  " status NOT IN ('placed','received' ,'in progress') AND DATE(`date`) = CURDATE()";
                    }
                    else{
                        query += " DATE(`date`) < CURDATE()";
                    }
                    break;

                default:
                    if(type.equals("current")){
                        query +=  " status NOT IN ('paid','canceled') AND DATE(`date`) = CURDATE()";
                    }
                    else if(type.equals("closed")){
                        query +=  " status IN ('paid','canceled') AND DATE(`date`) = CURDATE()";
                    }
                    else{
                        query += " DATE(`date`) < CURDATE()";
                    }
                    break;
            }

            ResultSet rs = state.executeQuery(query);
            while (rs.next()) {
                Order o = new Order();
                o.setId(rs.getInt("id"));
                o.setTable(new Table(rs.getInt("Tableid")));
                o.setStatus(rs.getString("status"));
                o.setDate(rs.getDate("date"));
                tempOrderList.add(o);
            }

            for(Order o : tempOrderList){
                ResultSet rs2 = state.executeQuery("SELECT `order item`.*, `food`.* FROM `order item` JOIN food ON(food.id = `order item`.`Foodid`) WHERE Orderid = "+ o.getId());
                while (rs2.next()){
                    OrderItem tempItem = new OrderItem(
                            new Food(rs2.getString("name_"+ LANGUAGE),
                                    rs2.getString("photoName"),
                                    rs2.getDouble("price"),
                                    rs2.getInt("id") ), rs2.getInt("amount"));
                    o.addToOrder(tempItem);
                }
            }

            switch (type){
                case "current":
                    StaticData.CURRENT_ORDERS.clear();
                    StaticData.CURRENT_ORDERS = tempOrderList;
                    break;
                case "closed":
                    StaticData.PAST_ORDERS.clear();
                    StaticData.PAST_ORDERS = tempOrderList;
                    break;
                default:
                    StaticData.HISTORY_ORDERS.clear();
                    StaticData.HISTORY_ORDERS = tempOrderList;
                    break;
            }
            return "success";
        }
        catch (Exception e){
            Log.d("Error get order list", e.toString());
            return "error";
        }

    }


    public static String changeStatus(String status, int id){
        try{
            state.executeUpdate("UPDATE `order` SET `status` = '"+status+"' WHERE `order`.`id` = "+id);
            if(status.equals("canceled") || status.equals("paid")){
                ResultSet rs = state.executeQuery("SELECT Tableid FROM `order` WHERE `order`.`id` = "+ id);
                if(rs.next()){
                    int tableID = rs.getInt("Tableid");
                    state.executeUpdate("UPDATE `table` SET `WaiterId` = NULL WHERE `table`.`id` =" + tableID);
                }
            }
            return "success";
        }
        catch (Exception e){
            Log.d("Error", e.toString());
            return "error";
        }
    }


    public static String askForBill(){
        String message;
        try{

            Boolean byCard = StaticData.ORDER.getPayByCard();
            int splitBetween = StaticData.ORDER.getSplitBillBetween();
            int orderID = StaticData.ORDER.getId();
            state.executeUpdate("UPDATE `order` SET `status`= 'ready to pay', `payByCard` = "+byCard
                    +", `splitBillTo`= "+splitBetween +" WHERE `order`.`id`=" + orderID);

            message = "success";
        }
        catch (Exception e){
            Log.d("Ask for bill error", e.toString());
            message = "Error";
        }
        return message;
    }

}
