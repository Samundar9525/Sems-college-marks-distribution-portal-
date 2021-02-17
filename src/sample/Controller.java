package sample;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.text.Text;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql. * ;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import javafx.scene.layout.AnchorPane;
import javafx.scene.control.ChoiceBox;
import sample.table.attendance_table;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;
public class Controller implements Initializable {
    @FXML
    private BorderPane dashboardborder;

    @FXML
    void facultydashboard(ActionEvent event) throws IOException {
        System.out.println("working facult click");
        fxmlloader obj = new fxmlloader();
        Pane view = obj.getpage("facultylogin");
        dashboardborder.setCenter(view);

    }

    @FXML
    void studentdashboard(ActionEvent event) throws IOException {
        System.out.println("working facult click");
        fxmlloader obj = new fxmlloader();
        Pane view = obj.getpage("studentlogin");
        dashboardborder.setCenter(view);

    }
    ObservableList<String> list = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)   {
        try {
            list.removeAll(list);
          list.add("Semester_I");
          list.add("Semester_II");
          list.add("Semester_III");
          list.add("Semester_IV");
          list.add("Semester_V");
          list.add("Semester_VI");
           semester_list.getItems().addAll(list);
        }
        catch (NullPointerException e){
            System.out.println("ee ka hai");
        }
        deptinit(); //-----------ee marks wala me deptt ko pehle hi initialize kar dega---------------------------------

    }

    ///////////////////////////////////////////////////////////// student login tak redirect hone ka code//////////////////////////////


    //    public class Facultylogin {
    //
    //    }
    //    public class Studentlogin {
    //    }
    //
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //#################################################################################################################
    //*****************************************************************************************************************
    //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
    //----------------------------------------------------Student ka kaam shuru hua yahan se---------------------------
    @FXML
    void redirect_home(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("studentlogin.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }


    @FXML
    private TextField student_regno;
    @FXML
    private TextField student_name;
    @FXML
    private TextField student_stream;
    @FXML
    private TextField student_phone;
    @FXML
    private TextField student_address;
    @FXML
    private DatePicker student_dob;
    @FXML
    private TextField student_password;
    @FXML
    private Label testing;
    @FXML
    private ImageView student_image;
    @FXML
    private AnchorPane student_portal;
    @FXML
    private Label testing_faculty;

    @FXML
    void student_register(ActionEvent event) throws IOException {
        String stud_regno = student_regno.getText();
        String stud_name = student_name.getText();
        String stud_stream = student_stream.getText();
        String stud_phone = student_phone.getText();
        String stud_address = student_address.getText();
        LocalDate stud_dob = student_dob.getValue();
        String stud_password = student_password.getText();

        //----------------------------------database me data dalne ke liye ---------------------------------------------

        try {
            Connection c = null;
            Statement stmt = null;
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/semsproject", "postgres", "nevada");
            System.out.println("Opened database successfully");
            stmt = c.createStatement();
            PreparedStatement st = c.prepareStatement("insert into studentsignup values(?,?,?,?,?,?,?)");
            st.setString(1, stud_regno);
            st.setString(2, stud_name);
            st.setString(3, stud_stream);
            st.setDate(4, java.sql.Date.valueOf(stud_dob));
            st.setString(5, stud_phone);
            st.setString(6, stud_address);
            st.setString(7, stud_password);

            //st.setBinaryStream(8, fis, file.length());
            st.executeUpdate();
            st.close();
            c.close();

            //-------------------------------------------popup ke liye------------------------------------------------------
            Parent root1 = FXMLLoader.load(getClass().getResource("sucessfulpopup.fxml"));
            Stage stage2 = new Stage();
            stage2.setScene(new Scene(root1));
            stage2.show();

        } catch(Exception e) {
            System.out.println("na hoga insert ");
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    ///@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@      IMAGE CHOOSE KARNE KE LIYE @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    File file;
    FileInputStream fis;

    @FXML
    void image_chooser(ActionEvent event) throws FileNotFoundException {
        FileChooser filechoose = new FileChooser();
        filechoose.setTitle("Choose Image");
        Stage stage = (Stage) student_portal.getScene().getWindow();
        file = filechoose.showOpenDialog(stage);
        FileInputStream inputstream = new FileInputStream(file.getAbsolutePath());
        Image image = new Image(inputstream);
        if (file != null) {
            student_image.setImage(image);
        }
        File file1 = new File(file.getAbsolutePath());
        fis = new FileInputStream(file1);

    }

    ///----------------------------------------- student login ke liye--------------------------------------------------
    @FXML
    private TextField login_regno;

    @FXML
    private PasswordField login_password;

    @FXML
    void stud_login(ActionEvent event) {
        String log_regno = login_regno.getText();
        String log_pass = login_password.getText();

        try {
            Connection c = null;
            Statement stmt = null;
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/semsproject", "postgres", "nevada");
            stmt = c.createStatement();
            System.out.println("open database sucessfully");
            // System.out.println(log_regno);                                                                // debugging
            String sqlStatement = "SELECT * FROM studentsignup WHERE reg_no='" + log_regno + "'";
            //System.out.println(sqlStatement);                                                             // debugging
            ResultSet rs = stmt.executeQuery(sqlStatement);
            String fetch_password = null;
            String fetch_name = null;


            while (rs.next()) {
                fetch_password = rs.getString("password");
                fetch_name=rs.getString("name");
            }
            System.out.println(log_regno + " " + fetch_password);
            if (log_pass.equals(fetch_password)) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("studentmarksview.fxml"));
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch(FileNotFoundException e) {
                    System.out.println(" file not found");
                }
            } else {
                System.out.println("galat enter kiya hai ");
                testing.setText("INVALID USER");
            }
            rs.close();
            stmt.close();
            c.close();
        } catch(Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //#################################################################################################################
    //*****************************************************************************************************************
    //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
    //----------------------------------------------------Faculty ka kaam shuru hua yahan se---------------------------
    @FXML
    private AnchorPane faculty_portal;@FXML
    private TextField login_id;@FXML
    private PasswordField login_faculty_password;@FXML
    private TextField faculty_adhar;@FXML
    private TextField faculty_name;@FXML
    private TextField faculty_phone;@FXML
    private TextField faculty_address;@FXML
    private DatePicker faculty_dob;@FXML
    private ImageView faculty_image;@FXML
    private TextField faculty_qualification;@FXML
    private TextField faculty_password;

    @FXML
    void faculty_register(ActionEvent event) throws Exception {
        String facu_adhar = faculty_adhar.getText();
        String facu_name = faculty_name.getText();
        String facu_phone = faculty_phone.getText();
        String facu_address = faculty_address.getText();
        LocalDate facu_dob = faculty_dob.getValue();
        String facu_quali = faculty_qualification.getText();
        String facu_password = faculty_password.getText();

        try {
            Connection c = null;
            Statement stmt = null;
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/semsproject", "postgres", "nevada");
            System.out.println("Opened database successfully");
            stmt = c.createStatement();
            PreparedStatement st = c.prepareStatement("insert into facultysignup values(?,?,?,?,?,?,?)");
            st.setString(1, facu_adhar);
            st.setString(2, facu_name);
            st.setString(3, facu_phone);
            st.setString(4, facu_address);
            st.setDate(5, java.sql.Date.valueOf(facu_dob));
            st.setString(6, facu_quali);
            st.setString(7, facu_password);

            //st.setBinaryStream(8, fis, file.length());
            st.executeUpdate();
            st.close();
            c.close();

            //-------------------------------------------popup ke liye--------------------------------------------------
            Parent root1 = FXMLLoader.load(getClass().getResource("facultysucessfulpopup.fxml"));
            Stage stage2 = new Stage();
            stage2.setScene(new Scene(root1));
            stage2.show();

        } catch(Exception e) {
            System.out.println("na hoga insert ");
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

    }

    @FXML
    void faculry_login(ActionEvent event) {
        String log_faculty_id = login_id.getText();
        String log_faculty_pass = login_faculty_password.getText();

        try {
            Connection c = null;
            Statement stmt = null;
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/semsproject", "postgres", "nevada");
            stmt = c.createStatement();
            System.out.println("open database sucessfully");
            String sqlStatement = "SELECT * FROM facultysignup WHERE adhar='" + log_faculty_id + "'";
            ResultSet rs = stmt.executeQuery(sqlStatement);
            String fetch_password = null;

            while (rs.next()) {
                fetch_password = rs.getString("password");
            }
            if (log_faculty_pass.equals(fetch_password)) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("facultymarks.fxml"));
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch(FileNotFoundException e) {
                    System.out.println(" file not found");
                }
            } else {
                System.out.println("galat enter kiya hai ");
                testing_faculty.setText("INVALID USER");
            }
            rs.close();
            stmt.close();
            c.close();
        } catch(Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    @FXML
    void faculty_redirect_home(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("facultylogin.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

    }
    File file1;
    FileInputStream fis1;
    @FXML
    void faculty_image_chooser(ActionEvent event) throws FileNotFoundException {
        FileChooser filechoose = new FileChooser();
        filechoose.setTitle("Choose Image");
        Stage stage = (Stage) faculty_portal.getScene().getWindow();
        file1 = filechoose.showOpenDialog(stage);
        FileInputStream inputstream = new FileInputStream(file1.getAbsolutePath());
        Image image = new Image(inputstream);
        if (file1 != null) {
            faculty_image.setImage(image);
        }
        file1 = new File(file1.getAbsolutePath());
        fis1 = new FileInputStream(file1);
    }
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //#################################################################################################################
    //*****************************************************************************************************************
    //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //#################################################################################################################
    //*****************************************************************************************************************
    //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //#################################################################################################################
    //*****************************************************************************************************************
    //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                    //     -----------------------Faculty Attendance Module -----------------------------------


    @FXML
    private ComboBox<String> semester_list;
    @FXML
    private ComboBox<String> course_list;
    @FXML
    private TableView<sample.table.attendance_table> attendance_table;
    @FXML
    private DatePicker attendance_date;
    @FXML
    private TableColumn<sample.table.attendance_table , String> f_table_regno;
    @FXML
    private TableColumn<sample.table.attendance_table , String> f_table_name;
    @FXML
    private TableColumn<sample.table.attendance_table , String> f_table_present;
    @FXML
    private TableColumn<sample.table.attendance_table , String> f_table_absent;


    @FXML
    public void select_course(javafx.scene.input.MouseEvent mouseEvent) {

        if (semester_list.getValue().equals("Semester_III")) {
            System.out.println(semester_list.getValue());
            ObservableList<String> li = FXCollections.observableArrayList();
            course_list.getItems().setAll((String) null);
            li.add("JAVA_programming");
            li.add("Advance_DataStructure");
            li.add("Machine_Learning");
            li.add("Networking");
            li.add("Social_Relevant Project");
            li.add("Ethical_Hacking");
            course_list.getItems().addAll(li);
        }
        else{
            course_list.getItems().setAll((String) null);
        }



    }

    @FXML
    void create_attendance_sheet(ActionEvent event) {
        String s=attendance_date.getValue().format(DateTimeFormatter.ofPattern("ddMMyyyy"));
        String t=semester_list.getValue();
        String u=course_list.getValue();
        String k=t+"_"+u+"_"+s;
        System.out.println(k);
        //     jab ateendance sheet banaya jaye toh Ek table create ho jaye ---------------Uska code--------------------
        try {
            Connection c = null;
            Statement stmt = null;
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/sems_attendance", "postgres", "nevada");
            System.out.println("Works");
            stmt = c.createStatement();
            String sql = "CREATE TABLE "+k+ "(reg_no varchar (20) primary key,Name varchar(30),Present varchar(2),Absent varchar(2))";
            System.out.println(sql);
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
            System.out.println("table created ");
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            warning2.setText("Already Exist");
        }
        //-------------------------fetch wala aur autamatic table banane wala bhi chhaiye uske liye ---------------------
        try{
            inittable();
            System.out.println("hi there");
            loaddata();
        }
        catch (Exception e){
            System.out.println(e);
        }

        System.out.println("WWWWWWWWWWWWWWrterterrtWWWWWWWWWWWWWWWWWWWWWWWWWWW");
    }

    @FXML
    private Label warning2;


    private void inittable(){
        initcols();
    }

    private void initcols(){
        // f_atd_regno,f_atd_name,f_atd_present,f_atd_absent
        f_table_regno.setCellValueFactory(new PropertyValueFactory<>("f_atd_regno"));
        f_table_name.setCellValueFactory(new PropertyValueFactory<>("f_atd_name"));
        f_table_present.setCellValueFactory(new PropertyValueFactory<>("f_atd_present"));
        f_table_absent.setCellValueFactory(new PropertyValueFactory<>("f_atd_absent"));
            editablecols();
    }
    private void editablecols(){
        f_table_regno.setCellFactory(TextFieldTableCell.forTableColumn());
        f_table_regno.setOnEditCommit(e ->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setF_atd_regno(e.getNewValue());
        });

        f_table_name.setCellFactory(TextFieldTableCell.forTableColumn());
        f_table_name.setOnEditCommit(e ->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setF_atd_name(e.getNewValue());
        });

        f_table_present.setCellFactory(TextFieldTableCell.forTableColumn());
        f_table_present.setOnEditCommit(e ->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setF_atd_present(e.getNewValue());
        });

        f_table_absent.setCellFactory(TextFieldTableCell.forTableColumn());
        f_table_absent.setOnEditCommit(e ->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setF_atd_absent(e.getNewValue());
        });

        attendance_table.setEditable(true);
    }

    // ------------------------------     CHoota Sa Data Structure      (Array List) -------------------
    ObservableList<String> reg = FXCollections.observableArrayList();
    ObservableList<String> nam = FXCollections.observableArrayList();
    ObservableList<sample.table.attendance_table> data_table = FXCollections.observableArrayList();
    private void loaddata() {
        try {
            Connection c = null;
            Statement stmt = null;
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/semsproject", "postgres", "nevada");
            System.out.println("Works");
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("select * from studentsignup;");
            System.out.println("-------------------------------------------");
            System.out.println("reg_no\t  NAME \t\t\n");
            while (rs.next()) {
                String reg_no = rs.getString("reg_no");
                String name = rs.getString("name");
                reg.add(reg_no);
                nam.add(name);
                System.out.printf("%s\t%s\t", reg_no, name);
                System.out.println();
            }
            System.out.println("---------------------------------------------");
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        //--------------------------------------yahan pe data load karne ka code ---------------------------------------

        System.out.println("ab to chalna hi chhaiye");
        for (int i = 0; i < reg.size(); i++) {

            data_table.add(new sample.table.attendance_table(reg.get(i), nam.get(i), "", ""));
        }
        attendance_table.setItems(data_table);
        System.out.println(data_table.get(1).getF_atd_name());
    }


    @FXML
    void update_attendance(ActionEvent event) {
        for (int i = 0; i < data_table.size(); i++) {

            String s = attendance_date.getValue().format(DateTimeFormatter.ofPattern("ddMMyyyy"));
            String t = semester_list.getValue();
            String u = course_list.getValue();
            String k = t + "_" + u + "_" + s;
            try {
                Connection c = null;
                Statement stmt = null;
                Class.forName("org.postgresql.Driver");
                c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/sems_attendance", "postgres", "nevada");
                System.out.println("Works");
                stmt = c.createStatement();
                String te= "'"+data_table.get(i).getF_atd_regno()+"'"+"," +
                        "'"+data_table.get(i).getF_atd_name()+"'"+","+
                               "'"+ data_table.get(i).getF_atd_present()+"'" +","+
                        "'"+data_table.get(i).getF_atd_absent()+"'" ;

                String sql = "insert into " + k + " Values (" +te+")";
                System.out.println(sql);
                stmt.executeUpdate(sql);
                stmt.close();
                c.close();
                System.out.println("table created ");
                warning2.setText("Updated");
            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());

            }


        }
    }

    //-----------------------------------------Yahan pe Pdf me badalne wala command hai---------------------------------

    @FXML
    void fetch_attendance_sheet(ActionEvent event) throws FileNotFoundException {
        String s=attendance_date.getValue().format(DateTimeFormatter.ofPattern("ddMMyyyy"));
        String t=semester_list.getValue();
        String u=course_list.getValue();
        String k=t+"_"+u+"_"+s;
        String file = "C:/Users/samun/Desktop/sems/Rough doc/addingTableToPDF.pdf";
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(file));
        Document doc = new Document(pdfDoc);

        Table table = new Table(4);
        table.addCell(new Cell().add("Reg_no"));
        table.addCell(new Cell().add("Name"));
        table.addCell(new Cell().add("Present"));
        table.addCell(new Cell().add("Absent"));
        try {
            Connection c = null;
            Statement stmt = null;
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/sems_attendance", "postgres", "nevada");
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "select * from "+k+";" );
            System.out.println("-------------------------------------------");
            while ( rs.next() ) {
                String pdf_reg=rs.getString("reg_no");
                String  pdf_name = rs.getString("name");
                String pdf_present = rs.getString("present");
                String pdf_absent = rs.getString("absent");
                table.addCell(new Cell().add(pdf_reg));
                table.addCell(new Cell().add(pdf_name));
                table.addCell(new Cell().add(pdf_present));
                table.addCell(new Cell().add(pdf_absent));

            }
            doc.add(table);
            doc.close();
            rs.close();
            stmt.close();
            c.close();
            File attend_data=new File("C:/Users/samun/Desktop/sems/Rough doc/addingTableToPDF.pdf");
            try {
                Desktop.getDesktop().open( attend_data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.out.println("kyu nahi chal raha hai");
        }
    }

    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //#################################################################################################################
    //*****************************************************************************************************************
    //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //#################################################################################################################
    //*****************************************************************************************************************
    //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //#################################################################################################################
    //*****************************************************************************************************************
    //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

    //     -----------------------Faculty Marks Module -----------------------------------

    @FXML
    private ChoiceBox<String> f_deptt_marks;
    @FXML
    private ChoiceBox<String> f_branch_marks;
    @FXML
    private ChoiceBox<String> f_semester_marks;
    @FXML
    private ChoiceBox<String> f_subject_marks;
    @FXML
    private TableView<sample.table.marks_table> f_marks_table;
    @FXML
    private TableColumn<sample.table.marks_table,String> marks_regno;
    @FXML
    private TableColumn<sample.table.marks_table,String> marks_name;
    @FXML
    private TableColumn<sample.table.marks_table,String> marks_asses_1;
    @FXML
    private TableColumn<sample.table.marks_table,String> marks_asses_2;
    @FXML
    private TableColumn<sample.table.marks_table,String> marks_endsem;
    @FXML
    private TableColumn<sample.table.marks_table,String> marks_total;
    @FXML
    private TableColumn<sample.table.marks_table,String> marks_grade;



    private void deptinit(){
        ObservableList<String> li1 = FXCollections.observableArrayList();
        try {
            li1.removeAll(li1);
            li1.add("Information_Technology");
            li1.add("Maths_deptt");
            li1.add("Physics_deptt");
            f_deptt_marks.getItems().addAll(li1);
        }
        catch (NullPointerException e){
            System.out.println("choice box na hoga load");
        }
        branchinit();
    }

    private void branchinit(){
        ObservableList<String> li2 = FXCollections.observableArrayList();
        try {
            li2.removeAll(li2);
            li2.add("MCA");
            li2.add("Maths");
            li2.add("Physics");
            li2.add("MBA");
            li2.add("Biotechnology");
            li2.add("Electronics");
            li2.add("Civil Engg..");

            f_branch_marks.getItems().addAll(li2);
        }
        catch (NullPointerException e){
            System.out.println("choice box na hoga load");
        }
        semesterinit();
    }
    private void semesterinit(){
        ObservableList<String> li3 = FXCollections.observableArrayList();
        try {
            li3.removeAll(li3);
            li3.add("Semester_I");
            li3.add("Semester_II");
            li3.add("Semester_III");
            li3.add("Semester_IV");
            li3.add("Semester_V");
            li3.add("Semester_VI");

            f_semester_marks.getItems().addAll(li3);
            Subjectinit();    //isko mouse click wala me daalna hai
        }
        catch (NullPointerException e){
            System.out.println("choice box na hoga load");
        }
    }


    private void Subjectinit(){
        ObservableList<String> li4 = FXCollections.observableArrayList();
        li4.add("JAVA_programming");
        li4.add("Advance_DataStructure");
        li4.add("Machine_Learning");
        li4.add("Networking");
        li4.add("Social_Relevant_Project");
        li4.add("Ethical_Hacking");
        f_subject_marks.getItems().addAll(li4);
    }

    @FXML
    public void loadsubject(javafx.scene.input.MouseEvent mouseEvent) {
        try {
            if (f_semester_marks.getValue().equals("Semester_III")) {
                f_subject_marks.getItems().setAll((String) null);
                Subjectinit();
            } else {
                f_subject_marks.getItems().setAll((String) null);
            }
        }
        catch (Exception e){
            System.out.println("hence it is not loaded");
        }
    }
    @FXML
    private Label warning3;

    @FXML
    void create_marks_sheet(ActionEvent event) {
        String branch=f_branch_marks.getValue();
        String semester=f_semester_marks.getValue();
        String subject=f_subject_marks.getValue();
        String mytable=branch+"_"+semester+"_"+subject;

        try {
            Connection c = null;
            Statement stmt = null;
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/sems_marks", "postgres", "nevada");
            System.out.println("Works");
            stmt = c.createStatement();
            String sql = "CREATE TABLE "+mytable+ "(Reg_no varchar(20) primary key,Name varchar(30)," +
                    "Asses_I varchar(3)," +
                    "Asses_II varchar(3),end_sem varchar (3)," +
                    "total varchar(4),grade varchar(3))";
            System.out.println(sql);
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
            System.out.println("table created ");
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            warning3.setText("Locked");
        }
        try{
            inittable2();
            System.out.println("hi there");
            loaddata2();
        }
        catch (Exception e){
            System.out.println(e);
        }

    }

    private void inittable2(){
        initcols2();
    }
    private void initcols2(){
        // m_regno,m_name,m_asses_I,m_asses_II,m_endsem,m_total,m_grade;
        marks_regno.setCellValueFactory(new PropertyValueFactory<>("m_regno"));
        marks_name.setCellValueFactory(new PropertyValueFactory<>("m_name"));
        marks_asses_1.setCellValueFactory(new PropertyValueFactory<>("m_asses_I"));
        marks_asses_2.setCellValueFactory(new PropertyValueFactory<>("m_asses_II"));
        marks_endsem.setCellValueFactory(new PropertyValueFactory<>("m_endsem"));
        marks_total.setCellValueFactory(new PropertyValueFactory<>("m_total"));
        marks_grade.setCellValueFactory(new PropertyValueFactory<>("m_grade"));

        editablecols2();
    }
    private void editablecols2(){
        marks_regno.setCellFactory(TextFieldTableCell.forTableColumn());
        marks_regno.setOnEditCommit(e ->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setM_regno(e.getNewValue());
        });
        marks_name.setCellFactory(TextFieldTableCell.forTableColumn());
        marks_name.setOnEditCommit(e ->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setM_name(e.getNewValue());
        });
        marks_asses_1.setCellFactory(TextFieldTableCell.forTableColumn());
        marks_asses_1.setOnEditCommit(e ->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setM_asses_I(e.getNewValue());
        });
        marks_asses_2.setCellFactory(TextFieldTableCell.forTableColumn());
        marks_asses_2.setOnEditCommit(e ->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setM_asses_II(e.getNewValue());
        });
        marks_endsem.setCellFactory(TextFieldTableCell.forTableColumn());
        marks_endsem.setOnEditCommit(e ->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setM_endsem(e.getNewValue());
        });
        marks_total.setCellFactory(TextFieldTableCell.forTableColumn());
        marks_total.setOnEditCommit(e ->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setM_total(e.getNewValue());
        });
        marks_grade.setCellFactory(TextFieldTableCell.forTableColumn());
        marks_grade.setOnEditCommit(e ->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setM_grade(e.getNewValue());
        });
        f_marks_table.setEditable(true);
    }
    ObservableList<String> reg2 = FXCollections.observableArrayList();
    ObservableList<String> nam2 = FXCollections.observableArrayList();
    ObservableList<sample.table.marks_table> data_table2 = FXCollections.observableArrayList();


    private void loaddata2() {
        try {
            Connection c = null;
            Statement stmt = null;
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/semsproject", "postgres", "nevada");
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("select * from studentsignup;");
            System.out.println("-------------------------------------------");
            System.out.println("reg_no\t  NAME \t\t\n");
            while (rs.next()) {
                String reg_no = rs.getString("reg_no");
                String name = rs.getString("name");
                reg2.add(reg_no);
                nam2.add(name);
                System.out.printf("%s\t%s\t", reg_no, name);
                System.out.println();
            }
            System.out.println("---------------------------------------------");
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        //--------------------------------------yahan pe data load karne ka code ---------------------------------------

        System.out.println("ab to chalna hi chhaiye");
        for (int i = 0; i < reg2.size(); i++) {

            data_table2.add(new sample.table.marks_table(reg2.get(i), nam2.get(i), "00", "00","00","00","XY"));
        }
       f_marks_table.setItems(data_table2);

    }

                            //   yahan par marks  update hoga
    @FXML
    void update_marks(ActionEvent event) {
        for (int i = 0; i < data_table2.size(); i++) {
            String t = f_branch_marks.getValue();
            String u = f_semester_marks.getValue();
            String v = f_subject_marks.getValue();
            String k=t+"_"+u+"_"+v;

            try {
                Connection c = null;
                Statement stmt = null;
                Class.forName("org.postgresql.Driver");
                c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/sems_marks", "postgres", "nevada");
                System.out.println("Works");
                stmt = c.createStatement();
                String te= "'"+data_table2.get(i).getM_regno()+"'"+"," +
                        "'"+data_table2.get(i).getM_name()+"'"+","+
                        "'"+ data_table2.get(i).getM_asses_I()+"'" +","+
                        "'"+data_table2.get(i).getM_asses_II()+"'" +","+
                        "'"+data_table2.get(i).getM_endsem()+"'" +","+
                        "'"+data_table2.get(i).getM_total()+"'" +","+
                        "'"+data_table2.get(i).getM_grade()+"'";

                String sql = "insert into " + k + " Values (" +te+")";
                System.out.println(sql);
                stmt.executeUpdate(sql);
                stmt.close();
                c.close();
                System.out.println("table created ");
                warning3.setText("Updated");
            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());

            }

        }

    }

    @FXML
    void print_result(ActionEvent event) throws FileNotFoundException {
        String t = f_branch_marks.getValue();
        String u = f_semester_marks.getValue();
        String v = f_subject_marks.getValue();
        String k=t+"_"+u+"_"+v;
        String marksfile = "C:/Users/samun/Desktop/sems/Rough doc/marks.pdf";
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(marksfile));
        Document doc = new Document(pdfDoc,PageSize.A4.rotate());
        doc.add(new Paragraph("---------------------------------------------"));
        doc.add(new Paragraph("Branch   : "+t));
        doc.add(new Paragraph("Semester  : "+u));
        doc.add(new Paragraph("Subject  : "+v));
        doc.add(new Paragraph("---------------------------------------------"));

        Table table = new Table(7);
        table.setFontSize(10);
        table.setTextAlignment(TextAlignment.CENTER);
        table.addCell(new Cell().add("Reg_no").setBold());
        table.addCell(new Cell().add("Name").setBold());
        table.addCell(new Cell().add("Asses I").setBold());
        table.addCell(new Cell().add("Asses II").setBold());
        table.addCell(new Cell().add("End Sem").setBold());
        table.addCell(new Cell().add("Total").setBold());
        table.addCell(new Cell().add("Grades").setBold());

        try {
            Connection c = null;
            Statement stmt = null;
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/sems_marks", "postgres", "nevada");
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "select * from "+k+";" );
            System.out.println("-------------------------------------------");
            while ( rs.next() ) {
                String pdf_reg=rs.getString("reg_no");
                String  pdf_name = rs.getString("name");
                String pdf_asses1 = rs.getString("asses_i");
                String pdf_asses2 = rs.getString("asses_ii");
                String pdf_endsem = rs.getString("end_sem");
                String pdf_total = rs.getString("total");
                String pdf_grade = rs.getString("grade");
                table.addCell(new Cell().add(pdf_reg));
                table.addCell(new Cell().add(pdf_name));
                table.addCell(new Cell().add(pdf_asses1));
                table.addCell(new Cell().add(pdf_asses2));
                table.addCell(new Cell().add(pdf_endsem));
                table.addCell(new Cell().add(pdf_total));
                table.addCell(new Cell().add(pdf_grade));

            }
            doc.add(table);
            doc.close();
            rs.close();
            stmt.close();
            c.close();
            File marks_data=new File("C:/Users/samun/Desktop/sems/Rough doc/marks.pdf");
            try {
                Desktop.getDesktop().open( marks_data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.out.println("kyu nahi chal raha hai");
        }

    }
    ///@%$$$$$$$$@@@@!@$$!##!$##$!@$!@$@$!$@%&!#&**&#       Redirect KARNE KELIYE       $@!#$@!!#$$#@%$@%^$@$&^%#%
    @FXML
    void redirect_attendance(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("facultyattendance.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
    @FXML
    void redirect_marks(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("facultymarks.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }


    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //#################################################################################################################
    //*****************************************************************************************************************
    //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //#################################################################################################################
    //*****************************************************************************************************************
    //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //#################################################################################################################
    //*****************************************************************************************************************
    //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

    //--------------------------        Student Marks Module        ----------------------------------
    @FXML
    private Label s_name_falg;
    @FXML
    private ChoiceBox<String> s_marks_semester;
    @FXML
    private Text cgpa_cal;
    @FXML
    private TextField s_marks_reg;
    @FXML
    private TextField s_marks_stream;
    @FXML
    private TableView<sample.table.result> s_marks_table2;
    @FXML
    private TableColumn<sample.table.result,String> m_serial;
    @FXML
    private TableColumn<sample.table.result,String> smarks_name;
    @FXML
    private TableColumn<sample.table.result,String> smarks_asses_1;
    @FXML
    private TableColumn<sample.table.result,String> smarks_asses_2;
    @FXML
    private TableColumn<sample.table.result,String> smarks_endsem;
    @FXML
    private TableColumn<sample.table.result,String> smarks_total;
    @FXML
    private TableColumn<sample.table.result,String> smarks_grade;


    @FXML
    public void s_marks_fetchreg(javafx.scene.input.MouseEvent mouseEvent) {
        String reg = s_marks_reg.getText();
        try {
            Connection c = null;
            Statement stmt = null;
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/semsproject", "postgres", "nevada");
            stmt = c.createStatement();
            System.out.println("open database sucessfully");
            String sqlStatement = "SELECT * FROM studentsignup WHERE reg_no='" +reg + "'";
            ResultSet rs = stmt.executeQuery(sqlStatement);

            String fetch_name = null;
            String fetch_stream = null;

            while (rs.next()) {
                fetch_name = rs.getString("name");
                fetch_stream = rs.getString("stream");
            }
            s_name_falg.setText(fetch_name);
            s_marks_stream.setText(fetch_stream);
            initilizable3();

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(" file not found");
        }
    }

    private void initilizable3(){
        ObservableList<String> li5 = FXCollections.observableArrayList();
        s_marks_semester.getItems().setAll((String) null);
        li5.add("Semester_I");
        li5.add("Semester_II");
        li5.add("Semester_III");
        li5.add("Semester_IV");
        li5.add("Semester_V");
        li5.add("Semester_VI");
        s_marks_semester.getItems().addAll(li5);
    }

    ObservableList<String> temp1 = FXCollections.observableArrayList();
    ObservableList<String> temp2 = FXCollections.observableArrayList();
    ObservableList<String> temp3 = FXCollections.observableArrayList();
    ObservableList<String> temp4 = FXCollections.observableArrayList();
    ObservableList<String> temp5 = FXCollections.observableArrayList();
    ObservableList<String> temp6 = FXCollections.observableArrayList();
    @FXML
    void Show_result(ActionEvent event) {
        String stmt1="select * from mca_semester_iii_advance_datastructure where reg_no="+"'"+s_marks_reg.getText()+"'";
        String stmt2="select * from mca_semester_iii_ethical_hacking where reg_no="+"'"+s_marks_reg.getText()+"'";
        String stmt3="select * from mca_semester_iii_java_programming where reg_no="+"'"+s_marks_reg.getText()+"'";
        String stmt4="select * from mca_semester_iii_machine_learning where reg_no="+"'"+s_marks_reg.getText()+"'";
        String stmt5="select * from mca_semester_iii_networking where reg_no="+"'"+s_marks_reg.getText()+"'";
        String stmt6="select * from mca_semester_iii_social_relevant_project where reg_no="+"'"+s_marks_reg.getText()+"'";

        try {
            Connection c = null;
            Statement stmt = null;
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/sems_marks", "postgres", "nevada");
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(stmt1);
            while ( rs.next() ){
                String f_regno=rs.getString("reg_no");
                String f_asse1=rs.getString("asses_i");
                String f_asse2=rs.getString("asses_ii");
                String f_endsem=rs.getString("end_sem");
                String f_total=rs.getString("total");
                String f_grade=rs.getString("grade");
                temp1.addAll("01","Advance_datastructure",f_asse1, f_asse2,f_endsem,f_total,f_grade);
                System.out.println();
            }
        }
        catch (Exception e){

        }
        try {
            Connection c = null;
            Statement stmt = null;
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/sems_marks", "postgres", "nevada");
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(stmt2);
            while ( rs.next() ){
                String f_regno=rs.getString("reg_no");
                String f_asse1=rs.getString("asses_i");
                String f_asse2=rs.getString("asses_ii");
                String f_endsem=rs.getString("end_sem");
                String f_total=rs.getString("total");
                String f_grade=rs.getString("grade");
                temp2.addAll("02","Ehical_Hacking",f_asse1, f_asse2,f_endsem,f_total,f_grade);
            }
        }
        catch (Exception e){

        }
        try {
            Connection c = null;
            Statement stmt = null;
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/sems_marks", "postgres", "nevada");
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(stmt3);
            while ( rs.next() ){
                String f_regno=rs.getString("reg_no");
                String f_asse1=rs.getString("asses_i");
                String f_asse2=rs.getString("asses_ii");
                String f_endsem=rs.getString("end_sem");
                String f_total=rs.getString("total");
                String f_grade=rs.getString("grade");
                temp3.addAll("03","JAVA_Programming",f_asse1, f_asse2,f_endsem,f_total,f_grade);
            }
        }
        catch (Exception e){

        }
        try {
            Connection c = null;
            Statement stmt = null;
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/sems_marks", "postgres", "nevada");
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(stmt4);
            while ( rs.next() ){
                String f_regno=rs.getString("reg_no");
                String f_asse1=rs.getString("asses_i");
                String f_asse2=rs.getString("asses_ii");
                String f_endsem=rs.getString("end_sem");
                String f_total=rs.getString("total");
                String f_grade=rs.getString("grade");
                temp4.addAll("04","Machine_Learning",f_asse1, f_asse2,f_endsem,f_total,f_grade);
            }
        }
        catch (Exception e){

        }
        try {
            Connection c = null;
            Statement stmt = null;
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/sems_marks", "postgres", "nevada");
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(stmt5);
            while ( rs.next() ){
                String f_regno=rs.getString("reg_no");
                String f_asse1=rs.getString("asses_i");
                String f_asse2=rs.getString("asses_ii");
                String f_endsem=rs.getString("end_sem");
                String f_total=rs.getString("total");
                String f_grade=rs.getString("grade");
                temp5.addAll("05","Networking",f_asse1, f_asse2,f_endsem,f_total,f_grade);
            }
        }
        catch (Exception e){

        }

        try {
            Connection c = null;
            Statement stmt = null;
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/sems_marks", "postgres", "nevada");
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(stmt6);
            while ( rs.next() ){
                String f_regno=rs.getString("reg_no");
                String f_asse1=rs.getString("asses_i");
                String f_asse2=rs.getString("asses_ii");
                String f_endsem=rs.getString("end_sem");
                String f_total=rs.getString("total");
                String f_grade=rs.getString("grade");
                temp6.addAll("06","Social_relevant_project",f_asse1, f_asse2,f_endsem,f_total,f_grade);
            }

            initcols3();
            loaddata3();
            loaddata4();
            loaddata5();
            loaddata6();
            loaddata7();
            loaddata8();
            cgpa();

        }
        catch (Exception e){
            System.out.println(e);
        }

    }
    private void initcols3(){
        m_serial.setCellValueFactory(new PropertyValueFactory<>("sno"));
        smarks_name.setCellValueFactory(new PropertyValueFactory<>("sub_name"));
        smarks_asses_1.setCellValueFactory(new PropertyValueFactory<>("asses1"));
        smarks_asses_2.setCellValueFactory(new PropertyValueFactory<>("asses2"));
        smarks_endsem.setCellValueFactory(new PropertyValueFactory<>("endsem"));
        smarks_total.setCellValueFactory(new PropertyValueFactory<>("total"));
        smarks_grade.setCellValueFactory(new PropertyValueFactory<>("grade"));

    }
    ObservableList<sample.table.result> data_table3 = FXCollections.observableArrayList();
    private void loaddata3(){
            data_table3.add(new sample.table.result(temp1.get(0),temp1.get(1),temp1.get(2),temp1.get(3),temp1.get(4),temp1.get(5),temp1.get(6)));
        s_marks_table2.setItems(data_table3);
    }

    private void loaddata4(){
        System.out.println("9");
        data_table3.removeAll();
        data_table3.add(new sample.table.result(temp2.get(0),temp2.get(1),temp2.get(2),temp2.get(3),temp2.get(4),temp2.get(5),temp2.get(6)));
        s_marks_table2.setItems(data_table3);
    }

    private void loaddata5(){
        data_table3.removeAll();
        data_table3.add(new sample.table.result(temp3.get(0),temp3.get(1),temp3.get(2),temp3.get(3),temp3.get(4),temp3.get(5),temp3.get(6)));
        s_marks_table2.setItems(data_table3);
    }
    private void loaddata6(){
        data_table3.removeAll();
        data_table3.add(new sample.table.result(temp4.get(0),temp4.get(1),temp4.get(2),temp4.get(3),temp4.get(4),temp4.get(5),temp4.get(6)));
        s_marks_table2.setItems(data_table3);
    }
    private void loaddata7(){
        data_table3.removeAll();
        data_table3.add(new sample.table.result(temp5.get(0),temp5.get(1),temp5.get(2),temp5.get(3),temp5.get(4),temp5.get(5),temp5.get(6)));
        s_marks_table2.setItems(data_table3);
    }
    private void loaddata8(){
        data_table3.removeAll();
        data_table3.add(new sample.table.result(temp6.get(0),temp6.get(1),temp6.get(2),temp6.get(3),temp6.get(4),temp6.get(5),temp6.get(6)));
        s_marks_table2.setItems(data_table3);

    }


    private int fetchgrade(String j){
        if (j.equals("O")){
            return 10;
        }
        else if (j.equals("A+")){
            return 9;
        }
        else if (j.equals("A")){
            return 8;
        }
        else if(j.equals("B+")){
            return 7;
        }
        else if (j.equals("B")){
            return 6;
        }
        else{
            return 0;
        }
    }

    private void cgpa(){
       int a1=fetchgrade(temp1.get(6));
       int a2=fetchgrade(temp2.get(6));
       int a3=fetchgrade(temp3.get(6));
       int a4=fetchgrade(temp4.get(6));
       int a5=fetchgrade(temp5.get(6));
       int a6=fetchgrade(temp6.get(6));

       int total=(a1*3+a2*3+a3*3+a4*3+a5*3+a6*3)/18;
        cgpa_cal.setText(String.valueOf(total));
    }


    @FXML
    void print_marks(ActionEvent event) throws FileNotFoundException {
            String t =s_marks_reg.getText();
            String u = s_marks_stream.getText();
            String v = s_marks_semester.getValue();
            String w=s_name_falg.getText();
        String marksfile = "C:/Users/samun/Desktop/sems/Rough doc/marksheet.pdf";
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(marksfile));
        Document doc = new Document(pdfDoc,PageSize.A4.rotate());

        doc.add(new Paragraph("---------------------------------------------"));
        doc.add(new Paragraph("Reg.No  : "+t).setBold().setFontColor(Color.BLUE));
        doc.add(new Paragraph("Name  : "+w).setBold());
        doc.add(new Paragraph("Stream : "+u).setBold());
        doc.add(new Paragraph("Semester : "+v).setBold());
        doc.add(new Paragraph("---------------------------------------------"));
        doc.add(new Paragraph(""));
        doc.add(new Paragraph(""));
        doc.add(new Paragraph(""));
        doc.add(new Paragraph("Marks: ").setBold().setTextAlignment(TextAlignment.CENTER).setFontSize(18));
        doc.add(new Paragraph(""));
        Table table = new Table(7);
        table.setFontSize(10);
        table.setTextAlignment(TextAlignment.CENTER);
        table.addCell(new Cell().add("s.no").setBold());
        table.addCell(new Cell().add("Subjects").setBold());
        table.addCell(new Cell().add("Asses I").setBold());
        table.addCell(new Cell().add("Asses II").setBold());
        table.addCell(new Cell().add("End Sem").setBold());
        table.addCell(new Cell().add("Total").setBold());
        table.addCell(new Cell().add("Grades").setBold());

        table.addCell(new Cell().add(temp1.get(0)));
        table.addCell(new Cell().add(temp1.get(1)));
        table.addCell(new Cell().add(temp1.get(2)));
        table.addCell(new Cell().add(temp1.get(3)));
        table.addCell(new Cell().add(temp1.get(4)));
        table.addCell(new Cell().add(temp1.get(5)));
        table.addCell(new Cell().add(temp1.get(6)));

        table.addCell(new Cell().add(temp2.get(0)));
        table.addCell(new Cell().add(temp2.get(1)));
        table.addCell(new Cell().add(temp2.get(2)));
        table.addCell(new Cell().add(temp2.get(3)));
        table.addCell(new Cell().add(temp2.get(4)));
        table.addCell(new Cell().add(temp2.get(5)));
        table.addCell(new Cell().add(temp2.get(6)));

        table.addCell(new Cell().add(temp3.get(0)));
        table.addCell(new Cell().add(temp3.get(1)));
        table.addCell(new Cell().add(temp3.get(2)));
        table.addCell(new Cell().add(temp3.get(3)));
        table.addCell(new Cell().add(temp3.get(4)));
        table.addCell(new Cell().add(temp3.get(5)));
        table.addCell(new Cell().add(temp3.get(6)));

        table.addCell(new Cell().add(temp4.get(0)));
        table.addCell(new Cell().add(temp4.get(1)));
        table.addCell(new Cell().add(temp4.get(2)));
        table.addCell(new Cell().add(temp4.get(3)));
        table.addCell(new Cell().add(temp4.get(4)));
        table.addCell(new Cell().add(temp4.get(5)));
        table.addCell(new Cell().add(temp4.get(6)));

        table.addCell(new Cell().add(temp5.get(0)));
        table.addCell(new Cell().add(temp5.get(1)));
        table.addCell(new Cell().add(temp5.get(2)));
        table.addCell(new Cell().add(temp5.get(3)));
        table.addCell(new Cell().add(temp5.get(4)));
        table.addCell(new Cell().add(temp5.get(5)));
        table.addCell(new Cell().add(temp5.get(6)));

        table.addCell(new Cell().add(temp6.get(0)));
        table.addCell(new Cell().add(temp6.get(1)));
        table.addCell(new Cell().add(temp6.get(2)));
        table.addCell(new Cell().add(temp6.get(3)));
        table.addCell(new Cell().add(temp6.get(4)));
        table.addCell(new Cell().add(temp6.get(5)));
        table.addCell(new Cell().add(temp6.get(6)));

        doc.add(table);
        doc.add(new Paragraph("---------------------------------------------"));
        doc.add(new Paragraph("CGPA : "+cgpa_cal.getText()).setBold().setFontColor(Color.RED));
        doc.add(new Paragraph("---------------------------------------------"));

        doc.close();

        File marks_data=new File("C:/Users/samun/Desktop/sems/Rough doc/marksheet.pdf");
        try {
            Desktop.getDesktop().open( marks_data);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


} //end of controller






