package Model1;

import java.io.Serializable;

public class Lesson implements Serializable {

    private LessonType lessonType;
    private String time;
    private String room;
    private Course course;

    public Lesson(Course course, LessonType lessonType, String time, String room) {
        this.course = course;
        this.lessonType = lessonType;
        this.time = time;
        this.room = room;
    }

    public LessonType getLessonType() { return lessonType; }
    public String getTime() { return time; }
    public String getRoom() { return room; }
    public Course getCourse() { return course; }

    @Override
    public String toString() {
        return String.format("%-10s | %-20s | Time: %-12s | Room: %s",
                lessonType, course.getCourseName(), time, room);
    }
}