package com.ssn.academiaEnroll.service;

import com.ssn.academiaEnroll.Model.CourseOffering;
import com.ssn.academiaEnroll.Model.Faculty;
import com.ssn.academiaEnroll.Model.Message;
import com.ssn.academiaEnroll.Model.Student;
import com.ssn.academiaEnroll.repository.CourseOfferingRepository;
import com.ssn.academiaEnroll.repository.facultyRepository;
import com.ssn.academiaEnroll.repository.MessageRepository;
import com.ssn.academiaEnroll.repository.studentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    private facultyRepository facultyRepository;

    @Autowired
    private studentRepository studentRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private CourseOfferingRepository courseOfferingRepository;

    public List<Message> getMessagesForUser(Long userId) {
        return messageRepository.findBySenderIdOrReceiverId(userId); // Assuming you have this method in your repository
    }
    public Message sendMessage(Message message) {
        // Logic to save message to the database
        return messageRepository.save(message);
    }

    public Optional<Message> getMessageById(Long id) {
        return messageRepository.findById(id);
    }

    public void deleteMessage(Long id) {
        messageRepository.deleteById(id);
    }
    public boolean canMessage(int senderId, int receiverId) {
        // Check if the sender is a faculty member
        if (isFaculty(senderId)) {
            Faculty faculty = facultyRepository.findById(senderId).orElse(null);
            if (faculty != null) {
                List<Integer> studentIds = faculty.getStudentIds();
                return studentIds.contains(receiverId); // Check if the receiver is one of the students
            }
        } else if (isStudent(senderId)) {
            Student student = studentRepository.findById(senderId).orElse(null);
            if (student != null) {
                // Check if the student is enrolled in any course offerings taught by the receiver (faculty)
                return isEnrolledInCourseOffering(senderId, receiverId);
            }
        }
        return false; // Default case if none of the conditions are met
    }

    private boolean isFaculty(int userId) {
        return facultyRepository.existsById(userId);
    }

    private boolean isStudent(int userId) {
        return studentRepository.existsById(userId);
    }

    private boolean isEnrolledInCourseOffering(int studentId, int facultyId) {
        // Implement the logic to check if the student is enrolled in any courses offered by the faculty member
        // This might involve checking course offerings linked to the facultyId and verifying the student's enrollment
        List<CourseOffering> enrolledOfferings = courseOfferingRepository.findByStudentId(studentId);
        // Example logic (this needs to be adapted based on your actual models):
        return enrolledOfferings // Assuming this method retrieves the list of course offerings the student is enrolled in
                .stream()
                .anyMatch(courseOffering -> courseOffering.getFacultyID() == facultyId);
    }
}
