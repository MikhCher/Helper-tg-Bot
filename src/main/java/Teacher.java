public enum Teacher {
    LENTOVSKIY("Лентовский","Вадим","Валентинович"),
    GUSEV("Гусев", "Сергей", "Александрович"),
    ORESHINA("Орешина","Ольга","Анатольевна"),
    BUTKAREVA("Буткарева","Наталья","Германовна"),
    ILIKHMENEV("Илихменьев","А.","Л."),
    YARYGIN("Ярыгин","Дмитрий","Михайлович"),
    BOLSHAKOVA("Большакова","Галина","Анатольевна"),
    BESPERSTOV("Бесперстов","Эдуард","Александрович"),
    OSINSKAYA("Осинская","Е.","А."),
    NEVZOROVA("Невзорова","Наталья","Федоровна"),
    SOLDATKIN("Солдаткин","Андрей","Владимирович"),
    IVANOV("Иманов","Дмитрий","?"),
    FIZRUK("?", "?","?"),
    LABI("Алексеева", "Ольга","Сергеевна");

    private String name;
    private String patronymic;
    private String surname;

    Teacher(String surname, String name, String patronymic) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
    }

    public String getName() {
        return name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public String getSurname() {
        return surname;
    }

    @Override
    public String toString() {
        return surname + " " + name + " " + patronymic;
    }
}
