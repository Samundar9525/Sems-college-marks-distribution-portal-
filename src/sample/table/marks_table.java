package sample.table;

public class marks_table {
    String m_regno,m_name,m_asses_I,m_asses_II,m_endsem,m_total,m_grade;

    public marks_table(String m_regno, String m_name, String m_asses_I, String m_asses_II, String m_endsem, String m_total, String m_grade) {
        this.m_regno = m_regno;
        this.m_name = m_name;
        this.m_asses_I = m_asses_I;
        this.m_asses_II = m_asses_II;
        this.m_endsem = m_endsem;
        this.m_total = m_total;
        this.m_grade = m_grade;
    }

    public String getM_regno() {
        return m_regno;
    }

    public void setM_regno(String m_regno) {
        this.m_regno = m_regno;
    }

    public String getM_name() {
        return m_name;
    }

    public void setM_name(String m_name) {
        this.m_name = m_name;
    }

    public String getM_asses_I() {
        return m_asses_I;
    }

    public void setM_asses_I(String m_asses_I) {
        this.m_asses_I = m_asses_I;
    }

    public String getM_asses_II() {
        return m_asses_II;
    }

    public void setM_asses_II(String m_asses_II) {
        this.m_asses_II = m_asses_II;
    }

    public String getM_endsem() {
        return m_endsem;
    }

    public void setM_endsem(String m_endsem) {
        this.m_endsem = m_endsem;
    }

    public String getM_total() {
        return m_total;
    }

    public void setM_total(String m_total) {
        this.m_total = m_total;
    }

    public String getM_grade() {
        return m_grade;
    }

    public void setM_grade(String m_grade) {
        this.m_grade = m_grade;
    }
}
