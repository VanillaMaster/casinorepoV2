# casinorepoV2
NameBot: "Дифичента" (казино(нечестное)) (Постановка Task 1)
1. Работа в консоли;
2. Инициализация пользователя и его поинтов;
3. Реализация игры "Крэпс";
4. Система подкрутки для игры "Крэпс";
5. Автореген поинтов до N количества

Правила игры Крепс:
- 1 этап игры: Игрок бросает 1 12-гранный кубик. Игрок имеет возможность ставить на pass или dpass с выигрышем х2,
если игрок поставил на pass и выиграл, начинается второй этап игры.
- 2 этап игры: Игрок бросает 2 12-гранныч кубика. Игрок имеет возможность поставить на любое число от 2 до 24 с выигрышем х8,
- Выигрыш pass 4,5,6,8,9,10,11 в остальном случак выйгрывает dpass.
- условие поражения: если на одном из 12-гранных кубиков выпадет число 7 или число равное выпавшему на первом кубике из первого этапа,
- условие победы: сумма чисел двух 12-гранных кубиков равна поставленному числу.

Изначально игрок получает 1000 пойнтов. Которые регенерируют до 1000 если их меньше.

Постановка Task2:

1. Работа в телеграм.
2. Работа с несколькими пользователями. 
3. Реализация статусов с пассивными эффектами. 
4. Кнопки выбора игр 
5. Визуал статусов+ количества поинтов (плашечка с текущим количеством поинтов, текущим статусом, и доступными статусами.)             

Доп: данные в облочной DB

1) cloud DB (https://github.com/VanillaMaster/vanilla-db)
