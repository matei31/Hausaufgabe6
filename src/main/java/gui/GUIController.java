package gui;

import controller.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import model.Student;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

/**
 * Date: 12.12.2021
 * Author: Grosu Matei
 * Class GUIController uses one Controller to carry out the functionalities
 */
public class GUIController{

    private final Controller controller;

    public GUIController() {
        this.controller = new Controller();
    }

    @FXML
    private TextField myStudentFirstName;
    @FXML
    private TextField myStudentLastName;
    @FXML
    private TextField myStudentId;
    @FXML
    private TextField totalCredits;
    @FXML
    private TextField courseId;
    @FXML
    private TextField myTeacherFirstName;
    @FXML
    private TextField myTeacherLastName;
    @FXML
    private TextField teacherId;
    @FXML
    private TextField refreshId;
    @FXML
    private Label studentLabel;
    @FXML
    private ListView<String> myListView = new ListView<>();


    /**
     * Button to enter the students menu
     */
    @FXML
    protected void onStudentsMenuButtonClick() throws IOException {
        GuiApplication.showMenu("StudentsMenu.fxml", "Students menu");
    }

    /**
     * Button to enter the teachers menu
     */
    @FXML
    protected void onTeachersMenuButtonClick() throws IOException {
        GuiApplication.showMenu("TeachersMenu.fxml", "Teachers menu");
    }

    /**
     * Button to save student in the DB
     */
    @FXML
    protected void saveStudent() throws IOException {
        controller.addStudent(myStudentFirstName.getText(),
                myStudentLastName.getText(),
                parseInt(myStudentId.getText()), parseInt(totalCredits.getText()));

    }

    /**
     * Button to save enroll student identified by studentId to a course identified by courseId
     */
    @FXML
    protected void registerStudent() throws IOException {
        controller.registerById(parseInt(myStudentId.getText()), parseInt(courseId.getText()));
    }

    /**
     * Button to display the number of credits of a student identified by a studentId
     */
    @FXML
    protected void showCredits() throws IOException {
        int studentId = Integer.parseInt(myStudentId.getText());
        ArrayList<Student> students = controller.findAllStudents();
        boolean found = false;
        for (Student student: students)
            if(student.getStudentId() == studentId)
            {
                found = true;
                studentLabel.setText("" + student.getTotalCredits());
            }
        if(!found)
            studentLabel.setText("Student does not exist");
    }

    /**
     * Button to save teacher in the DB
     */
    @FXML
    protected void saveTeacher() throws IOException {
        controller.addTeacher(myTeacherFirstName.getText(),
                myTeacherLastName.getText(),
                parseInt(teacherId.getText()));
    }

    /**
     * Button to display students enrolled to courses that are lectured by a teacher identified by a teacherId
     */
    @FXML
    protected void showStudents() throws IOException {
        if(teacherId.getText() != null) {
            myListView.getItems().clear();
            ArrayList<Student> students = this.controller.findStudentsByTeacherId(Integer.parseInt(teacherId.getText()));
            List<String> strings = students.stream().map(Student::toString).collect(Collectors.toList());
            myListView.getItems().addAll(strings);
            myListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        }
    }

    /**
     * Button to refresh the students enrolled to a course while the app is running from the teachers menu
     */
    @FXML
    protected void refresh() throws IOException {
        if(refreshId.getText() != null) {
            myListView.getItems().clear();
            ArrayList<Student> students = this.controller.findStudentsByCourseId(Integer.parseInt(refreshId.getText()));
            List<String> strings = students.stream().map(Student::toString).collect(Collectors.toList());
            myListView.getItems().addAll(strings);
            myListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        }
    }

    /**
     * Button to clear all text fields in the student menu
     */
    @FXML
    public void clearStudent(){
        myStudentFirstName.clear();
        myStudentLastName.clear();
        myStudentId.clear();
        totalCredits.clear();
        courseId.clear();
    }

    /**
     * Button to clear all text fields in the teacher menu
     */
    @FXML
    public void clearTeacher(){
        myTeacherFirstName.clear();
        myTeacherLastName.clear();
        teacherId.clear();
        refreshId.clear();
    }
}