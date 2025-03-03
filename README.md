# НТО 2024. II отборочный этап. Командные задани — серверная часть

## 📖 Предыстория
В компании S контроль доступа в офис осуществляется с помощью СКУД (системы контроля управления доступом). На данный момент у каждого сотрудника компании есть карта-пропуск с NFC меткой. А у каждой входной двери есть считыватель с обеих сторон. При поднесении карты к считывателю, дверь открывается, а информация о времени входа или выхода сотрудника фиксируется в базе данных. 
Администрации компании S требуется мобильное приложение, как для рядовых сотрудников, так и для администрации с возможностью просмотра посещений и работой электронного пропуска как временной замены обычного (при помощи сканировании QR кода, который находится на считывателе). Поскольку в приложении есть возможность использовать телефон как пропуск - то к данному приложению повышенные требования к безопасности всех данных, находящихся внутри него.

## Документация
Документация доступна на [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
