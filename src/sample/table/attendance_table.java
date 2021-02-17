package sample.table;
public class attendance_table {
    String f_atd_regno,f_atd_name,f_atd_present,f_atd_absent;

    public attendance_table(String f_atd_regno, String f_atd_name, String f_atd_present, String f_atd_absent) {
        this.f_atd_regno = f_atd_regno;
        this.f_atd_name = f_atd_name;
        this.f_atd_present = f_atd_present;
        this.f_atd_absent = f_atd_absent;
    }

    public String getF_atd_regno() {
        return f_atd_regno;
    }

    public void setF_atd_regno(String f_atd_regno) {
        this.f_atd_regno = f_atd_regno;
    }

    public String getF_atd_name() {
        return f_atd_name;
    }

    public void setF_atd_name(String f_atd_name) {
        this.f_atd_name = f_atd_name;
    }

    public String getF_atd_present() {
        return f_atd_present;
    }

    public void setF_atd_present(String f_atd_present) {
        this.f_atd_present = f_atd_present;
    }

    public String getF_atd_absent() {
        return f_atd_absent;
    }

    public void setF_atd_absent(String f_atd_absent) {
        this.f_atd_absent = f_atd_absent;
    }

}
