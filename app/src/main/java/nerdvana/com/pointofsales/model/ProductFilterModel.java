package nerdvana.com.pointofsales.model;

public class ProductFilterModel {
    private int id;
    private int core_id;
    private String department;
    private boolean isChecked;
    public ProductFilterModel(int id, int core_id, String department, boolean isChecked) {
        this.id = id;
        this.core_id = core_id;
        this.department = department;
        this.isChecked = isChecked;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCore_id() {
        return core_id;
    }

    public void setCore_id(int core_id) {
        this.core_id = core_id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
