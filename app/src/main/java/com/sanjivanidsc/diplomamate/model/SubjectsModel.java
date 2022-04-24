package com.sanjivanidsc.diplomamate.model;

public class SubjectsModel {

    String subjectName, subjectDescribe, semester, subjectImage;

    public SubjectsModel() {

    }

    public SubjectsModel(String subjectName, String subjectDescribe, String semester, String subjectImage) {
        this.subjectName = subjectName;
        this.subjectDescribe = subjectDescribe;
        this.semester = semester;
        this.subjectImage = subjectImage;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectDescribe() {
        return subjectDescribe;
    }

    public void setSubjectDescribe(String subjectDescribe) {
        this.subjectDescribe = subjectDescribe;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getSubjectImage() {
        return subjectImage;
    }

    public void setSubjectImage(String subjectImage) {
        this.subjectImage = subjectImage;
    }
}
