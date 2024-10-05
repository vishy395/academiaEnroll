package com.ssn.academiaEnroll.Model;
public enum Semester {
    I(1),    // 1st semester
    II(2),   // 2nd semester
    III(3),  // 3rd semester
    IV(4),   // 4th semester
    V(5),    // 5th semester
    VI(6),   // 6th semester
    VII(7),  // 7th semester
    VIII(8); // 8th semester

    private final int number;

    // Constructor to assign number to each enum value
    Semester(int number) {
        this.number = number;
    }

    // Getter to retrieve the semester number
    public int getNumber() {
        return number;
    }

    // Method to convert number to Roman numeral
    public static Semester fromNumber(int number) {
        for (Semester semester : Semester.values()) {
            if (semester.getNumber() == number) {
                return semester;
            }
        }
        throw new IllegalArgumentException("Invalid semester number: " + number);
    }
}
