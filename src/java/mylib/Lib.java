/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mylib;

/**
 *
 * @author nvhoa
 */
public class Lib {

    public boolean checkString(String s) {
        if (s == null || s.isEmpty()) {
            return false;
        }
        return true;
    }

    public boolean isDate(String s) {
        // năm - tháng - nhày
        String[] sD = s.split("-");

        if (sD.length != 3) {
            return false;
        }

        if (sD[0].length() != 4 || sD[1].length() != 2 || sD[2].length() != 2) {
            return false;
        }

        for (char c : sD[0].toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }

        for (char c : sD[1].toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }

        for (char c : sD[2].toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }

        return true;
    }

    public boolean isPhoneNumber(String s) {
        char[] cA = s.toCharArray();

        if (cA.length != 10) {
            return false;
        }

        for (char c : cA) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    public boolean isEmail(String s) {
        // abc@gmail.com 

        String[] s1 = s.split("@");
        if (s1.length != 2) {
            return false;
        }

        for (char c : s1[0].toCharArray()) {
            if (!Character.isLetterOrDigit(c)) {
                return false;
            }
        }

        if (!s1[1].equalsIgnoreCase("gmail.com")) {
            return false;
        }
        return true;
    }

    public int compareDateString(String date1, String date2) {
        String[] d1 = date1.split("-");
        String[] d2 = date2.split("-");
        int y1 = Integer.parseInt(d1[0]);
        int y2 = Integer.parseInt(d2[0]);
        if (y1 > y2) {
            return 1;
        } else if (y1 < y2) {
            return -1;
        }
        int m1 = Integer.parseInt(d1[1]);
        int m2 = Integer.parseInt(d2[1]);
        if (m1 > m2) {
            return 1;
        } else if (m1 < m2) {
            return -1;
        }
        int dy1 = Integer.parseInt(d1[2]);
        int dy2 = Integer.parseInt(d2[2]);
        if (dy1 > dy2) {
            return 1;
        } else if (dy1 < dy2) {
            return -1;
        }
        return 0;
    }

}
