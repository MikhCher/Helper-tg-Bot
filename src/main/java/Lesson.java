import java.util.Date;

public enum Lesson {
    PHYSICS_LEC("Физика","9:00 - 10:30","331*","(лек)", Teacher.LENTOVSKIY),
    PHYSICS_PR("Физика","16:45 - 18:15","328а*","(пр)", Teacher.IVANOV),
    PHYSICS_LAB("Физика","16:45 - 18:15","322*","(лаб)", Teacher.LABI),
    ELTECH_LEC("Элтех","10:50 - 12:20","325*","(лек)", Teacher.GUSEV),
    ELTECH_LAB("Элтех","12:40 - 14:10","360*","(лаб)", Teacher.GUSEV),
    MATH_LEC("Математика 5","14:55 - 16:25","313","(лек)", Teacher.SOLDATKIN),
    MATH_PR("Математика 5","10:50 - 12:20","477","(пр)", Teacher.ORESHINA),
    PE("Физвоспитание","12:40 - 14:10","-","(пр)", Teacher.FIZRUK),
    SOPROMAT_LEC("Сопр. Материалов","14:55 - 16:25","310","(лек)", Teacher.BUTKAREVA),
    SOPROMAT_PR("Сопр. Материалов","16:45 - 18:15","102*","(пр)", Teacher.BUTKAREVA),
    TEORMEKH_LEC("Теор. Механика","10:50 - 12:20","318","(лек)", Teacher.ILIKHMENEV),
    TEORMEKH_PR("Теор. Механика","12:40 - 14:10","444","(пр)", Teacher.ILIKHMENEV),
    MIKROELECTRONIC_LEC("Микроэлектроника","14:55 - 16:25","493","(лек)", Teacher.YARYGIN),
    MIKROELECTRONIC_LAB("Микроэлектроника","12:40 - 14:10","423в","(лаб)", Teacher.YARYGIN),
    METROLOGY_LEC("Метрология","10:50 - 12:20","316","(лек)", Teacher.BOLSHAKOVA),
    METROLOGY_PR("Метрология","14:55 - 16:25","332","(пр)", Teacher.BOLSHAKOVA),
    SYSTEMS_LEC("Сисетмы и сети","12:40 - 14:10","313","(лек)", Teacher.BESPERSTOV),
    SYSTEMS_PR("Сисетмы и сети","14:55 - 16:25","278(?)","(пр)", Teacher.OSINSKAYA),
    ENGLISH("Иностранный язык","16:45 - 18:15","-","(пр)", Teacher.NEVZOROVA);

    private String name;
    private String continues;
    private String classroom;
    private String type;
    private Teacher teacher;

    Lesson(String name, String continues, String classroom, String type, Teacher teacher) {
        this.name = name;
        this.continues = continues;
        this.classroom = classroom;
        this.type = type;
        this.teacher = teacher;
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

    public Teacher getTeacher() {
        return teacher;
    }
}
