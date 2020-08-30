public enum Teacher {
    SAVELEV("Савельев","Б.","Н."),
    YAKOVENKO("Яковенко", "Н.", "Г."),
    KOROTKOV("Кровенко","Е.","Б."),
    STAZHKOV("Стажков","С.","М."),
    MOROZ("Мороз","А.","В."),
    LAVROV("Лавров","В.","Ю."),
    VOROTINCEV("Воротынцев","Б.","Н."),
    KOROBKOVA("Коробкова","И.","Л."),
    ZHUKOV("Жуков","Ю.","А."),
    SLOBODZYAN("Слободзян","Н.","С."),
    ROMANENKO("Романенко","И.","А."),
    DZUKICH("Джукич","Д.","Й."),
    FIZRUK("?", "?","?"),
    BOBROV("Бобров", "А.","А."),
    NULL("","","");

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
