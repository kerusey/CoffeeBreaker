<a href="https://github.com/kerusey/CoffeeBreaker/blob/master/LICENSE"><img src="https://img.shields.io/github/license/kerusey/CoffeeBreaker.svg?label=Coffee%20Breaker" /></a>
<a href="https://github.com/kerusey/CoffeeBreaker/commits/master"><img src="https://img.shields.io/github/last-commit/kerusey/CoffeeBreaker.svg"/></a>
<a href="https://github.com/kerusey/CoffeeBreaker/archive/master.zip"><img src="https://img.shields.io/github/repo-size/kerusey/CoffeeBreaker.svg"/></a>
<a href="https://github.com/kerusey/CoffeeBreaker/tags"><img src="https://img.shields.io/github/release-date/kerusey/CoffeeBreaker.svg"/></a>
<a href="https://discord.gg/9bdQ9py"><img src="https://img.shields.io/discord/693528089283657749"></a>
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/c5a4cf1ec75a46239019e6ad5395ca12)](https://app.codacy.com/manual/kerusey/CoffeeBreaker/dashboard)

<img src="https://github.com/kerusey/CoffeeBreaker/blob/master/Schemes/WhiteNewLogo.png"/>

<a href="https://github.com/kerusey/CoffeeBreaker/blob/CoffeeBreaker2.0/README-RU.md"><p align="right">Русская версия</p></a>
## Description
### Main goal of the project
  Automation is one of the main trends in the development of modern IT technologies. It is not difficult to explain it: the ability to completely avoid the human factor, necessity to pay salaries-this puts automation at the top of modern prospects.

 Our product is aimed at implementing this very perspective.

 The Coffee Breaker system is an automated system that allows everyone who loves coffee and knows how to use a modern mobile phone to make coffee without the participation of a Barista.
### Our mobile app
<img src="https://raw.githubusercontent.com/kerusey/CoffeeBreaker/master/Schemes/Preview.jpg"/>


## Alleged product
The main idea of ours project is creation of a smart CoffeeBreaker system, which contains:
1. Mobile app
2. IoT integrated coffee machine.
3. Web-Server, witch connects mobile app and the coffee machine
4. DataBase
5. A neural network that predicts spending ingredients for each coffee machine
</br>
<p align="center"><img src="https://raw.githubusercontent.com/kerusey/CoffeeBreaker/master/Schemes/NewProjectMainStructure.jpg" / ></p>

## System composition</br>
#### The interaction of intersystem nodes consists of three consecutive stages that occur in turn:

###### • The first step:</br>
The user searches the map for the cafe where the coffee machine is located, then orders a coffee drink in the mobile app. Later, the order information is sent to the server and the order information is saved in the database.
</br>
###### • The second stage:</br>
The server sends order data to the microcomputer using the REST API, and it passes it as bytes to the Arduino, which translates it to the coffee machine.
 </br>
###### • The third stage: </br>
The coffee machine receives data from the Arduino and brews coffee for the user. </br>
## Results
The result of interaction of the system elements is a coffee drink prepared in a user-defined configuration. The resources used for the preparation of which are recorded and stored in the database, with the possibility of subsequent access and processing of this information by the neural network.
## Developer notes
If you would like to take a part in the project development feel free to fork it and open pull requests for any fix, improvement or feature you add.
You may check the <a href="https://github.com/kerusey/CoffeeBreaker/blob/master/CONTRIBUTING.md">contributing guide</a> for more information on how to do this.

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
