package kr.ac.ks.app.controller;

import kr.ac.ks.app.domain.Lesson;
import kr.ac.ks.app.domain.Student;
import kr.ac.ks.app.repository.LessonRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class LessonController {

    private final LessonRepository lessonRepository;

    public LessonController(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    @GetMapping(value = "/lessons/new")
    public String createForm(Model model) {
        model.addAttribute("lessonForm", new LessonForm());
        return "lessons/lessonForm";
    }

    @PostMapping(value = "/lessons/new")
    public String create(LessonForm form) {
        Lesson lesson = new Lesson();
        lesson.setName(form.getName());
        lesson.setQuota(form.getQuota());
        lessonRepository.save(lesson);
        return "redirect:/lessons";
    }

    @GetMapping(value = "/lessons")
    public String list(Model model) {
        List<Lesson> lessons = lessonRepository.findAll();
        model.addAttribute("lessons", lessons);
        return "lessons/lessonList";
    }


    @GetMapping("/lesson/update/{id}")
    public String showUpdateLessonForm(@PathVariable("id")Long id, Model model){
        Lesson lesson = lessonRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Invalid lesson Id:" +id));
        model.addAttribute("lesson",lesson);
        return "lessons/update-lesson";
    }

    @PostMapping("/lesson/update/{id}")
    public String updateLesson(@PathVariable("id")Long id, @Valid LessonForm lessonForm, BindingResult result) {
        if (result.hasErrors()) {
            return "lessons/lessonForm";
        }
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid lesson Id:" + id));

        lesson.setName(lessonForm.getName());
        lesson.setQuota(lessonForm.getQuota());
        lessonRepository.save(lesson);
        return "redirect:/lessons";
    }

    @GetMapping("lesson/delete/{id}")
    public String deleteLesson(@PathVariable("id")Long id/*,Model model*/){

        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid lesson Id:" + id));

        lessonRepository.delete(lesson);
        return "redirect:/lessons";
    }
}
