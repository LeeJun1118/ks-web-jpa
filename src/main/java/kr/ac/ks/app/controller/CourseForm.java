package kr.ac.ks.app.controller;

import kr.ac.ks.app.domain.Lesson;
import kr.ac.ks.app.domain.Student;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseForm {
    private Student student;
    private Lesson lesson;
}
