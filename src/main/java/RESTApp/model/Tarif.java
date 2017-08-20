package RESTApp.model;

import java.util.Objects;

public class Tarif {

    private int tarifId;
    private String tarifName;
    private int amount;
    private int amountChange;
    private int timeChange;

    public Tarif(){}

    public Tarif(int tarifId,String tarifName, int amount, int amountChange, int timeChange){
        this.tarifId=tarifId;
        this.tarifName = tarifName;
        this.amount = amount;
        this.amountChange= amountChange;
        this.timeChange = timeChange;
    }

    public int getTarifId() {
        return tarifId;
    }

    public String getTarifName() {
        return tarifName;
    }

    public int getAmount() {
        return amount;
    }

    public int getAmountChange() {
        return amountChange;
    }

    public int getTimeChange() {
        return timeChange;
    }

    public void setTarifId(int tarifId) {
        this.tarifId = tarifId;
    }

    public void setTarifName(String tarifName) {
        this.tarifName = tarifName;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setAmountChange(int amountChange) {
        this.amountChange = amountChange;
    }

    public void setTimeChange(int timeChange) {
        this.timeChange = timeChange;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tarif tarif = (Tarif) o;
        return tarifId == tarif.tarifId &&
                amount == tarif.amount &&
                amountChange == tarif.amountChange &&
                timeChange == tarif.timeChange &&
                Objects.equals(tarifName, tarif.tarifName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tarifId, tarifName, amount, amountChange, timeChange);
    }
}
