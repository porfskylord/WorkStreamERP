@echo off
set ROOT=D:\JAVAPROGRAMMING\ALLProjects\WorkStreamERP
set EMAIL_USERNAME=porf.notifications.service@gmail.com
set EMAIL_PASSWORD=jhkl bact vuzd atlk

wt new-tab --title "discovery-server" cmd /k "cls && echo =============================== && echo DISCOVERY-SERVER && echo =============================== && cd /d %ROOT%\discovery-server && mvn spring-boot:run" ; ^
new-tab --title "user-service" cmd /k "timeout /t 5 >nul && cls && echo =============================== && echo USER-SERVICE && echo =============================== && cd /d %ROOT%\user-service && mvn spring-boot:run" ; ^
split-pane -H --title "auth-service" cmd /k "timeout /t 8 >nul && cls && echo =============================== && echo AUTH-SERVICE && echo =============================== && cd /d %ROOT%\auth-service && mvn spring-boot:run" ; ^
new-tab --title "project-service" cmd /k "timeout /t 12 >nul && cls && echo =============================== && echo PROJECT-SERVICE && echo =============================== && cd /d %ROOT%\project-service && mvn spring-boot:run" ; ^
split-pane -H --title "notification-service" cmd /k "timeout /t 15 >nul && cls && echo =============================== && echo NOTIFICATION-SERVICE && echo =============================== && cd /d %ROOT%\notification-service && set EMAIL_USERNAME=%EMAIL_USERNAME% && set EMAIL_PASSWORD=%EMAIL_PASSWORD% && mvn spring-boot:run" ; ^
new-tab --title "api-gateway" cmd /k "timeout /t 20 >nul && cls && echo =============================== && echo API-GATEWAY && echo =============================== && cd /d %ROOT%\api-gateway && mvn spring-boot:run"
