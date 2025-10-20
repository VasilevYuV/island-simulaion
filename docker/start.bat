@echo off
chcp 65001 > NUL

echo 🏝️ Building Island Simulation...
cd ..
call mvn clean package -DskipTests

if %errorlevel% equ 0 (
    echo ✅ Build successful!
    echo 🐳 Starting Docker containers...
    docker-compose -f docker/docker-compose.prod.yml down
    docker-compose -f docker/docker-compose.prod.yml up --build -d

    timeout /t 5 /nobreak > nul
    echo 🎉 Island Simulation is running!
    echo 📊 Application: http://localhost:8080
    echo 🗄️  H2 Console: http://localhost:8080/h2-console
    start http://localhost:8080
) else (
    echo ❌ Build failed!
    pause
    exit /b 1
)
echo Press anything to stop application...
pause
docker stop island-simulation-app
exit