package zenika.cfm.variable;

public class Variable {

    String id;
    String name;

    public Variable(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Variable{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
