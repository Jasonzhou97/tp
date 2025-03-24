package seedu.address.model.reservation;

public class Person {
    private Phone phone;
    private Name name;
    private boolean isRegular = false;
    private int counter = 0;

    public Person(Name name, Phone phone) {
        this.phone = phone;
        this.name = name;
    }

    public Phone getPhone() {
        return phone;
    }

    public String getPhoneNumber() {
        return phone.value;
    }
    public Name getName() {
        return name;
    }

    public boolean isRegular() {
        return isRegular;
    }

    public void setIsRegular(boolean isRegular) {
        this.isRegular = isRegular;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

}