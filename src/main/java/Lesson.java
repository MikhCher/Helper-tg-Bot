import java.util.Date;

public enum Lesson {
    ELMASH_LEC("Электромашины","9:00 - 10:30","270*(Фесто)","(лек)", Teacher.SAVELEV),
    ITSEC_PR("Информационная безопасность","10:50 - 12:20","371*","(пр)", Teacher.YAKOVENKO),
    MIRTC_LEC("МИРТС","14:55 - 16:25","270*(Фесто)","(лек)", Teacher.KOROTKOV),
    HYDROMECH_LEC("Гидромеханика","9:00 - 10:30","270*(Фесто)","(лек)", Teacher.STAZHKOV),
    OMR_PR("Основы мехатроники и робототехники","10:50 - 12:20","155*","(пр)", Teacher.MOROZ),
    TMM_LAB("Теория механизмов и машин","12:40 - 14:10","372*","(лаб)", Teacher.LAVROV, Teacher.VOROTINCEV),
    TAU_PR("ТАУ","14:55 - 16:25","267*(К.Кп)","(пр)", Teacher.KOROBKOVA, Teacher.ZHUKOV),
    PE("Физвоспитание","12:40 - 14:10","-","(пр)", Teacher.FIZRUK),
    ITSEC_LEC("Информационная безопасность","9:00 - 10:30","270*(Фесто)","(лек)", Teacher.YAKOVENKO),
    USTRMEC_LAB("Устройство мехатроники","10:50 - 12:20","Леб 28","(лаб)", Teacher.SLOBODZYAN, Teacher.ROMANENKO),
    USTRMEC_LEC("Устройство мехатроники","12:40 - 14:10","270*(Фесто)","(лек)", Teacher.SLOBODZYAN),
    USTRMEC_LEC2("Устройство мехатроники","16:45 - 18:15","270*(Фесто)","(лек)", Teacher.SLOBODZYAN),
    HYDROMECH_LAB("Гидромеханика","10:50 - 12:20","152*","(лаб)", Teacher.STAZHKOV, Teacher.VOROTINCEV),
    ELMASH_LAB("Электромашины","12:40 - 14:10","156*(Maxon)","(лаб)", Teacher.SAVELEV, Teacher.DZUKICH),
    TAU_LEC("ТАУ","14:55 - 16:25","270*(Фесто)","(лек)", Teacher.ZHUKOV),
    MIRTC_LEC2("МИРТС","16:45 - 18:15","270*(Фесто)","(лек)", Teacher.KOROTKOV),
    TMM_PR("Теория механизмов и машин","9:00 - 10:30","268*(Фесто), 155*","(пр)", Teacher.LAVROV),
    TMM_LEC("Теория механизмов и машин","10:50 - 12:20","270*(Фесто)","(лек)", Teacher.LAVROV),
    KP_PR("КП","14:55 - 16:25","532*","(пр)", Teacher.BOBROV),
    ELMASH_PR("Электромашины","9:00 - 10:30","269*(Фесто)","(пр)", Teacher.SAVELEV),
    MIRTC_LAB("МИРТС","9:00 - 10:30","268*(Фесто)","(лаб)", Teacher.KOROTKOV);


    private String name;
    private String continues;
    private String classroom;
    private String type;
    private Teacher teacher1;
    private Teacher teacher2 = Teacher.NULL;

    Lesson(String name, String continues, String classroom, String type, Teacher teacher) {
        this.name = name;
        this.continues = continues;
        this.classroom = classroom;
        this.type = type;
        this.teacher1 = teacher;
    }

    Lesson(String name, String continues, String classroom, String type, Teacher teacher1, Teacher teacher2) {
        this.name = name;
        this.continues = continues;
        this.classroom = classroom;
        this.type = type;
        this.teacher1 = teacher1;
        this.teacher2 = teacher2;
    }

    public String getName() {
        return name;
    }

    public String getContinues() {
        return continues;
    }

    public String getClassroom() {
        return classroom;
    }

    public String getType() {
        return type;
    }

    public Teacher getTeacher1() {
        return teacher1;
    }

    public Teacher getTeacher2() {
        return teacher2;
    }
}
