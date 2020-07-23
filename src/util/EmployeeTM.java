package util;


import java.math.BigDecimal;

public class EmployeeTM {
    private String id;
    private String name;
    private BigDecimal salary;
    private BigDecimal etf;

    public EmployeeTM() {
    }

    public EmployeeTM(String id, String name, BigDecimal salary, BigDecimal etf) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.etf = etf;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public BigDecimal getEtf() {
        return etf;
    }

    public void setEtf(BigDecimal etf) {
        this.etf = etf;
    }

    @Override
    public String toString() {
        return "EmployeeTM{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                ", etf=" + etf +
                '}';
    }
}
