package com.example.lesson_4

import android.util.Log

typealias activityListener = () -> Unit

object PersonsHolder {

    private val personsList = mutableListOf<Persons>()
    private var listeners = mutableListOf<activityListener>()

    init {
        personsList.add(Persons(
            "https://interesnyefakty.org/wp-content/uploads/eksl-rouz.jpg",
            "Эксл Роуз",
            "1962 - now",
            "Американский музыкант, фронтмен и вокалист группы Guns N' Roses. Из-за своего мощного и широкого вокального диапазона и энергичных концертных выступлений Роуз был назван одним из величайших вокалистов всех времён различными средствами массовой информации, включая Rolling Stone и NME",
            "Мужской"))
        personsList.add(Persons("https://mesmika.com/upload/012/u1240/f/3/cffa37d1.jpg",
            "Анатолий Царёв",
            "1988 - now",
            "Анатолий родился в Тамбове 4 мая 1988 года[3]. В 2002 году в возрасте 14 лет на Анатолия произвел сильное впечатление знакомый Александр Гриднев, сыгравший в необычной манере песню «Генералы песчаных карьеров».",
            "Мужской"))
        personsList.add(Persons("https://chel.aif.ru/pictures/201307/%D0%9C%D0%931.jpg",
            "Михаил Юрьевич Горшенёв",
            "1973 - 2013",
            "Советский и российский музыкант, основатель, один из двух фронтменов и вокалистов, идейный вдохновитель, а также автор большей части музыки и некоторых текстов рок-группы «Король и Шут».",
            "Мужской"))
        personsList.add(Persons("https://img.gazeta.ru/files3/657/10730657/upload-RIAN_01148607.HR.ru-pic905-895x505-68268.jpg",
            "Виктор Цой",
            "1962 - 1990",
            "Советский рок-музыкант, автор песен, поэт, художник и киноактёр. Основатель и лидер рок-группы «Кино»; до этого являлся основателем групп «Палата № 6» и «Гарин и Гиперболоиды», а также был участником группы «Автоматические удовлетворители».",
            "Мужской"))
        personsList.add(Persons("https://psb4ukr.natocdn.net/criminals/04/09/f7/wzxWDlNxILg.jpg",
            "Валерий Александрович Кипелов",
            "1979 - now",
            "Родился 12 июля 1958 года в поселке городского типа Капотня (ныне район Капотня города Москвы). В школьные годы учился в музыкальной школе, которую окончил по классу баяна.",
            "Мужской"))
        personsList.add(Persons("https://upload.wikimedia.org/wikipedia/commons/d/d9/%D0%9B%D1%91%D0%B2%D0%B0_%D0%B1%D0%B82.jpg",
            "Ягор Міхайлавіч Бортнік",
            "1988 - now",
            "В 1985 году Егор Бортник в минской детской театральной студии «Ронд» познакомился с другим будущим основателем группы «Би-2» — Александром Уманом (Шура Би-2).",
            "Мужской"))
        personsList.add(Persons("https://upload.wikimedia.org/wikipedia/commons/thumb/d/d6/Noize_MC_2020_%282%29.jpg/411px-Noize_MC_2020_%282%29.jpg",
            "Иван Алексеев",
            "1997 - now",
            "Более известный под сценическим псевдонимом Noize MС, — российский музыкант, рэп-рок-исполнитель.",
            "Мужской"))
        personsList.add(Persons("https://upload.wikimedia.org/wikipedia/commons/thumb/a/a1/%D0%9F%D0%BE%D1%80%D0%BD%D0%BE%D1%84%D0%B8%D0%BB%D1%8C%D0%BC%D1%8B_18.02.2018.jpg/411px-%D0%9F%D0%BE%D1%80%D0%BD%D0%BE%D1%84%D0%B8%D0%BB%D1%8C%D0%BC%D1%8B_18.02.2018.jpg",
            "Владимир Андреевич Котляров",
            "1987 - now",
            "Владимир Котляров родился в городе Дубна, в СССР 28 октября 1987 года. Пошел в Школу №7 в Дубне. Желание заниматься музыкой появилось, когда Владимир учился во втором классе и первый раз услышал группу Nirvana.",
            "Мужской"))
        personsList.add(Persons("https://upload.wikimedia.org/wikipedia/commons/thumb/e/eb/Aleksandr_Ilyin_Jr._2015.jpg/411px-Aleksandr_Ilyin_Jr._2015.jpg",
            "Александр Ильин",
            "1992 - now",
            "Группа образовалась в августе 2010 года в Москве. Александр Ильин (вокал), Андрей Шморгун (бас гитара) и Денис Хромых (гитара) в течение полугода записали 5 демо-песен. В начале 2011 года к музыкантам присоединились Андрей Обухов (гитара), Сергей Иванов (барабаны) и Илья Ильин (баян).",
            "Мужской"))
        personsList.add(Persons("https://cdnn21.img.ria.ru/images/148763/40/1487634003_0:185:1961:1298_600x0_80_0_0_a0df5ce2aa1ff0c1a879610da066049e.jpg",
            "Курт До́нальд Кобе́йн",
            "1967 - 1994",
            "Американский рок-музыкант, вокалист, гитарист и автор песен. Наиболее известен как основатель и лидер рок-группы «Нирвана».",
            "Мужской"))
    }

    fun getPersonsList(): MutableList<Persons> {
        return personsList
    }

    fun addListener(listener: activityListener) {
        listeners.add(listener)
    }

    fun sendMessage() {
        Log.i("[APP]", "Element was added")
        for (listener in listeners){
            listener.invoke()
        }
    }

}