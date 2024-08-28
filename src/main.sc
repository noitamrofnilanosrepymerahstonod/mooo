require: slotfilling/slotFilling.sc
  module = sys.zb-common

require: common.js
    module = sys.zb-common
    
require: functions.js


theme: /

    state: Правила
        q!: $regex</start>
        intent!: /Давай поиграем
        a: Игра "Быки и коровы". Загадаю четырёхзначное число с неповторяющимися цифрами, ты будешь отгадывать. После каждой попытки я сообщу сколько цифр угадано без совпадения с их позициями в тайном числе (то есть количество коров) и сколько угадано вплоть до позиции в тайном числе (то есть количество быков). Начнём?
        go!: /Правила/Согласен?

        state: Согласен?

            state: Да
                intent: /Согласие
                go!: /Игра

            state: Нет
                intent: /Несогласие
                a: Ну и ладно! Если передумаешь — скажи "давай поиграем"

    state: Игра
        script:
            $session.number = generateNumber()
            $reactions.answer("Загадано {{$session.number}}");
            $reactions.transition("/Проверка");

    state: Проверка
        intent: /Число
        script:
            # сохраняем введенное пользователем число
            var num = $parseTree._Number;

            # проверяем угадал ли пользователь загаданное число и выводим соответствующую реакцию
            if (num == $session.number) {
                $reactions.answer("Ты выиграл! Хочешь еще раз?");
                $reactions.transition("/Правила/Согласен?");
            }
            else {
                var result = bullsandcows($session.number, num)
                $reactions.answer("Быки: ${result.bulls}, Коровы: ${result.cows}");
            }

    state: NoMatch || noContext = true
        event!: noMatch
        random:
            a: Я не понял.
            a: Что вы имеете в виду?
            a: Ничего не пойму