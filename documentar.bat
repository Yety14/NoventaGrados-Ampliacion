@echo off

REM Crear carpeta doc si no existe
if not exist "doc" mkdir doc

REM Documentar todo el código fuente, redirigiendo la salida para ocultar mensajes innecesarios
javadoc -d doc -sourcepath src -subpackages noventagrados >nul 2>&1

echo "Documentacion finalizada"
pause
