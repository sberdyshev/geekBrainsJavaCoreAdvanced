package ru.sberdyshev.learn.geekbains.java.core.advanced.lesson1.homework;

import ru.sberdyshev.learn.geekbains.java.core.advanced.lesson1.homework.tools.Course;
import ru.sberdyshev.learn.geekbains.java.core.advanced.lesson1.homework.tools.Team;

/**
 * @author sberdyshev
 *
 */
public class App {
    public static void main(String[] args) {
        Course c = new Course(); // Создаем полосу препятствий
        Team team = new Team("winners"); // Создаем команду
        c.doIt(team); // Просим команду пройти полосу
        team.showTeamMates(); // Показываем результаты
    }
}
