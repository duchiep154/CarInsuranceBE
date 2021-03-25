package com.c0920g1.c0920g1carinsurancebe.utils.check_input_output;

import java.util.Scanner;

public class CheckInPrimeData {
    public static float checkFloatPositive() {
        Scanner sc = new Scanner(System.in);
        float n = 0;
        boolean check = false;
        while (!check) {
            try {
                n = sc.nextFloat();
                if (n > 0) {
                    check = true;
                } else {
                    System.out.println("Do NOT enter negative or zero value, enter again!");
                    check = false;
                }
            } catch (Exception e) {
                System.err.println("Wrong type of data, enter again!");
                sc.nextLine();
            }
        }
        return n;
    }

    public static float checkFloat() {
        Scanner sc = new Scanner(System.in);
        float n = 0;
        boolean check = false;
        while (!check) {
            try {
                n = sc.nextFloat();
                check = true;
            } catch (Exception e) {
                System.err.println("Wrong type of data, enter again!");
                sc.nextLine();
            }
        }
        return n;
    }

    public static double checkDoublePositive() {
        Scanner sc = new Scanner(System.in);
        double n = 0;
        boolean check = false;
        while (!check) {
            try {
                n = sc.nextDouble();
                if (n > 0) {
                    check = true;
                } else {
                    System.out.println("Do NOT enter negative or zero value, enter again!");
                    check = false;
                }
            } catch (Exception e) {
                System.err.println("Wrong type of data, enter again!");
                sc.nextLine();
            }
        }
        return n;
    }
    public static double checkDoubleMinOneMillion() {
        Scanner sc = new Scanner(System.in);
        double n = 0;
        boolean check = false;
        while (!check) {
            try {
                n = sc.nextDouble();
                if (n >= 1000000) {
                    check = true;
                } else {
                    System.out.println("Money deposit min 1.000.000 VND, enter again!");
                    check = false;
                }
            } catch (Exception e) {
                System.err.println("Wrong type of data, enter again!");
                sc.nextLine();
            }
        }
        return n;
    }

    public static double checkDouble() {
        Scanner sc = new Scanner(System.in);
        double n = 0;
        boolean check = false;
        while (!check) {
            try {
                n = sc.nextDouble();
                check = true;
            } catch (Exception e) {
                System.err.println("Wrong type of data, enter again!");
                sc.nextLine();
            }
        }
        return n;
    }

    public static int checkIntPositive() {
        Scanner sc = new Scanner(System.in);
        int n = 0;
        boolean check = false;
        while (!check) {
            try {
                n = sc.nextInt();
                if (n > 0) {
                    check = true;
                } else {
                    System.out.println("Do NOT enter negative or zero value, enter again!");
                    check = false;
                }
            } catch (Exception e) {
                System.err.println("Wrong type of data, enter again!");
                sc.nextLine();
            }
        }
        return n;
    }

    public static int checkInt() {
        Scanner sc = new Scanner(System.in);
        int n = 0;
        boolean check = false;
        while (!check) {
            try {
                n = sc.nextInt();
                check = true;
            } catch (Exception e) {
                System.err.println("Wrong type of data, enter again!");
                sc.nextLine();
            }
        }
        return n;
    }
}
