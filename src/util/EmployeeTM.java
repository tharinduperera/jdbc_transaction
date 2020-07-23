package util;


public class EmployeeTM {
    private int id;
    private String name;
    private double salary;
    private double etf;

    public EmployeeTM() {
    }

    public EmployeeTM(int id, String name, double salary, double etf) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.etf = etf;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getEtf() {
        return etf;
    }

    public void setEtf(double etf) {
        this.etf = etf;
    }

    @Override
    public String toString() {
        return "EmployeeTM{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                ", etf=" + etf +
                '}';
    }
}
