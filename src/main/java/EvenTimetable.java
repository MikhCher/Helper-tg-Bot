import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public enum EvenTimetable {
    MONDAY(Arrays.asList(
            Lesson.PHYSICS_LEC, Lesson.ELTECH_LEC
    )),
    TUESDAY(Arrays.asList(
            Lesson.MATH_PR, Lesson.PE, Lesson.SOPROMAT_LEC
    )),
    WEDNESDAY(Arrays.asList(
            Lesson.TEORMEKH_LEC, Lesson.TEORMEKH_PR, Lesson.MIKROELECTRONIC_LEC
    )),
    THURSDAY(Arrays.asList(
            Lesson.METROLOGY_LEC, Lesson.SYSTEMS_LEC, Lesson.METROLOGY_PR, Lesson.ENGLISH
    )),
    FRIDAY(Arrays.asList(
            Lesson.PE, Lesson.MATH_LEC, Lesson.PHYSICS_LAB
    )),
    SUTARDAY(null);

    private List<Lesson> list;

    EvenTimetable(List<Lesson> list) {
        this.list = list;
    }

    public List<Lesson> getList() {
        return list;
    }
}
