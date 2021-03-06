# Описание приложений :
## [Calculator](https://github.com/EgorBa/Android_App/tree/master/Calculator)
Обычный калькулятор поддерживающий работу с double, поворот экрана и возможность копировать результат.  
[Внешний вид](https://github.com/EgorBa/Android_App/blob/master/Calculator/example.jpg)
## [UIContent](https://github.com/EgorBa/Android_App/tree/master/UIContent)
Экран приложения погоды.  
[Горизонтальная ориентация](https://github.com/EgorBa/Android_App/blob/master/UIContent/horizontal.jpg)  
[Вертикальная ориентация](https://github.com/EgorBa/Android_App/blob/master/UIContent/vertical.jpg)
## [Animation](https://github.com/EgorBa/Android_App/tree/master/Animation)
Бесконечно анимирующаяся вьюшка.  
[Пример анимации](https://github.com/EgorBa/Android_App/blob/master/Animation/Example.mp4)
## [Contacts](https://github.com/EgorBa/Android_App/tree/master/Contacts)
Приложение получает доступ к вашим контактам (работа с Permission) и загружает их в RecyclerView. При нажатии на контакт
открывается окно вызова данного контакта. Так же добавлен поиск контактов по именни и номеру телефона. Написаны Unit и Integration тесты,
проверяющие фильстрацию и работу кнопок.
## [DroidScan](https://github.com/EgorBa/Android_App/tree/master/DroidScan)
Приложение получает доступ к вашему местоположению (работа с Permisson) и каждую минуту записывает его на ваш телефон в определенный файл.
Приложение работает в фоновом режиме с помощью Services.
## [SimpleInstagram](https://github.com/EgorBa/Android_App/tree/master/SimpleInstagram)
Приложение загружает фотографии и их описание с открытого api с помощью AsyncTask и загружает их в RecyclerView. При нажатии на 
фотографию, загружается она же в хорошем качестве. При повороте экрана данные не перезапрашиваются.
## [WeatherApp](https://github.com/EgorBa/Android_App/tree/master/WeatherApp)
Приложение погоды, загружающее погоду с открытого api на ближайшую неделю, если есть соеденение с интеретом 
(работа с библиотеками Retrofit, Picasso).
Если соеденения нет, то загружает из памяти телефона результаты прошлой загрузки (работа с Permission и Room).
При повороте экрана данные не перезапрашиваются.  
[Горизонтальная ориентация](https://github.com/EgorBa/Android_App/blob/master/WeatherApp/horizontal.jpg)  
[Вертикальная ориентация](https://github.com/EgorBa/Android_App/blob/master/WeatherApp/vertical.jpg)
## [KrutBar](https://github.com/EgorBa/Android_App/tree/master/KrutBar)
Приложение разработанное для ресторана. Загружает всё [меню](https://github.com/EgorBa/Android_App/blob/master/KrutBar/Examples/Menu.jpg) в RecyclerView с google базы данных (таким образом есть возможность вносить в него коррективы), от туда же берет доступные службы доставки. Также есть отдельная страница ["О нас"](https://github.com/EgorBa/Android_App/blob/master/KrutBar/Examples/About.jpg), где присутствуют ссылки на соц. сети и google карта. По мимо этого присутствует регистрация и реферальная программа, позволяющая накапливать бонусы и тратить их но новые заказы (также работает все на основе google базы данных и двух приложений : [клиента](https://github.com/EgorBa/Android_App/blob/master/KrutBar/Examples/Profile.jpg) и [мэнеджера](https://github.com/EgorBa/Android_App/blob/master/KrutBar/Examples/Manager.jpg))  
[Приложение для клиента](https://github.com/EgorBa/Android_App/tree/master/KrutBar/Krutbar)  
[Приложение для мэнеджера](https://github.com/EgorBa/Android_App/tree/master/KrutBar/KrutbarManager)
