package RESTApp.model;

public class User {
    private int id;
    private String fio;
    private String phone;
    private int tarifId;

    public User() {}

    public User(int id, String fio, String phone, int tarifId) {
        this.id = id;
        this.fio = fio;
        this.phone = phone;
        this.tarifId = tarifId;
    }

    public User(String fio, String phone, int tarifId){
        this.fio = fio;
        this.phone = phone;
        this.tarifId = tarifId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getTarifId() {
        return tarifId;
    }

    public void setTarifId(int tarifId) {
        this.tarifId = tarifId;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fio='" + fio + '\'' +
                ", phone='" + phone + '\'' +
                ", tarifId=" + tarifId +
                '}';
    }
}
