package com.example.virtualwaiter.Net;

import static com.example.virtualwaiter.Net.StaticData.IP;
import static com.example.virtualwaiter.Net.StaticData.DB_NAME;
import static com.example.virtualwaiter.Net.StaticData.LANGUAGE;
import static com.example.virtualwaiter.Net.StaticData.MENU;

import android.util.Log;

import com.example.virtualwaiter.CommonClasses.Dish;
import com.example.virtualwaiter.CommonClasses.Drink;
import com.example.virtualwaiter.CommonClasses.Food;
import com.example.virtualwaiter.CommonClasses.FoodToAdd;
import com.example.virtualwaiter.CommonClasses.Menu;
import com.example.virtualwaiter.CommonClasses.Order;
import com.example.virtualwaiter.CommonClasses.OrderItem;
import com.example.virtualwaiter.CommonClasses.Table;
import com.example.virtualwaiter.CommonClasses.Worker;

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
                o.setPayByCard(rs.getBoolean("payByCard"));
                o.setSplitBillBetween(rs.getInt("splitBillTo"));
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

    public static String getAllOrders() {
        try {
            String query = "SELECT o.*, w.name FROM `order` o INNER JOIN `worker` w ON o.WaiterId = w.id";
            ResultSet rs = state.executeQuery(query);
            ArrayList<Order> tempOrderList = new ArrayList<>();

            while (rs.next()) {
                Order o = new Order();
                o.setId(rs.getInt("id"));
                o.setTable(new Table(rs.getInt("Tableid")));
                o.setStatus(rs.getString("status"));
                o.setDate(rs.getDate("date"));
                o.setPayByCard(rs.getBoolean("payByCard"));
                o.setSplitBillBetween(rs.getInt("splitBillTo"));
                o.setWaiterName(rs.getString("name"));
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

            StaticData.ALL_ORDERS.clear();
            StaticData.ALL_ORDERS = tempOrderList;
            return "success";
        }
        catch (Exception e) {
            Log.d("Error get all orders list", e.toString());
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


    public static String addWorker() {
        try{
            String workerType = StaticData.WORKER.getType();
            String login = StaticData.WORKER.getLogin();
            String pass = StaticData.WORKER.getPassword();
            String name = StaticData.WORKER.getName();


            Log.d("Add new worker", StaticData.WORKER.toString());
            state.executeUpdate("INSERT INTO `worker` (id, type, login, password, name) " +
                                    "VALUES (null,'"+workerType+"','"+login+"','" +pass+"','"+name+"');");
            return  "success";
        }
        catch(Exception e){
            Log.d("Error add new Worker", e.toString());
            return "error";
        }
    }

    public static String updateWorker() {
        try{
            Worker worker = StaticData.WORKER;
            Log.d("Update worker", Integer.toString(worker.getId()) + ", " + worker.getType() + ", " + worker.getLogin() + ", " + worker.getPassword() + ", " + worker.getName());
            state.executeUpdate("UPDATE `worker` SET `type` = '" + worker.getType() + "', `login` = '" + worker.getLogin() + "', `password` = '" + worker.getPassword() + "', `name` = '" + worker.getName() + "' WHERE id = " + worker.getId());
            return  "success";
        }
        catch(Exception e){
            Log.d("Error update Worker", e.toString());
            return "error";
        }
    }

    public static String deleteWorker() {
        try{
            Worker worker = StaticData.WORKER;
            Log.d("Delete worker", worker.toString());
            state.executeUpdate("DELETE FROM `worker` WHERE id = " + worker.getId() + ";");
            return  "success";
        }
        catch(Exception e){
            Log.d("Error delete Worker", e.toString());
            return "error";
        }
    }

    public static String getWorkers() {
        try {
            String query = "SELECT * FROM `worker`";
            ResultSet rs = state.executeQuery(query);
            StaticData.EMPLOYEES.clear();

            while (rs.next()) {
                Worker w = new Worker(Integer.parseInt(rs.getString("id")), rs.getString("name"), rs.getString("login"), rs.getString("password"), rs.getString("type"));
                StaticData.EMPLOYEES.add(w);
            }

            return "success";
        }
        catch (Exception e) {
            Log.d("Error get all workers list", e.toString());
            return "error";
        }
    }

    public static String addTable() {
        try{
            Table table = StaticData.TABLE;

            Log.d("Add new table", StaticData.TABLE.toString());
            state.executeUpdate("INSERT INTO `table` (`id`, `numberOfSeats`, `numberOfSeatsOccupied`, `WaiterId`) " +
                    "VALUES (null,'"+table.getNumberOfSeats()+"', 0, null);");
            return  "success";
        }
        catch(Exception e){
            Log.d("Error add new Table", e.toString());
            return "error";
        }
    }

    public static String updateTable() {
        try{
            Table table = StaticData.TABLE;
            Log.d("Update table", table.toString());
            state.executeUpdate("UPDATE `table` SET `numberOfSeats` = '" + table.getNumberOfSeats() +
                    "' WHERE id = " + table.getTableId() + ";");
            return  "success";
        }
        catch(Exception e){
            Log.d("Error update Table", e.toString());
            return "error";
        }
    }

    public static String getTables() {
        try {
            String query = "SELECT * FROM `table`";
            ResultSet rs = state.executeQuery(query);
            StaticData.TABLES.clear();

            while (rs.next()) {
                Table t = new Table(Integer.parseInt(rs.getString("id")), Integer.parseInt(rs.getString("numberOfSeats")));
                StaticData.TABLES.add(t);
            }

            return "success";
        }
        catch (Exception e) {
            Log.d("Error get all tables list", e.toString());
            return "error";
        }

    }

    public static String deleteTable() {
        try{
            Table table = StaticData.TABLE;
            Log.d("Delete table", table.toString());
            state.executeUpdate("DELETE FROM `table` WHERE id = " + table.getTableId() + ";");
            return  "success";
        }
        catch(Exception e){
            Log.d("Error delete table", e.toString());
            return "error";
        }
    }

    public static String addFood() {
        try{
            FoodToAdd food = StaticData.FOOD;

            Log.d("Add new food", StaticData.FOOD.toString());
            state.executeUpdate("INSERT INTO `food` (id, name_pl, name_en, price, type, description_pl, description_en, isGlutenFree, isVegan, isAlcoholic, photoName) " +
                    "VALUES (null,'"+food.getNamePL()+"','" + food.getName() + "', " + food.getPrice() + ", '"+ food.getType() + "', '" +
                    food.getDescriptionPL() + "', '"+ food.getDescription() +"', "+ (food.getIsGlutenFree() ? 1 : 0) +", "+ (food.getIsVegan() ? 1 : 0) + ", "+(food.getIsAlcoholic() ? 1 : 0) +", '"+food.getPhotoName()+"');");
            return  "success";
        }
        catch(Exception e){
            Log.d("Error add new Food", e.toString());
            return "error";
        }
    }

    public static String updateFood() {
        try{
            FoodToAdd food = StaticData.FOOD;
            Log.d("Update food", food.toString());

            state.executeUpdate("UPDATE `food` SET `name_pl` = '" + food.getNamePL() + "', `name_en` = '" + food.getName() + "', `price` = " + food.getPrice() +
                            ", `type` = '" + food.getType() + "', `description_pl` = '" + food.getDescriptionPL() + "', `description_en` ='" + food.getDescription() +
                            "', `isGlutenFree` = " + (food.getIsGlutenFree() ? 1 : 0) + ", `isVegan` = " + (food.getIsVegan() ? 1 : 0) + ", `isAlcoholic` = " + (food.getIsAlcoholic() ? 1 : 0) + ", `photoName` = '" + food.getPhotoName() + "'" +
                    " WHERE id = " + food.getId() + ";");
            return  "success";
        }
        catch(Exception e){
            Log.d("Error update Food", e.toString());
            return "error";
        }
    }

    public static String getFoodFull() {
        try {
            String query = "SELECT * FROM `food`";
            ResultSet rs = state.executeQuery(query);
            StaticData.ALL_FOOD.clear();

            while (rs.next()) {
                FoodToAdd f = new FoodToAdd(rs.getInt("id"),
                        rs.getString("name_en"),
                        rs.getString("name_pl"),
                        rs.getString("description_en"),
                        rs.getString("description_pl"),
                        rs.getString("type"),
                        rs.getDouble("price"),
                        rs.getBoolean("isAlcoholic"),
                        rs.getBoolean("isGlutenFree"),
                        rs.getBoolean("isVegan"),
                        rs.getString("photoName")
                );
                StaticData.ALL_FOOD.add(f);
            }

            return "success";
        }
        catch (Exception e) {
            Log.d("Error get all food list", e.toString());
            return "error";
        }
    }

    public static String deleteFood() {
        try{
            FoodToAdd food = StaticData.FOOD;
            Log.d("Delete food", food.toString());
            state.executeUpdate("DELETE FROM `food` WHERE id = " + food.getId() + ";");
            return  "success";
        }
        catch(Exception e){
            Log.d("Error delete food", e.toString());
            return "error";
        }
    }

}
