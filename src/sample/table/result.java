package sample.table;

public class result {
    String sno,sub_name,asses1,asses2,endsem,total,grade;


    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getSub_name() {
        return sub_name;
    }

    public void setSub_name(String sub_name) {
        this.sub_name = sub_name;
    }

    public String getAsses1() {
        return asses1;
    }

    public void setAsses1(String asses1) {
        this.asses1 = asses1;
    }

    public String getAsses2() {
        return asses2;
    }

    public void setAsses2(String asses2) {
        this.asses2 = asses2;
    }

    public String getEndsem() {
        return endsem;
    }

    public void setEndsem(String endsem) {
        this.endsem = endsem;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public result(String sno, String sub_name, String asses1, String asses2, String endsem, String total, String grade) {
        this.sno = sno;
        this.sub_name = sub_name;
        this.asses1 = asses1;
        this.asses2 = asses2;
        this.endsem = endsem;
        this.total = total;
        this.grade = grade;


    }
}
