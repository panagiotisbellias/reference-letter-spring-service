package gr.hua.dit.ds.reference.letter.service.entity;

import gr.hua.dit.ds.reference.letter.service.entity.hibernate.DateUtils;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email")
    private String email;

    @Column(name = "school")
    private String school;

    @Column(name = "uni_id")
    private String uniId;

    @Column(name = "url_grading_file")
    private String urlGradingFile;

    @Column(name = "date_of_birth")
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    public Student() {
    }

    public Student(int id, String fullName, String email, String school, String uniId, String urlGradingFile,
                   Date dateOfBirth) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.school = school;
        this.uniId = uniId;
        this.urlGradingFile = urlGradingFile;
        this.dateOfBirth = dateOfBirth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getSchool() {
        return school;
    }

    public String getUniId() {
        return uniId;
    }

    public String getUrlGradingFile() {
        return urlGradingFile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public void setUniId(String uniId) {
        this.uniId = uniId;
    }

    public void setUrlGradingFile(String urlGradingFile) {
        this.urlGradingFile = urlGradingFile;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", school='" + school + '\'' +
                ", uniId='" + uniId + '\'' +
                ", urlGradingFile='" + urlGradingFile + '\'' +
                ", dateOfBirth=" + DateUtils.formatDate(dateOfBirth) +
                '}';
    }
}

