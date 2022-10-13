package ovh.fejker.filetosql;

public class User {
    private long id;
    private String name;
    private String surname;
    private String login;

    public User(long id, String login, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.login = login;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getLogin() {
        return login;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name=" + name + ", surname=" + surname + ", login=" + login + "}";
    }
}
