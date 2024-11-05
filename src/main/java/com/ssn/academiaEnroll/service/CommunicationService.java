package com.ssn.academiaEnroll.service;

import com.ssn.academiaEnroll.Model.*;
import com.ssn.academiaEnroll.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommunicationService {

    @Autowired
    private facultyRepository facultyRepository;

    @Autowired
    private studentRepository studentRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private CourseOfferingRepository courseOfferingRepository;

    @Autowired
    private AnnouncementRepository announcementRepository;

    public List<Message> getMessagesForUser(Long userId) {
        return messageRepository.findBySenderIdOrReceiverId(userId);
    }
    public <T extends Communication> T sendCommunication(T communication) {
        if (communication instanceof Message) {
            return (T) messageRepository.save((Message) communication);
        } else if (communication instanceof Announcement) {
            return (T) announcementRepository.save((Announcement) communication);
        } else {
            throw new IllegalArgumentException("Unsupported communication type");
        }
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

    public boolean isFaculty(int userId) {
        return facultyRepository.existsById(userId);
    }

    private boolean isStudent(int userId) {
        return studentRepository.existsById(userId);
    }

    private boolean isEnrolledInCourseOffering(int studentId, int facultyId) {
        List<CourseOffering> enrolledOfferings = courseOfferingRepository.findByStudentId(studentId);
        return enrolledOfferings
                .stream()
                .anyMatch(courseOffering -> courseOffering.getFacultyID() == facultyId);
    }

    public List<Announcement> getAnnouncements() {
        return announcementRepository.findAll();
    }
}
