public enum Teacher {
    LENTOVSKIY("Лентовский","В.","В."),
    GUSEV("Гусев", "Сергей", "Александрович"),
    ORESHINA("Орешина","Ольга","Анатольевна"),
    BUTKAREVA("Буткарева","Н.","Г."),
    ILIKHMENEV("Илихменьев","А.","Л."),
    YARYGIN("Ярыгин","Дмитрий","Михайлович"),
    BOLSHAKOVA("Большакова","Г.","А."),
    BESPERSTOV("Бесперстов","Э.","А."),
    OSINSKAYA("Осинская","Е.","А."),
    NEVZOROVA("Невзорова","Наталья","Федоровна"),
    SOLDATKIN("Солдаткин","А.","В."),
    IVANOV("Иманов","Дмитрий","?"),
    FIZRUK("Хз", "кто","это"),
    LABI("хз", "кто","это");

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
