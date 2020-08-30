import java.util.Arrays;
import java.util.List;

public enum UnevenTimetable {
    MONDAY(null),
    TUESDAY(Arrays.asList(
            Lesson.ELMASH_LEC, Lesson.ITSEC_PR, Lesson.PE, Lesson.MIRTC_LEC
    )),
    WEDNESDAY(Arrays.asList(
            Lesson.HYDROMECH_LEC, Lesson.OMR_PR, Lesson.TMM_LAB, Lesson.TAU_PR
    )),
    THURSDAY(Arrays.asList(
            Lesson.ITSEC_LEC, Lesson.USTRMEC_LAB, Lesson.USTRMEC_LEC, Lesson.TAU_PR
    )),
    FRIDAY(Arrays.asList(
            Lesson.HYDROMECH_LAB, Lesson.ELMASH_LAB, Lesson.TAU_LEC, Lesson.MIRTC_LEC2
    )),
    SATURDAY(Arrays.asList(
            Lesson.TMM_PR, Lesson.TMM_LEC, Lesson.PE, Lesson.KP_PR
    ));

    private List<Lesson> list;

    UnevenTimetable(List<Lesson> list) {
        this.list = list;
    }

    public List<Lesson> getList() {
        return list;
    }
}
