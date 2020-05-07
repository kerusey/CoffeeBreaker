<a href="https://github.com/kerusey/CoffeeBreaker/blob/master/LICENSE"><img src="https://img.shields.io/github/license/kerusey/CoffeeBreaker.svg?label=Coffee%20Breaker" /></a>
<a href="https://github.com/kerusey/CoffeeBreaker/commits/master"><img src="https://img.shields.io/github/last-commit/kerusey/CoffeeBreaker.svg"/></a>
<a href="https://github.com/kerusey/CoffeeBreaker/archive/master.zip"><img src="https://img.shields.io/github/repo-size/kerusey/CoffeeBreaker.svg"/></a>
<a href="https://github.com/kerusey/CoffeeBreaker/tags"><img src="https://img.shields.io/github/release-date/kerusey/CoffeeBreaker.svg"/></a>
<a href="https://discord.gg/9bdQ9py"><img src="https://img.shields.io/discord/693528089283657749"></a>
<a href="https://github.com/kerusey/CoffeeBreaker/pulls"><img src="https://img.shields.io/github/hacktoberfest/2019/kerusey/CoffeeBreaker.svg"/></a>
![Screenshot](Schemes/WhiteNewLogo.png)
<a href="https://github.com/kerusey/CoffeeBreaker/blob/master/README.md"><p align="right">English version</p></a>

## Описание
### Цель проекта 
  Автоматизация – одна из основных тенденций развития современных IT- технологий. Объяснить её нетрудно: возможность полностью избежать человеческого фактора, необходимости выплаты зарплат – это ставит автоматизацию во главу современных перспектив.
  
  На реализацию именно этой перспективы направлен наш продукт.
  
  Система Coffee Breaker – это автоматизированная система, с помощью который любой человек, любящий кофе и умеющий пользоваться современным мобильным телефоном, сможет приготовить себе кофе без участия баристы.

### Наше приложение
<img src="https://raw.githubusercontent.com/kerusey/CoffeeBreaker/master/Schemes/Preview.jpg"/>

## Предполагаемый продукт
Непосредственной целью нашего проекта является создание умной системы CoffeeBreaker, включающую в себя:
1. Мобильное приложение
2. Кофемашина, технически интегрированная в систему IoT.
3. Web-Сервер, осуществляющий взаимосвязь между мобильным приложением и кофемашиной.
4. Базу данных
5. Нейронную сеть, осуществляющую предсказание потраченных ингредиентов для каждой кофемашины

<p align="center"><img src="https://raw.githubusercontent.com/kerusey/CoffeeBreaker/master/Schemes/NewProjectMainStructure.jpg" / ></p>

## Устройство системы
Взаимодействие внутрисистемных узлов представляет собой три последовательных этапа, происходящих поочередно:

#### • Первый этап.</br>
Пользователь ищет на карте кафе,где находится кофемашина, затем заказывает кофейный напиток в мобильном приложении. Позже происходит передача информации о заказе на сервер и сохранение информации о заказе в базу данных.
</br>
#### • Второй этап.</br>
 Сервер отсылает на микрокомпьютер данные о заказе с помощью REST API.
 </br>
#### • Третий этап. </br>
Raspberry pi принимает конфигурацию напитка из .json файла и интерпретирует конфиг в Barista class. </br>
#### • Четвертый этап. </br>
Посредством вызовов методов класса Barista, Raspberry pi последовательно отправляет матрицы байт, при воспроизведении которых arduino (или esp8266), происходит имитация последовательного исполнения алгоритма выставления настроек кофе, которые указал пользователь, с последующим приготовлением напитка. </br>
## Результаты
Результатом взаимодействия элементов системы является приготовленный, в заданной пользователем конфигурации, кофейный напиток. Ресурсы, использованные для приготовления которого, зафиксированы и сохранены в базе данных, с возможностью последующего доступа и обработки этой информации нейронной сетью.


## Заметки для разработчиков
Если вы хотите принять участие в разработке проекта, можете сделать fork данного репозитория открыть новый pull request для любой поправки, улучшения или нового функционала. 
Обратитесь к нашему [руководсту для разработчиков](https://github.com/kerusey/CoffeeBreaker/blob/master/CONTRIBUTING.md), для более детальной информации о том, как правильно это сделать

## CoffeeBreaker team
<table>
  <tr>
    <td align="center"><a href="https://github.com/kerusey"><img src="https://avatars3.githubusercontent.com/u/38439184?s=400&u=cd2c9b9940b9faba20ad080274f079ca21286489&v=4" width="100px;" alt=""/><br /><sub><b>Danil Likh</b></sub></a><br /><a href="#ideas" title="Ideas, Planning, & Feedback">🤔</a><a href="#maintenance" title="Maintenance">🛠</a><a href="https://github.com/kerusey/CoffeeBreaker/commits?author=kerusey" title="Code">💻</a><a href="https://github.com/kerusey/CoffeeBreaker/commits?author=kerusey" title="Documentation">📖</a></td>
    <td align="center"><a href="https://github.com/syorito-hatsuki"><img src="https://avatars3.githubusercontent.com/u/33298273?s=400&v=4" width="100px;" alt=""/><br /><sub><b>Syorito Hatsuki</b></sub></a><br /><a href="#maintenance" title="Reviewed Pull Requests">👀 </a><a href="#maintenance" title="Maintenance">🛠</a><a href="https://github.com/kerusey/CoffeeBreaker/commits?author=syorito-hatsuki" title="Code">💻</a><a href="#projectManagement" title="Project Management">📆</a></td>
    <td align="center"><a href="https://github.com/BlueBlood-dev"><img src="https://avatars0.githubusercontent.com/u/62560825?s=400&u=96b5a5e6ce57625b605f5fc4e2dab1fe956c2c26&v=4" width="100px;" alt=""/><br /><sub><b>Khaschuk Den</b></sub></a><br /><a href="#tool" title="Tools">🔧</a><a href="#content" title="Content">🖋</a><a href="https://github.com/kerusey/CoffeeBreaker/commits?author=BlueBlood-dev" title="Code">💻</a>
      <td align="center"><a href="https://github.com/llav3ji2019"><img src="https://avatars3.githubusercontent.com/u/56979109?s=400&u=3d7ae402373361726aea80cc6ce2275a55223e70&v=4" width="100px;" alt=""/><br /><sub><b>Pavel Emelyanov</b></sub></a><br /><a href="#ideas" title="Ideas, Planning, & Feedback">🤔</a><a href="#maintenance" title="Maintenance">🛠</a><a href="https://github.com/kerusey/CoffeeBreaker/commits?author=llav3ji2019" title="Code">💻</a>
    <td align="center"><a href="https://github.com/nzhnme"><img src="https://sun9-66.userapi.com/c844521/v844521801/cb888/forMhE2fT9U.jpg" width="100px;" alt=""/><br /><sub><b>Mayya Nuzhnaya</b></sub></a><br /><a href="#content" title="Content">🖋</a><a href="https://github.com/kerusey/CoffeeBreaker/commits?author=nzhnme" title="Code">💻</a><a href="#tool" title="Tools">🔧</a>
      <td align="center"><a href="https://github.com/Conng"><img src="https://steamcdn-a.akamaihd.net/steamcommunity/public/images/avatars/62/62e0c072ecbf2947600ee1c0cf71229c626342fa_full.jpg" width="100px;" alt=""/><br /><sub><b>Max Denisov</b></sub></a><br /><a href="#content" title="Content">🖋</a><a href="#documentation" title="Documentation">📖</a>
    
  </tr>
</table>
