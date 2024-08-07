package org.alex_group.users_menu;

public interface PrintMenu {
    static void updateUserInfoMenu() {
        System.out.println("""
                Какую информацию вы хотите обновить?
                1. Имя
                2. Фамилию
                3. Возраст
                4. Логин
                5. Пароль
                6. вернуться в меню
             
                """);
    }

    static void printRegisterInfo () {
        System.out.println(
                """ 
                        1. Регистрация в сервисе
                        2. Авторизация в сервисе
                        3. Войти в личный кабинет
                        """
        );
    }

     static void printMenuForAdmin() {
        System.out.println(
                """
                        1. Просмотр списка зарегистрированных клиентов и сотрудников.
                        2. просмотр заявок на покупку автомобилей
                        3. Обработка заявок на обслуживание автомобиля
                        4. редактирование информации об автомобиле
                        5. удаление автомобиля
                        6. просмотр списка всех автомобилей
                        7. Поиск заказов по дате, клиенту, статусу и автомобилю
                        8. добавление автомобиля
                        9. редактирование данных
                       10. заявка на покупку автомобиля
                       11. заявка на обслуживание автомобиля
                       12. Поиск автомобилей по марке, модели, году выпуска, цене и другим характеристикам
                       13. Выход
                        """
        );
    }

    static void printMenuForManager() {
        System.out.println(
                """
                        1. редактирование личных данных
                        2. добавление автомобиля
                        3. просмотр заявок на покупку автомобилей
                        4. просмотр заявок на обслуживание автомобиля.
                        5. редактирование информации об автомобиле
                        6. удаление автомобиля
                        7. просмотр списка всех автомобилей
                        8. Поиск заказов по дате, клиенту, статусу и автомобилю
                        9. Выход
                        """
        );
    }

     static void printMenuForUser() {
        System.out.println(
                """
                        1. редактирование учетной записи
                        2. просмотр списка автомобилей
                        3. заявка на покупку автомобиля
                        4. заявка на обслуживание автомобиля
                        5. Поиск автомобилей по марке, модели, году выпуска, цене и другим характеристикам. 
                        6. Выход
                        """
        );

    }




}