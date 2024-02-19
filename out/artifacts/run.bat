@echo off
if exist C:\ProgramData\PLAT\Credentials.csv (
    echo file exists
) else (
    echo "Password;Username">C:\ProgramData\PLAT\Credentials.csv
)
"C:\Program Files\Java\jdk-20\bin\java.exe" -jar design_jar/design.jar